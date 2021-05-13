package com.bnyte.util;

import com.bnyte.anno.scan.Component;
import com.bnyte.anno.scan.Controller;
import com.bnyte.anno.scan.Repository;
import com.bnyte.anno.scan.Service;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @说明 获取指定包下所有的class犬类名, jar包同样适用
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class ClazzUtils {

    /*
    构建四大注解, 即为如果包含该四大注解之一则将其扫描至容器中
    分别为:
        @Controller   控制层，就是我们的action层
        @Service        业务逻辑层，就是我们的service或者manager层
        @Repository  持久层，就是我们常说的DAO层
        @Component  （字面意思就是组件），它在你确定不了事哪一个层的时候使用。
 */
    private static Map<String, Class<?>> baseAnnotationInScan = new HashMap<>();

    static {
        baseAnnotationInScan.put("com.bnyte.anno.scan.Controller", Controller.class);
        baseAnnotationInScan.put("com.bnyte.anno.scan.Service", Service.class);
        baseAnnotationInScan.put("com.bnyte.anno.scan.Repository", Repository.class);
        baseAnnotationInScan.put("com.bnyte.anno.scan.Component", Component.class);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName, boolean isRecursion) {
        // 创建返回结果
        Set<String> classNames = null;
        /*
            获取jvm核心类库的类加载器,该方式可以获取到所有的类加载器,
            如果只是使用getClassLoader()只会获取到当前类的类加载器。
            获取到核心类加载器对象可以获取到大量的数据信息, 具体等待研究。
         */
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // 将启动类包路径转换为系统可识别路径
        String packagePath = packageName.replace(".", "/");
        // 获取到指定路径的URL对象, 通过该对象可以获取到当前指定路径所在系统的绝对路径
        URL url = loader.getResource(packagePath);
        if (url != null) {
            // 获取当前文件的协议, 判断是file还是jar包
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                // 从目录中获取所有全类名
                classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
            } else if (protocol.equals("jar")) {
                JarFile jarFile = null;
                try {
                    /*
                        获取jarFile, 类型大致为: jar:http://hostname/my.jar!/
                         具体访问: https://blog.csdn.net/xiaoliuliu2050/article/details/88751596
                     */
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jarFile != null) {
                    // 获取指定jar包路径下的所有类的全类名
                    getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
                }
            }
        } else {
            // 从所有的jar包中查找包名
            classNames = getClassNameFromJars(((URLClassLoader) loader).getURLs(), packageName, isRecursion);
        }

        return classNames;
    }

    /**
     * 从项目文件获取某包下有类
     *
     * @param filePath    文件路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
        Set<String> className = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {

            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
                }
            } else {
                String fileName = childFile.getName();
                //endsWith() 方法用于测试字符串是否以指定的后缀结束  !fileName.contains("$") 文件名中不包含 '$'(该校验主要是用于判断是否是内部类)
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }

        return className;
    }


    /**
     * @param jarEntries
     * @param packageName
     * @param isRecursion
     * @return
     */
    private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName,
                                                   boolean isRecursion) {
        Set<String> classNames = new HashSet();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (!jarEntry.isDirectory()) {
                /*
                 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
                 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
                 */
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if (isRecursion) {
                        classNames.add(entryName);
                    } else if (!entryName.replace(packageName + ".", "").contains(".")) {
                        classNames.add(entryName);
                    }
                }
            }
        }

        return classNames;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls        URL集合
     * @param packageName
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
        Set<String> classNames = new HashSet<>();

        for (int i = 0; i < urls.length; i++) {
            String classPath = urls[i].getPath();
            //不必搜索classes文件?
            if (classPath.endsWith("classes/")) {
                continue;
            }

            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile != null) {
                classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
            }
        }

        return classNames;
    }

    public static boolean annotationIsExistsForClass(Class<?> searchClass) {
        boolean returnValue = false;
        // 获取当前类上的所有注解
        Annotation[] classAnnotations = searchClass.getAnnotations();
        for (Annotation classAnnotation : classAnnotations) {
            // 获取当前注解的全类名
            String annotationName = classAnnotation.annotationType().getName();
            // 判断当前注解是否是四大注解之一, 如果存在则说明当前类需要加载到IOC容器中
            returnValue = baseAnnotationInScan.containsKey(annotationName);
        }
        return returnValue;
    }
}

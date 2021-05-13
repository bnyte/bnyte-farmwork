package com.bnyte.util;

import com.bnyte.exception.ApplicationYamlException;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @说明 yaml的工具类
 * @创建日期 2021-05-11 星期二
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BnyteYamlUtils {

    /**
     * 将一个对象写出到配置文件
     * @param data 写入的对象
     */
    public static void writerForObject(String filename, Object data) {
        YamlWriter writer = null;
        try {
            writer = new YamlWriter(new FileWriter(filename));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (YamlException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  注意: 将数据类型, 属性名, 属性中的元素类型一一对应即可完成映射
     * @param filename 写出的文件(路径)名
     * @param data 写出的数据
     * @param type 写出的数据中的属性类对象
     * @param propertyNames 写出的数据中的属性名
     * @param elementTypes 属性中的元素类对象
     */
    public static void writerForObject(String filename, Object data, Class type, List<String> propertyNames, List<Class> elementTypes) {
        if (propertyNames.size() != elementTypes.size()) {
            throw new ApplicationYamlException("属性名和属性中元素类型必须保持拥有相同个数");
        }
        YamlWriter writer = null;
        try {
            writer = new YamlWriter(new FileWriter(filename));
            for (int i = 0; i < propertyNames.size(); i++) {
                // 将数据类型, 属性名, 属性中的元素类型一一对应即可完成映射
                writer.getConfig().setPropertyElementType(type, propertyNames.get(i), elementTypes.get(i));
            }
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (YamlException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件classpath下的的绝对路径
     * @param filename 文件名
     * @return 文件绝对路径
     */
    public static String getAbsolutePath(String filename) {
        return BnyteYamlUtils.class.getClassLoader().getResource(filename).getPath();
    }

    /**
     * 解析配置文件中的数据并转换为Map对象
     * @param filename 配置文件的名, 基于classpath下, 如果在classpath下的多级目录则指出即可
     *                 如: "test/bnyte.yaml"
     * @return 解析的数据并且转换为map数据结构
     */
    public static Map<String, Object> getYamlDataForFilename(String filename) {
        YamlReader yamlReader = null;
        Map<String, Object> readData = null;
        try {
            yamlReader = new YamlReader(new FileReader(getYamlFilepath(filename)));
            readData = (Map<String, Object>) yamlReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readData;
    }

    /**
     * 解析配置文件中的数据并转换为Map对象
     * @param filename 配置文件的名, 基于classpath下, 如果在classpath下的多级目录则指出即可
     *                 如: "test/bnyte.yaml"
     * @param entity 读取之后需要转换的Java实体类的类对象
     * @return 解析的数据并且转换为指定的对象, 但是返回的依然是Object
     */
    public static Object getYamlDataForEntity(String filename, Class<? extends Object> entity) {
        YamlReader yamlReader = null;
        Object instance = null;
        try {
            yamlReader = new YamlReader(new FileReader(getYamlFilepath(filename)));
            instance =  yamlReader.read(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 获取指定配置文件名的路径
     * @return 配置文件路径
     */
    public static String getYamlFilepath(String filename) {
        URL resource = BnyteYamlUtils.class.getClassLoader().getResource(filename);
        if (resource == null) {
            if ("bnyte.yaml".equals(filename) || "bnyte.yml".equals(filename)) {
                int indexOf = filename.lastIndexOf(".");
                String filePrefix = filename.substring(0, indexOf);
                String fileSuffix = filename.substring(indexOf + 1);
                if (fileSuffix.length() > 3) {
                    fileSuffix = "yml";
                } else {
                    fileSuffix = "yaml";
                }
                filename = filePrefix +"."+ fileSuffix;
                resource = BnyteYamlUtils.class.getClassLoader().getResource(filename);
                if (resource == null)
                    throw new ApplicationYamlException("核心配置文件\"bnyte.yaml\"或\"bnyte.yml\"未找到");
            }
            else
                throw new ApplicationYamlException("配置文件" +filename+ "未找到");
        }
        return resource.getPath();
    }

    /**
     * 将文件路径名转换成yaml容器对象中的key, 不同的yaml文件存放其对应的yaml数据
     *  解析格式如下:
     *      beans.yaml => beans
     *      bnyte/bnyt/bny/beans.yaml => bnyteBnytBnyBeans
     * @param yamlSourceName 源文件名
     * @return 返回解析后的文件名用作为当前yaml文件中读取到的数据存放在yaml容器中的key
     */
    public static String formatYamlName (String yamlSourceName) {
        int suffixIndex = yamlSourceName.lastIndexOf(".");
        if (suffixIndex > 0) {
            yamlSourceName = yamlSourceName.substring(0, yamlSourceName.lastIndexOf("."));
        }
        String change = formatPathnameChangeToCapital(yamlSourceName);
        if (change.lastIndexOf("/") > 0) {
            change = formatYamlName(change);
        }
        return change;
    }

    /**
     * 注释见本类方法: formatYamlName() 一致, 只是该方法用作于解析多级目录的封装方法
     * @param pathname 文件名
     * @return 解析后的文件名
     */
    private static String formatPathnameChangeToCapital(String pathname) {
        StringBuilder returnValue = new StringBuilder(pathname);
        // 获取第一次出现的索引
        int firstPathDelimiterIndex = returnValue.indexOf("/");
        if (firstPathDelimiterIndex > 0) {
            // 获取第一次出现分割符索引之前的字符串
            String prefix = returnValue.substring(0, firstPathDelimiterIndex);
            String suffix = returnValue.substring(firstPathDelimiterIndex + 2);
            // 获取到文件分隔符的后一位字符并将其转换为大写
            String changeStr = String.valueOf(pathname.charAt(firstPathDelimiterIndex + 1)).toUpperCase();
            // 将字符进行清空拼接给返回结果
            returnValue = new StringBuilder(prefix + changeStr + suffix);
        }
        return returnValue.toString();

    }
}

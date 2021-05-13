package com.bnyte.ioc.analyze;

import com.bnyte.ioc.bean.BeanConfigInAnnotation;
import com.bnyte.ioc.util.BeanUtils;
import com.bnyte.util.ClazzUtils;

import java.util.Set;

/**
 * @说明 解析注解获得到的IOC容器对象
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class AnalyzeAnnotation extends BeanConfigInAnnotation {


    protected static void start(Class<?> type) {
        // 通过字节码对象获取到指定字节码所在的包路径
        String packageName = type.getPackage().getName();
        // 扫描程序入口所在包及其所在包下的所有类
        Set<String> classNames = ClazzUtils.getClassName(packageName, true);
        // 将类名转换为类对象并判断其类是否存在指定注解
        for (String className : classNames) {
            try {
                // 转换成字节码对象
                Class<?> classObj = Class.forName(className);
                // 判断是否存在指定注解
                boolean existsForClass = ClazzUtils.annotationIsExistsForClass(classObj);
                // 如果存在指定的注解则将注解添加至IOC容器中
                if (existsForClass) {
                    // 获取容器名
                    String beanName = BeanUtils.getBeanName(className);
                    // 将当前符合条件的对象添加到IOC容器中
                    BeanConfigInAnnotation.addBean(beanName, classObj);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

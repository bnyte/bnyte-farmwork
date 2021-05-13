package com.bnyte.ioc.application;

import com.bnyte.ioc.application.impl.BnyteIOCApplication;
import com.bnyte.ioc.application.impl.IOCApplicationInAnnotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @说明 通过注解获取到的IOC容器对象
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class IOCApplicationInAnnotationFactory {

    // 获取注解ioc容器
    protected static Map<String, Object> iocBeanInAnno = new HashMap<>();

    /**
     * 往注解IOC容器中添加数据
     * @param beanName 容器名
     * @param classObj 容器对象
     */
    protected static void addBean(String beanName, Class<?> classObj) {
        // 判断当前容器对象是否存在
        if (iocBeanInAnno.get(beanName) == null) {
            Object instance = null;
            try {
                // 实例化对象
                instance = classObj.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            // 将对象添加到IOC容器中
            iocBeanInAnno.put(beanName, instance);
        }
    }


}

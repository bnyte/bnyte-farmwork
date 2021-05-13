package com.bnyte.ioc.bean;

import com.bnyte.ioc.application.IOCApplicationInAnnotationFactory;

/**
 * @说明 bean放置到容器之前的配置
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BeanConfigInAnnotation extends IOCApplicationInAnnotationFactory {

    /**
     * 往ioc容器中添加bean组件
     * @param beanName bean名字
     * @param classObj bean值
     */
    protected static void addBean(String beanName, Class<?> classObj) {
        // 往ioc的注解容器中添加数据
        IOCApplicationInAnnotationFactory.addBean(beanName, classObj);
    }
}

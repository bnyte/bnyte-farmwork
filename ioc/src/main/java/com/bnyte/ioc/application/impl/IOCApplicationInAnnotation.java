package com.bnyte.ioc.application.impl;

import com.bnyte.ioc.application.IOCApplicationInAnnotationFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @说明 获取工厂注解ioc容器对象中获取到ioc容器中的数据
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class IOCApplicationInAnnotation extends IOCApplicationInAnnotationFactory {

    // 当前的注解IOC容器对象中已有的beanName和其对应的bean组件
    protected Map<String, Object> iocBeanInAnno;

    public IOCApplicationInAnnotation() {
        // 获取到注解的ioc容器对象
        iocBeanInAnno = IOCApplicationInAnnotationFactory.iocBeanInAnno;
        // 判断容器对象是否为空
        if (iocBeanInAnno == null) {
            iocBeanInAnno = new HashMap<>();
        }
    }

    /**
     * 获取bean对象
     * @param beanName beanName
     * @return 返回一个该beanName所对应的bean组件
     */
    public Object getBean(String beanName) {
        return iocBeanInAnno.get(beanName);
    }

    protected Map<String, Object> getIocBeanInAnno() {
        return iocBeanInAnno;
    }
}

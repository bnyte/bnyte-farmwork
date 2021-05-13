package com.bnyte.ioc.application.impl;

import com.bnyte.ioc.application.BnyteIOCApplicationFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @说明 yaml容器和注解容器中的对象都会放在这里面
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BnyteIOCApplication extends BnyteIOCApplicationFactory {

    public BnyteIOCApplication() {
        // 获取注解容器
        IOCApplicationInAnnotation inAnnotation = new IOCApplicationInAnnotation();
        iocBean.put("iocBeanInAnnotation", inAnnotation.getIocBeanInAnno());
        // TODO 获取yaml容器并放入ioc容器对象中
    }

}

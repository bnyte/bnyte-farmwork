package com.bnyte.ioc.application;

import com.bnyte.ioc.application.impl.IOCApplicationInAnnotation;

import java.util.HashMap;
import java.util.Map;

/**
 * @说明 IOC容器的工厂数据处理等格式化类
 * @创建日期 2021-05-13 星期四
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BnyteIOCApplicationFactory {

    protected BnyteIOCApplicationFactory() {

    }


    // IOC容器, 该容器包含了注解解析的对象和yaml解析的对象
    protected static Map<String, Map<String, Object>> iocBean = new HashMap<>();
    static {
        // 添加注解解析的对象
        iocBean.put("iocBeanInAnnotation", new HashMap<>());
        // 添加yaml解析的对象
        iocBean.put("iocBeanInYaml", new HashMap<>());
    }








}

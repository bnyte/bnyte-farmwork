package com.bnyte.ioc.application.impl;

import com.bnyte.ioc.application.IOCApplicationInYamlFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @说明 通过yaml容器注入的对象
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class IOCApplicationInYaml extends IOCApplicationInYamlFactory {

    /**
     * 该方法存放所有的IOC容器bean组件, 并且是通过读取yaml文件获取到的
     */
    private Map<String, Object> iocApplicationInYaml = new HashMap<>();

    /**
     * 初始化yaml配置文件读到的bean组件
     */
    public IOCApplicationInYaml() {
        initAllBeans();
    }

    /**
     * 初始化yaml的IOC容器对象集
     */
    private void initAllBeans() {
        iocApplicationInYaml = initIOCData();
    }

    /**
     * 获取所有的yamlIOC容器对象集
     * @return
     */
    public Map<String, Object> getIocBeanInYaml() {
        return iocApplicationInYaml;
    }

    public Object getBean(String beanName) {
        // 获得bean
        return iocApplicationInYaml.get(beanName);
    }

}

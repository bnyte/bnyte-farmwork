package com.bnyte.ioc.application;

import com.bnyte.ioc.analyze.AnalyzeYaml;
import com.bnyte.ioc.util.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @说明 通过yaml容器注入的对象
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class IOCApplicationInYamlFactory extends ApplicationDataYamlsFactory {

    /**
     * 加载所有由配置文件加载的ioc容器bean组件
     * @return
     */
    protected static Map<String, Object> initIOCData() {
        // 创建返回值对象
        Map<String, Object> classMap = new HashMap<>();
        // 获取到所有读取到的需要加载的IOC容器类, 此处为所有的容器对象进行实例化
        Map<String, Object> inYamlClassName = getIOCApplicationInYaml();
        for (String beanName : inYamlClassName.keySet()) {
            try {
                Class<?> forNameClass = Class.forName((String) inYamlClassName.get(beanName));
                classMap.put(beanName, forNameClass.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return classMap;
    }

}

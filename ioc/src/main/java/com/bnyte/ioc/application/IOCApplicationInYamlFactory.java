package com.bnyte.ioc.application;

import com.bnyte.ioc.analyze.AnalyzeYaml;
import com.bnyte.ioc.application.impl.IOCApplicationInAnnotation;
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

    protected IOCApplicationInYamlFactory() {
    }

    /**
     * 加载所有由配置文件加载的ioc容器bean组件
     * @return
     */
    protected static Map<String, Object> initIOCData() {
        // 创建返回值对象
        Map<String, Object> classMap = new HashMap<>();
        // 获取注解IOC容器对象
        IOCApplicationInAnnotation inAnnotation = new IOCApplicationInAnnotation();
        // 获取到所有读取到的需要加载的IOC容器类, 此处为所有的容器对象进行实例化
        Map<String, Object> inYamlClassName = getIOCApplicationInYaml();
        for (String beanName : inYamlClassName.keySet()) {
            // 判断注解容器中是否存在当前组件, 如果不存在则创建新的示例, 如果存在则将注解容器中的组件赋值给yaml容器中
            if (inAnnotation.getBean(beanName) == null) {
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
            } else {

            // 将注解容器中的组件放到yaml容器中
                classMap.put(beanName, inAnnotation.getBean(beanName));
            }

        }
        return classMap;
    }

}

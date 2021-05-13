package com.bnyte.ioc.application;

import com.bnyte.ioc.analyze.AnalyzeYaml;

import java.util.*;

/**
 * @说明 读取所有yaml读取到的数据
 * @创建日期 2021-05-13 星期四
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class ApplicationDataYamlsFactory extends AnalyzeYaml {

    // ioc容器对象, 通过yaml解析获得, 该容器对象中的数据只会在beans.yaml和bnyteApplication.yaml中获取
    protected static Map<String, Object> iocApplicationInYaml = new HashMap<>();

    /**
     * 获取到通过yaml解析的ioc容器对象
     * @return
     */
    protected static Map<String, Object> getIOCApplicationInYaml() {
        iocApplicationInYaml = handleIOCApplicationInYaml("beans");
        iocApplicationInYaml = handleIOCApplicationInYaml("bnyteApplication");
        return iocApplicationInYaml;
    }

    /**
     * 处理yaml中读取到的ioc容器对象
     * @return
     */
    protected static Map<String, Object> handleIOCApplicationInYaml(String yamlKey) {
        // 判断从yaml全部数据中是否包含指定yaml文件
        if (yamlDatas.get(yamlKey) != null) {
            // 从yaml中读取到的所有数据
            Map<String, Object> yamlsData = (Map<String, Object>) yamlDatas.get(yamlKey);
            /*
                从所有数据中读取到bnyte.application.beans = List<Map<String, Map<String, Object>>>
                因为配置文件中配置如下:
                    bnyte:
                        application:
                            beans:
                                - name: userMapper
                                  value: usermapp
                    配置文件解析:
                        1. 从上可以看到, 当我们获取到所有bean组件时其实是一个List
                        2. 在获取到List集合时候我们可以看到这个List集合中其实是对象, 而每个对象包含了name和value属性
                            而name属性使用来存放beanName的, 而value是用来获取我们的类的全类名的
                            所以我们想通过全类名为此类进行实例化则这是必不可少的
                            因为我们读取yaml的机制, 一个对象在读取之后只会是map
                            进而我们就可以确定他读取到的存储结构了, 结构如下:
                            {
                                bnyte={
                                    application={
                                        beans=[
                                            {
                                                name=userMapper, value=com.bnyte.mapper.UserMapper
                                            },
                                            {
                                                name=userMapper, value=com.bnyte.controller.UserController
                                            }
                                        ]
                                    }
                                }
                            }

             */
            List<Map<String, Object>> readerMap = (List<Map<String, Object>>) ((Map<String, Object>) (((Map<String, Object>) yamlsData.get("bnyte")).get("application"))).get("beans");
            for (Map<String, Object> map : readerMap) {
                iocApplicationInYaml.put((String) map.get("name"), map.get("value"));
            }
        }
        return iocApplicationInYaml;
    }

}

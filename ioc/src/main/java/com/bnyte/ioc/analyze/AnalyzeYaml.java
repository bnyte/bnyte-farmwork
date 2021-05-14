package com.bnyte.ioc.analyze;

import com.bnyte.util.BnyteYamlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 解析Yaml获取到的容器对象
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class AnalyzeYaml {

    /**
     * 改属性用作存储读取到的所有yaml文件
     *  而key为yaml文件名, value为其对应的yaml文件中读到的数据
     */
    protected static Map<String, Object> yamlDatas = new HashMap<>();

    /**
     * 解析单个yaml配置文件
     * @param configFilename yaml核心配置文件数组
     */
    public static void start(String configFilename) {
        // 循环所有的配置文件
        /*
            使用该方式会读取到yaml文件中的所有数据, 而我们通过bnyte.yaml配置的对象是通过如下格式解析
                bnyte:
                    application:
                        beans:
                            -beanName: value
                             beanClass: value
            其他的格式全部当做配置对象如有其他作用将另作修改,
            而yaml数据单独存放在YamlData类中, 而YamlData是通过YamlDataFactory进行解析,
            通过构建YamlData对象获取yaml中的所有数据,
            而通过构建IOCApplicationInYaml对象可以获取到yaml中构建的所有bean组件, 而该类是通过IOCApplicationInYamlFactory进行解析
        */
        // 获取到当前yaml中的所有数据
        Map<String, Object> yamlDataForFilename = BnyteYamlUtils.getYamlDataForFilename(configFilename);
        /*
            当前yaml的数据存放在yaml核心集合容器中, 以配置文件名作为key, 将该配置文件中的数据作为value
                如beans.yaml那么在数据结构中就是map("beans", DATA)
                如果改文件存在于多级目录如com.bnyte.config.beans.yaml
                    那么则数据结构中的就是map("comBnyteConfigBeans", DATA)
         */
        // 解析文件路径获取到该yaml文件对应的数据在容器中的key
        String yamlName = BnyteYamlUtils.formatYamlName(configFilename);
        yamlDatas.put(yamlName, yamlDataForFilename);
    }

    /**
     * 解析多个yaml配置文件
     * @param configFilenames yaml配置文件数组
     */
    public static void start(List<String> configFilenames) {
        // 循环所有的配置文件
        for (String configFilename : configFilenames) {
            /*
                使用该方式会读取到yaml文件中的所有数据, 而我们通过bnyte.yaml配置的对象是通过如下格式解析
                    bnyte:
                        application:
                            beans:
                                -beanName: value
                                 beanClass: value
                其他的格式全部当做配置对象如有其他作用将另作修改,
                而yaml数据单独存放在YamlData类中, 而YamlData是通过YamlDataFactory进行解析,
                通过构建YamlData对象获取yaml中的所有数据,
                而通过构建IOCApplicationInYaml对象可以获取到yaml中构建的所有bean组件, 而该类是通过IOCApplicationInYamlFactory进行解析
            */
            // 获取到当前yaml中的所有数据
            Map<String, Object> yamlDataForFilename = BnyteYamlUtils.getYamlDataForFilename(configFilename);
            /*
                当前yaml的数据存放在yaml核心集合容器中, 以配置文件名作为key, 将该配置文件中的数据作为value
                    如beans.yaml那么在数据结构中就是map("beans", DATA)
                    如果改文件存在于多级目录如com.bnyte.config.beans.yaml
                        那么则数据结构中的就是map("comBnyteConfigBeans", DATA)
             */
            // 解析文件路径获取到该yaml文件对应的数据在容器中的key
            String yamlName = BnyteYamlUtils.formatYamlName(configFilename);
            yamlDatas.put(yamlName, yamlDataForFilename);
        }
    }
}

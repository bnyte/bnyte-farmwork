package com.bnyte;

import com.bnyte.ioc.analyze.AnalyzeAnnotation;
import com.bnyte.ioc.analyze.AnalyzeYaml;

import java.util.List;

/**
 * @说明 bnyte项目启动类
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */

/*
    当前项目完成功能小结:
        1. 完成扫描四大注解并未携带四大注解的bean进行添加到IOC容器中
        2. 完成扫描yaml文件(并支持同时扫描多个文件)将bean注册到IOC容器中(仅扫描beans.yaml & bnyteApplication.yaml)
    TODO 1. 从配置文件中读取的其他内容还未解决, 只是解决了IOC容器
    TODO 2. 编写@Autowried注解实现自动注入以及编写@Value注解实现为属性从配置文件中取值赋值
    TODO 3. 当扫描多个yaml文件时, 如果不是ioc容器中的bean组件需要将其放在yaml中的归类
    TODO ...
    TODO
    TODO
 */
public class BnyteApplication extends AnalyzeAnnotation {

    /**
     * 该方式表示启用注解并只加载一个指定的配置文件
     * @param type 启动类
     * @param configFilename 配置文件名
     */
    public static void run(Class<?> type, String configFilename) {
        // 1. 读取注解
        AnalyzeAnnotation.start(type);
        // 2. 解析yaml配置文件
        AnalyzeYaml.start(configFilename);
    }

    /**
     * 该方式表示加载多个配置文件的同时也会使用注解来进行配置ioc容器
     *  1. 加载启动类
     *  2. 加载指定配置文件
     * @param type 启动类的字节码对象
     * @param configFilenames 指定配置文件
     */
    public static void run(Class<?> type, List<String> configFilenames) {
        // 1. 读取注解
        AnalyzeAnnotation.start(type);
        // 2. 解析yaml配置文件
        AnalyzeYaml.start(configFilenames);
    }

    /**
     * 仅加载启动类, 该重载方法表示仅使用注解方式注入ioc容器
     * @param type 启动类的类对象
     */
    public static void run(Class<?> type) {
        // 1. 读取注解
        AnalyzeAnnotation.start(type);
    }
}

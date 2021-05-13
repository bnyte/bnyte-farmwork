package com.bnyte;

import com.bnyte.anno.scan.Component;
import com.bnyte.anno.scan.Controller;
import com.bnyte.anno.scan.Repository;
import com.bnyte.anno.scan.Service;
import com.bnyte.ioc.analyze.AnalyzeAnnotation;

import java.util.Set;

/**
 * @说明 bnyte项目启动类
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BnyteApplication extends AnalyzeAnnotation {

    public static void run(Class<?> type) {
        // 1. 读取注解
        AnalyzeAnnotation.start(type);
        // 2. 解析yaml

        // 读取yaml
        // TODO 读取yaml将yaml解析的对象放到IOC容器中
    }
}

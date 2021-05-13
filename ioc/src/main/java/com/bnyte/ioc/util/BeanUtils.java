package com.bnyte.ioc.util;

/**
 * @说明 为Bean创建的工具类
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BeanUtils {


    public static String getBeanName(String classname) {
        StringBuilder beanName = new StringBuilder();
        // 最后一次出现 " . " 的索引
        int lastIndexOf = classname.lastIndexOf(".");
        String tmpBeanName = classname.substring(lastIndexOf + 1);
        // 获取第一个字符
        String firstChar = String.valueOf(tmpBeanName.charAt(0));
        // 将第一个字符转换为小写
        firstChar = firstChar.toLowerCase();
        beanName.append(firstChar).append(tmpBeanName.substring(1));
        return beanName.toString();
    }

}

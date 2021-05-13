package com.bnyte.anno.get;

/**
 * @说明 在类的属性中添加该注解会将注解中的value属性值赋值给该属性,
 *       而如果该value以'${}'格式修饰则是从yaml中获取值,
 *       TRUE为必须, FALSE为必须
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public @interface Value {
    String value();
}

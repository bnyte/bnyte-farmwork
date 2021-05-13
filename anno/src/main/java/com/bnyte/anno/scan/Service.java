package com.bnyte.anno.scan;

import java.lang.annotation.*;

/**
 * @说明 扫描普通类
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
@Target(ElementType.TYPE_USE)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

}

package com.bnyte.test;

import com.bnyte.anno.scan.Component;
import com.bnyte.anno.scan.Service;
import com.bnyte.util.ClazzUtils;

import java.util.Set;

/**
 * @说明
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
@Service
public class PackageScanTest {

    public static void main(String[] args) {
        String name = ClazzUtils.class.getPackage().getName();
        Set<String> className = ClazzUtils.getClassName(name, true);
        for (String s : className) {
            System.out.println(s);
        }
    }


}

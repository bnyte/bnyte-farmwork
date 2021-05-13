package com.bnyte.util.i6ce;

import java.io.Serializable;

/**
 * @说明 数组公共集合接口
 * @创建日期 2021-05-07 星期五
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public interface List<T> extends Serializable {

    /**
     * 获取数组长度
     * @return 查看当前数组长度
     */
    int size();

    /**
     * 为当前集合添加一条新的数据
     * @param t 添加的数据
     * @return 添加之后的数据返回给对象
     */
    Boolean add(T t);

}

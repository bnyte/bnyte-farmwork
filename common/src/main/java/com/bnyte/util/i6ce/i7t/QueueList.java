package com.bnyte.util.i6ce.i7t;

import com.bnyte.anno.scan.Service;
import com.bnyte.util.i6ce.List;

import java.util.Arrays;

/**
 * @说明 有序且排序的数组集合
 * @创建日期 2021-05-07 星期五
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class QueueList implements List<Integer>  {

    // 默认的初始化数组长度
    private int expansionSize = 8;

    // 默认的初始数组
    private Integer[] defaultQueueList;

    // 当前为空的索引下表
    private int currentNullIndex;

    // 结果数组
    private Integer[] resultQueueList;

    // 构造器
    public QueueList() {
        currentNullIndex = 0;
    }   

    // 获取数组长度
    public int size() {
        return currentNullIndex;
    }

    // 添加数据
    public Boolean add(Integer addData) {
        // 如果是第一次使用添加方法则为当前集合进行初始化
        if (currentNullIndex == 0) {
            // 为当前集合进行初始化长度
            defaultQueueList = new Integer[expansionSize];
            // 直接将当前需要添加的数据放在第一个索引中即可
            defaultQueueList[currentNullIndex] = addData;
            // 为当前为空下表 + 1
            currentNullIndex += 1;
            // 将当前插入的数据赋值给返回数据
            for (int i = 0; i < currentNullIndex; i++) {
                resultQueueList = new Integer[currentNullIndex];
                // 将数组中有数据的真实数据移动给返回的对象
                resultQueueList[i] = defaultQueueList[i];
            }
            return true;
        } else {
            // 判断当前真实对象数据的剩余长度是否大于4, 如果大于说明当前数组无需进行扩容, 直接添加即可
            if (currentNullIndex + 4 > expansionSize) {
                expansionSize *= 2;
            }
            // 创建临时存储对象数据对象
            defaultQueueList = new Integer[expansionSize];
            // 先获取到所有的已有数据, 然后将已有数据保存到临时存放数据中
            if (currentNullIndex >= 0) System.arraycopy(resultQueueList, 0, defaultQueueList, 0, currentNullIndex);
            // 然后将需要添加的数据添加到临时对象数组中
            defaultQueueList[currentNullIndex] = addData;
            // 更新当前的为空下表索引
            currentNullIndex += 1;
            // 创建当前返回对象的数据
            resultQueueList = new Integer[currentNullIndex];
            // 将当前插入的数据赋值给返回数据
            // 将数组中有数据的真是数据移动给返回的对象
            System.arraycopy(defaultQueueList, 0, resultQueueList, 0, currentNullIndex);
            // 排序
            bubbleSort();
            return true;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(resultQueueList);
    }

    // 冒泡排序
    public void bubbleSort() {
        for (int i = 0; i < currentNullIndex; i++) {
            for (int j = 0; j < currentNullIndex - i  - 1; j++) {
                if (resultQueueList[j] > resultQueueList[j+1]) {
                    int tmp = resultQueueList[j+1];
                    resultQueueList[j+1] = resultQueueList[j];
                    resultQueueList[j] = tmp;
                }
            }
        }
    }
}

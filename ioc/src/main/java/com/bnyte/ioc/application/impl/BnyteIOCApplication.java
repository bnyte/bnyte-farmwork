package com.bnyte.ioc.application.impl;

import com.bnyte.ioc.application.BnyteIOCApplicationFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @说明 yaml容器和注解容器中的对象都会放在这里面
 * @创建日期 2021-05-12 星期三
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class BnyteIOCApplication extends BnyteIOCApplicationFactory {

    public BnyteIOCApplication() {

    }

    /**
     * 去重bean组件
     */
    protected void deDuplicationBean() {
        // 获取注解容器
        IOCApplicationInAnnotation inAnnotation = new IOCApplicationInAnnotation();
        iocBean.put("iocBeanInAnnotation", inAnnotation.getIocBeanInAnno());
        IOCApplicationInYaml iocApplicationInYaml = new IOCApplicationInYaml();
        // 再添加yaml容器之前判断yaml容器中的数据是否已经在ioc容器中存在
        for (String key : inAnnotation.getIocBeanInAnno().keySet()) {
            // 判断通过yaml读取到的组件是中是否有和注解扫描中的组件一致(beanName)
            if (iocApplicationInYaml.getBean(key) != null) {
                // 如果注解容器中已经存在那就将注解容器对象中的值赋值给yaml容器对象中的值
                iocApplicationInYaml.getIocBeanInYaml().put(key, inAnnotation.getBean(key));
            }
        }
        iocBean.put("iocBeanInYaml", iocApplicationInYaml.getIocBeanInYaml());
    }

    /**
     * 通过beanname获取到bean组件
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        // 获取所有bean组件
        Map<String, Object> iocBeans = getIOCBeans();
        // 获取组件对象并返回
        return iocBeans.get(beanName);
    }

    /**
     * 获取IOC容器中的所有对象, 该容器中包含了yaml中读取的和注解解析到的
     * @return
     */
    protected Map<String, Object> getIOCBeans() {
        // 对象去重
        deDuplicationBean();
        // 常见返回值对象
        HashMap<String, Object> resultData = new HashMap<>();
        // 循环当前所有IOC容器中的两个容器, 这两个容器包括 yaml 和 注解
        for (String iocName : iocBean.keySet()) {
            // 获取到当前的容器名
            Map<String, Object> currentIOCBeanName = iocBean.get(iocName);
            // 通过当前容器名获取bean组件
            for (String currentBeanName : currentIOCBeanName.keySet()) {
                // 将所有bean组件封装成一个对象中, 方便数据访问
                resultData.put(currentBeanName, currentIOCBeanName.get(currentBeanName));
            }
        }
        return resultData;
    }

}

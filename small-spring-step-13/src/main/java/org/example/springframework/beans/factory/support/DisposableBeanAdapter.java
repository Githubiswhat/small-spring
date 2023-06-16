package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private Object bean;

    private String beanName;

    private String destroyMethod;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethod = beanDefinition.getDestroyMethod();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethod) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethod))){
            Method method = bean.getClass().getMethod(destroyMethod);
            if (method == null){
                throw new BeanException("no such destroy method " + destroyMethod);
            }
            method.invoke(bean);
        }
    }
}

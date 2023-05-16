package org.example.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableAdapter implements DisposableBean {

    private String beanName;

    private Object bean;

    private String destroyMethod;

    public DisposableAdapter(String beanName, Object bean, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.destroyMethod = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethod) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethod))) {
            Method method = bean.getClass().getMethod(destroyMethod);
            if (method == null) {
                throw new BeansException("don't hava destroy method");
            }
            method.invoke(bean);
        }
    }
}

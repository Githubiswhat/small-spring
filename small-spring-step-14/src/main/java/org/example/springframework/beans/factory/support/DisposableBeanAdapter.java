package org.example.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.BeanDefinition;

import javax.security.auth.Destroyable;
import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private String destroyMethod;

    private final String beanName;

    public DisposableBeanAdapter(Object bean, BeanDefinition beanDefinition, String beanName) {
        this.bean = bean;
        this.destroyMethod = beanDefinition.getDestroyMethodName();
        this.beanName = beanName;
    }


    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethod) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethod))){
            Method method = bean.getClass().getMethod(destroyMethod);
            if (method == null){
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethod + "' on bean with name '" + beanName + "'");
            }
            method.invoke(bean);
        }

    }
}

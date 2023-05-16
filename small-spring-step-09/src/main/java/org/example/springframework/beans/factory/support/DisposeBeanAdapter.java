package org.example.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeanException;
import org.example.springframework.beans.factory.DisposableBan;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposeBeanAdapter implements DisposableBan {

    private Object bean;

    private String beanName;

    private String destroyMethod;

    public DisposeBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethod = beanDefinition.getDestroyMethod();
    }


    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBan) {
            ((DisposableBan) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethod) && !(bean instanceof DisposableBan && "destroy".equals(destroyMethod))) {
            Method method = bean.getClass().getMethod(destroyMethod);
            if (method == null) {
                throw new BeanException("no such destroy method " + destroyMethod);
            }
            method.invoke(bean);
        }
    }
}

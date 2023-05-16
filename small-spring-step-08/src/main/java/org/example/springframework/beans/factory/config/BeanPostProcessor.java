package org.example.springframework.beans.factory.config;

public interface BeanPostProcessor {

    Object postProcessBeanBeforeInitialize(Object bean, String beanName);

    Object postProcessBeanAfterInitialize(Object bean, String beanName);

}

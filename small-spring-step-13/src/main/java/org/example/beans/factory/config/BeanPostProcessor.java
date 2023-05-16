package org.example.beans.factory.config;

public interface BeanPostProcessor {

    Object postProcessBeanBeforeInitialization(Object bean, String beanName);

    Object postProcessBeanAfterInitialization(Object bean, String beanName);

}

package org.example.springframework.beans.factory.config;

public interface BeanPostProcessor {

    Object postProcessBeanBeforeInitialize(Object bean, String name);

    Object postProcessBeanAfterInitialize(Object bean, String name);

}

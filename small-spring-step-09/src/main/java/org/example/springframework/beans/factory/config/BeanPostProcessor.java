package org.example.springframework.beans.factory.config;

public interface BeanPostProcessor {

    Object postProcessorBeanBeforeInitialize(Object bean, String name);

    Object postProcessorBeanAfterInitialize(Object bean, String name);

}

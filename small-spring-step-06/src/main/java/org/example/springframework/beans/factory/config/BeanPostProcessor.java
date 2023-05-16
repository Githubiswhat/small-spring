package org.example.springframework.beans.factory.config;

public interface BeanPostProcessor {

    Object postProcessBeforeInitial(Object bean, String beanName);

    Object postProcessAfterInitial(Object bean, String beanName);
}

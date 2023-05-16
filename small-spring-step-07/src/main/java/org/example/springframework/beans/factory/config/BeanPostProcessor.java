package org.example.springframework.beans.factory.config;

public interface BeanPostProcessor {


    Object postProcessBeforeInitialize(Object bean, String name);

    Object postProcessAfterInitialize(Object bean, String name);

}

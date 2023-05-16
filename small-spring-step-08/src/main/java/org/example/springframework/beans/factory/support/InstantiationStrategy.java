package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String name, Constructor constructor, Object[] args);

}

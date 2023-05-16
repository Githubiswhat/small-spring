package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String className, Constructor ctor, Object[] args);
}

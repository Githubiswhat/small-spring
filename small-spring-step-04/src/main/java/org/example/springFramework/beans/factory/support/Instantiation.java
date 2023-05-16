package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface Instantiation {
    Object instantiate(BeanDefinition beanDefinition, String name, Constructor constructor, Object[] args);

}

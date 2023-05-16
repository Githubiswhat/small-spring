package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiation implements Instantiation {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String name, Constructor constructor, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (constructor == null) {
                return clazz.getConstructor().newInstance();
            } else {
                return clazz.getConstructor(constructor.getParameterTypes()).newInstance();
            }
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

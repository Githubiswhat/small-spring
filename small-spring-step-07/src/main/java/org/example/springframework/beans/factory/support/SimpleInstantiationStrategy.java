package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiates(BeanDefinition beanDefinition, String name, Constructor constructor, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            if (constructor != null) {
                return beanClass.getConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return beanClass.getConstructor().newInstance();
            }
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new BeansException("bean simpleInstantiate failed");
        }
    }
}

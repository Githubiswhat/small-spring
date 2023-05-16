package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiation implements Instantiation {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String name, Constructor construct, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            if (construct == null) {
                return beanClass.getConstructor().newInstance();
            } else {
                return beanClass.getConstructor(construct.getParameterTypes()).newInstance();
            }
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new BeanException("instantiation bean failed " + name);
        }
    }
}

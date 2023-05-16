package org.example.beans.factory.support;

import org.example.beans.factory.config.BeanDefinition;
import org.example.beans.factory.config.BeanDefinitionRegistry;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String name, Constructor construct, Object[] args);

}

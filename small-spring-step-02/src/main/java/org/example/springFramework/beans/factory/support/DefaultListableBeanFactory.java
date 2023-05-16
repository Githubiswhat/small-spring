package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.BeanException;
import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.util.HashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    HashMap<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String name, BeanDefinition definition) {
        beanDefinitionMap.put(name, definition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        BeanDefinition beanDefinition;
        beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeanException("There don't hava " + name + " beanDefinition");
        }
        return beanDefinition;
    }
}

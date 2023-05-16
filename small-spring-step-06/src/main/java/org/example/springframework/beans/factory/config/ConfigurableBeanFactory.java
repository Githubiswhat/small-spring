package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry, HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    BeanDefinition getBeanDefinition(String name);
}

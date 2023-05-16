package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.HierarchicalBeanFactory;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCORE_SINGLETON = "singleton";

    String SCORE_PROTOTYPE = "prototype";

    void destroySingletons();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}

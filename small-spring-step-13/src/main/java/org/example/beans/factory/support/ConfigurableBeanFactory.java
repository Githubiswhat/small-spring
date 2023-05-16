package org.example.beans.factory.support;

import org.example.beans.factory.HierarchicalBeanFactory;
import org.example.beans.factory.config.BeanPostProcessor;
import org.example.beans.factory.config.SingletonBeanRegistry;
import org.example.context.support.ApplicationContextAwareProcessor;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCORE_SINGLETON = "singleton";

    String SCORE_PROTOTYPE = "prototype";

    void destroySingletons();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}

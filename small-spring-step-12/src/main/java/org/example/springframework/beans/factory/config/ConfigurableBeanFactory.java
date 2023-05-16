package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCORE_SINGLETON = "singleton";

    String SCORE_PROTOTYPE = "prototype";

    void destroySingletons();
}

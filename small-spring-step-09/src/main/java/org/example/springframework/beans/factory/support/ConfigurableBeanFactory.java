package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.HierarchicalBeanFactory;
import org.example.springframework.beans.factory.config.SingletonDeanRegistry;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonDeanRegistry {

    String SCORE_SINGLETON = "singleton";

    String SCORE_PROTOTYPE = "prototype";

    void destroySingletons();
}

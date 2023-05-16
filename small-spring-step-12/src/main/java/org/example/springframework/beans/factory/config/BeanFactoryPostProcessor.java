package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(BeanFactory beanFactory);
}

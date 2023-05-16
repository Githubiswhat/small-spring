package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory);

}

package org.example.springframework.beans.factory;

import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory, ConfigurableBeanFactory, ListableBeanFactory {

    void preInstantiateSingleton();


    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}

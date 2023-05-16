package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessBeforeInitialization(Object bean, String name);

    Object applyBeanPostProcessAfterInitialization(Object bean, String name);

}

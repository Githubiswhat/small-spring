package org.example.beans.factory.config;

import org.example.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    Object applyBeanPostProcessAfterInitialization(Object bean, String beanName) throws Exception;

    void destroySingletons();
}

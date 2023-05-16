package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorBeforeInitiate(Object bean, String beanName);

    Object applyBeanPostProcessorAfterInitiate(Object bean, String beanName);

}

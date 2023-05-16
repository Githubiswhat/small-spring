package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object applyBeanPostProcessorBeforeInitial(Object bean, String beanName);

    Object applyBeanPostProcessorAfterInitial(Object bean, String beanName);

}

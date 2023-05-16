package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyPostProcessorBeforeInitialize(Object bean, String name);

    Object applyPostProcessorAfterInitialize(Object bean, String name);

}

package org.example.springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    Object applyBeanPostProcessAfterInitialization(Object bean, String beanName) throws BeanException;

}

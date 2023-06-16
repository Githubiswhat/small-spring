package org.example.springframework.beans.factory;

import cn.hutool.core.bean.BeanException;
import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory, ListableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons() throws BeanException;

    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

}

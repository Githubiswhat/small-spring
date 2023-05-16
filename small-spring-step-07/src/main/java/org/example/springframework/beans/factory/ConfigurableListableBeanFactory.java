package org.example.springframework.beans.factory;

import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {
    void preInstantiateSingletonBean();

}

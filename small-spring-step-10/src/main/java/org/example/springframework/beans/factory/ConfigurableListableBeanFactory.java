package org.example.springframework.beans.factory;

import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.support.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingleton();
}

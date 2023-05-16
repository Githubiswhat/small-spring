package org.example.beans.factory;

import org.example.beans.factory.config.AutowireCapableBeanFactory;
import org.example.beans.factory.support.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory, ListableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons();

}

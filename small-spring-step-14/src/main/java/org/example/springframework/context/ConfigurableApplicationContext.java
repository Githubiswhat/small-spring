package org.example.springframework.context;

import org.example.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();

}

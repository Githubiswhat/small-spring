package org.example.springframework.context;

import org.example.springframework.beans.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh() throws BeanException;

    void close();

    void registerShutdownHook();

}

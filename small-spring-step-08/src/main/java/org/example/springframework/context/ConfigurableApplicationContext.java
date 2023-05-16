package org.example.springframework.context;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();

    void close();

    void registerShutdownHook();

}

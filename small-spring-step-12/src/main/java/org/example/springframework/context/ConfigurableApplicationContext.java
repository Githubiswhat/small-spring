package org.example.springframework.context;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void close();

    void refresh() throws Exception;

    void registerShutdownHook();

}

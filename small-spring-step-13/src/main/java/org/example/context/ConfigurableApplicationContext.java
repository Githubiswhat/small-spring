package org.example.context;

public interface ConfigurableApplicationContext extends ApplicationContext{

    void refresh() throws Exception;

    void close();

    void registerShutdownHooker();

}

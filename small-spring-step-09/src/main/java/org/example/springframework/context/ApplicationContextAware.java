package org.example.springframework.context;

public interface ApplicationContextAware extends ApplicationContext {

    void setApplicationContext(ApplicationContext applicationContext);

}

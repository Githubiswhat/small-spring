package org.example.springframework.context;

import org.example.springframework.beans.factory.config.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext);
}

package org.example.springframework.context.event;

import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;

public interface ApplicationEventMultiCaster {

    void addApplicationEventListener(ApplicationListener<?> applicationEventListener);

    void removeApplicationEventListener(ApplicationListener<?> applicationEventListener);

    void multicaster(ApplicationEvent event);

}

package org.example.springframework.context.event;

import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;

public interface ApplicationEventMultiCaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);

}

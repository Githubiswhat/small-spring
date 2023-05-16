package org.example.springframework.context.event;

import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;

public interface ApplicationEventMultiCaster {

    void addListener(ApplicationListener<?> listener);

    void removeListener(ApplicationListener<?> listener);

    void multiCasterEvent(ApplicationEvent event);

}

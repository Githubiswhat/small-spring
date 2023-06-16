package org.example.springframework.context.event;

import org.example.springframework.context.ApplicationContext;
import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.support.ApplicationContextAwareProcessor;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object resource) {
        super(resource);
    }

    public final ApplicationContext getApplicationContext() {
        return ((ApplicationContext) this.getSource());
    }
}

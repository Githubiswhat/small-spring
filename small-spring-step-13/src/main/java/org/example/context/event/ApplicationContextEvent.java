package org.example.context.event;

import org.example.context.ApplicationContext;
import org.example.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}

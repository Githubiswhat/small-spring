package org.example.springframework.context.event;

public class ContextClosedEvent extends ApplicationContextEvent{

    public ContextClosedEvent(Object resource) {
        super(resource);
    }
}

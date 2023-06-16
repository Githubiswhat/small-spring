package org.example.springframework.context.event;

public class ContextRefreshedEvent extends ApplicationContextEvent{

    public ContextRefreshedEvent(Object resource) {
        super(resource);
    }
}

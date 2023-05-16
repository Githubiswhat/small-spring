package org.example.springframework.context;

public interface ApplicationEventPublisher {

    void publish(ApplicationEvent event);

}

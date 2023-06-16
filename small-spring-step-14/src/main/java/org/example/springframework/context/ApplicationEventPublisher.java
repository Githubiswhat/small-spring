package org.example.springframework.context;

public interface ApplicationEventPublisher{

    void publishApplicationEvent(ApplicationEvent event);

}

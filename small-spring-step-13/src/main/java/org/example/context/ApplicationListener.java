package org.example.context;

public interface ApplicationListener<E extends ApplicationEvent> {

    void onApplicationEvent(E event);
}

package org.example.context.event;

import org.example.context.ApplicationEvent;
import org.example.context.ApplicationListener;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMultiCaster implements ApplicationEventMulticaster{

    private Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    @Override
    public void addListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeListener(ApplicationListener listener) {
        applicationListeners.remove((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }
}

package org.example.context.event;

import org.example.context.ApplicationEvent;
import org.example.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addListener(ApplicationListener<?> listener);

    void removeListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}

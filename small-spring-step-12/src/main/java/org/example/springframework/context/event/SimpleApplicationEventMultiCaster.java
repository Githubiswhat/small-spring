package org.example.springframework.context.event;

import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;

public class SimpleApplicationEventMultiCaster extends AbstractApplicationEventMultiCaster {

    public SimpleApplicationEventMultiCaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicaster(ApplicationEvent event) {
        for (ApplicationListener applicationListener : getApplicationListeners(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}

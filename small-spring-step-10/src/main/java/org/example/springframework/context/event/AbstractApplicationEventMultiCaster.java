package org.example.springframework.context.event;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.beans.factory.BeanFactoryAware;
import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;
import org.example.springframework.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractApplicationEventMultiCaster implements ApplicationEventMultiCaster, BeanFactoryAware {

    private final Set<ApplicationListener<ApplicationContextEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationContextEvent>) listener);
    }

    @Override
    public void removeListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected LinkedList<ApplicationListener> getApplicationListeners(org.example.springframework.context.ApplicationEvent event) {
        LinkedList<ApplicationListener> allApplicationListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationContextEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, event)) {
                allApplicationListeners.add(applicationListener);
            }
        }
        return allApplicationListeners;
    }

    protected boolean supportsEvent(ApplicationListener<ApplicationContextEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
        Class<?> targetClass = ClassUtil.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;

        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name");
        }

        return eventClassName.isAssignableFrom(event.getClass());
    }
}

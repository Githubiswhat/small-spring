package org.example.springframework.context.event;

import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.beans.factory.BeanFactoryAware;
import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;
import org.example.springframework.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractApplicationEventMultiCaster implements ApplicationEventMultiCaster, BeanFactoryAware {

    private LinkedHashSet<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationEventListener(ApplicationListener<?> applicationEventListener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) applicationEventListener);
    }

    @Override
    public void removeApplicationEventListener(ApplicationListener<?> applicationEventListener) {
        applicationListeners.remove(applicationEventListener);
    }

    public List<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allApplicationListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportApplicationEvent(applicationListener, event)) {
                allApplicationListeners.add(applicationListener);
            }
        }
        return allApplicationListeners;
    }

    private boolean supportApplicationEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
        Class<?> targetClass = ClassUtil.isCglibSubClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClass;
        try {
            eventClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventClass.isAssignableFrom(event.getClass());
    }

}

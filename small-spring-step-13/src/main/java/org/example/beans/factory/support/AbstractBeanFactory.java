package org.example.beans.factory.support;

import org.example.beans.factory.config.BeanDefinition;
import org.example.beans.factory.config.BeanPostProcessor;
import org.example.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory{

    private static final ClassLoader defaultClassLoader = ClassUtil.getDefaultClassLoader();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object[] args) {
        return doGetBean(name, args);
    }

    private Object doGetBean(String name, Object[] args) {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null){
            return getObjectForFactoryBean(sharedInstance);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        sharedInstance = createBean(beanDefinition, name, args);
        return getObjectForFactoryBean(sharedInstance);
    }

    protected abstract Object createBean(BeanDefinition beanDefinition, String name, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);


    private Object getObjectForFactoryBean(Object sharedInstance) {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<?> requestedType) {
        return (T) getBean(name);
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return beanPostProcessors;
    }

    public ClassLoader getDefaultClassLoader(){
        return defaultClassLoader;
    }
}

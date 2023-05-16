package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.FactoryBean;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.example.springframework.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private static final ClassLoader classLoader = ClassUtil.getDefaultClassLoader();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object[] args) {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requestedType) {
        return (T) getBean(name);
    }

    private Object doGetBean(String name, Object[] args) {
        Object shearedSingleton = getSingletonBean(name);
        if (shearedSingleton != null) {
            return getObjectForBeanInstance(name, shearedSingleton);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        shearedSingleton = createBean(beanDefinition, name, args);
        return getObjectForBeanInstance(name, shearedSingleton);
    }

    protected abstract Object createBean(BeanDefinition beanDefinition, String name, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);

    private Object getObjectForBeanInstance(String name, Object shearedSingleton) {
        if (!(shearedSingleton instanceof FactoryBean)) {
            return shearedSingleton;
        }
        Object bean = getCacheObjectFromFactoryBean(name);
        if (bean == null) {
            FactoryBean factorybean = (FactoryBean) shearedSingleton;
            bean = getObjectFromFactoryBean(name, factorybean);
        }
        return bean;
    }

    public ClassLoader getDefaultClassLoader() {
        return classLoader;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    protected List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

}



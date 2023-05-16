package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.FactoryBean;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private static ClassLoader defaultClassLoader = ClassUtil.getDefaultClassLoader();
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
        Object sharedBean = getSingleton(name);
        if (sharedBean != null) {
            return getObjectForBeanInstantiate(sharedBean, name);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        sharedBean = createBean(beanDefinition, name, args);
        return getObjectForBeanInstantiate(sharedBean, name);
    }

    protected abstract Object createBean(BeanDefinition beanDefinition, String name, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);

    private Object getObjectForBeanInstantiate(Object sharedBean, String name) {
        if (!(sharedBean instanceof FactoryBean)) {
            return sharedBean;
        }

        Object object = getBeanObjectFromCache(name);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) sharedBean;
            object = getBeanObjectFromFactoryBean(factoryBean, name);
        }
        return object;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return (T) getBean(name);
    }


    protected ClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    protected List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
}

package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.FactoryBean;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private ClassLoader classLoader = ClassUtil.getDefaultClassLoader();

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object[] args) {
        return doGetBean(name, args);
    }

    private Object doGetBean(String name, Object[] args) {
        Object sharedBeanObject = getSingleton(name);
        if (sharedBeanObject != null) {
            return getObjectForBeanInstance(sharedBeanObject, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        sharedBeanObject = createBean(beanDefinition, name, args);
        return getObjectForBeanInstance(sharedBeanObject, name);
    }

    private Object getObjectForBeanInstance(Object sharedBeanObject, String name) {
        if (!(sharedBeanObject instanceof FactoryBean)) {
            return sharedBeanObject;
        }

        Object cachedObject = getCachedObjectFromFactoryBean(name);
        if (cachedObject == null) {
            FactoryBean factoryBean = (FactoryBean) sharedBeanObject;
            getObjectFromFactoryBean(factoryBean, name);
        }
        return cachedObject;
    }

    protected abstract Object createBean(BeanDefinition beanDefinition, String name, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return (T) getBean(name);
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getDefaultClassLoader() {
        return classLoader;
    }

}

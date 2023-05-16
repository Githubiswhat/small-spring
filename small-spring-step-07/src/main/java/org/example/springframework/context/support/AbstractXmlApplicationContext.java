package org.example.springframework.context.support;

import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        String[] locations = getConfigLocation();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        xmlBeanDefinitionReader.loadBeanDefinition(locations);
    }

    protected abstract String[] getConfigLocation();
}

package org.example.springframework.context.support;

import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplication {

    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(this, beanFactory);
        String[] locations = getConfigLocation();
        if (locations != null) {
            xmlBeanDefinitionReader.loadBeanDefinition(locations);
        }
    }

    protected abstract String[] getConfigLocation();
}

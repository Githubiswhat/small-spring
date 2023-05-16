package org.example.context.support;

import cn.hutool.json.XML;
import org.example.beans.factory.support.DefaultListableBeanFactory;
import org.example.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanFactory(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] locations = getConfigLocation();
        if (locations != null){
            xmlBeanDefinitionReader.loadBeanDefinition(locations);
        }
    }

    protected abstract String[] getConfigLocation();
}

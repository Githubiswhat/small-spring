package org.example.beans.factory.xml;

import org.example.beans.factory.config.BeanDefinitionRegistry;
import org.example.beans.factory.support.AbstactBeanDefinitionReader;
import org.example.core.io.DefatultResourceLoader;
import org.example.core.io.Resource;
import org.example.core.io.ResourceLoader;

public class XmlBeanDefinitionReader extends AbstactBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinition(String location) {
        DefatultResourceLoader defatultResourceLoader = new DefatultResourceLoader();
        Resource resource = defatultResourceLoader.getResource(location);
        loadBeanDefinition(resource);
    }

    @Override
    public void loadBeanDefinition(String... locations) {
        for (String location : locations) {
            loadBeanDefinition(location);
        }
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        doLoadBeanDefinition(resource);
    }

    private void doLoadBeanDefinition(Resource resource) {
        //todo
    }

    @Override
    public void loadBeanDefinition(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinition(resource);
        }
    }
}

package org.example.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanReference;
import org.example.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.example.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.example.springframework.core.io.Resource;
import org.example.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinition(String location) {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinition(resource);
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinition(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) continue;
            if (!(childNodes.item(i).getNodeName().equals("bean"))) continue;

            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            Class<?> beanClass = Class.forName(className);

            String beanName = StrUtil.isNotEmpty("id") ? id : name;

            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!("property".equals(bean.getChildNodes().item(j).getNodeName()))) continue;

                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                propertyValues.addPropertyValue(propertyValue);
            }

            if (getRegistry().containBeanDefinition(beanName)) {
                throw new BeansException("Duplicate bean definition");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Override
    public void loadBeanDefinition(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinition(resource);
        }
    }

    @Override
    public void loadBeanDefinition(String... locations) {
        for (String location : locations) {
            loadBeanDefinition(location);
        }
    }

}

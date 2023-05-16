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
import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.Resource;
import org.example.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }

    @Override
    public void loadBeanDefinition(String location) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(location);
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
        try {
            doLoadBeanDefinition(resource);
        } catch (ClassNotFoundException | IOException e) {
            throw new BeansException("load beanDefinition xml failed");
        }
    }

    private void doLoadBeanDefinition(Resource resource) throws ClassNotFoundException, IOException {
        Document document = XmlUtil.readXML(resource.getInputStream());
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) continue;
            if (!("bean".equals(childNodes.item(i).getNodeName()))) continue;

            Element bean = (Element) childNodes.item(i);

            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");

            Class beanClass = Class.forName(className);

            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = beanClass.getSimpleName();
            }

            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;

                Element properties = (Element) bean.getChildNodes().item(j);
                String attrName = properties.getAttribute("name");
                String attrValue = properties.getAttribute("value");
                String attrRef = properties.getAttribute("ref");

                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                propertyValues.addPropertyValue(propertyValue);
            }
            if (getBeanDefinitionRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("duplicate bean definition");
            }
            getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Override
    public void loadBeanDefinition(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinition(resource);
        }
    }
}

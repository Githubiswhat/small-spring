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
import java.net.BindException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(resourceLoader, beanDefinitionRegistry);
    }

    @Override
    public void loadBeanDefinition(String location) {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(location);
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
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("load beanDefinition from xml failed");
        }
    }

    private void doLoadBeanDefinition(Resource resource) throws IOException, ClassNotFoundException {
        Document document = XmlUtil.readXML(resource.getInputStream());
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) continue;
            if (!(childNodes.item(i).getNodeName().equals("bean"))) continue;

            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = clazz.getSimpleName();
            }

            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");
            String score = bean.getAttribute("score");

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setScore(score);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setDestroyMethod(destroyMethod);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!(bean.getChildNodes().item(j).equals("property"))) continue;

                Element properties = (Element) bean.getChildNodes().item(j);
                String attrName = properties.getAttribute("name");
                String attrValue = properties.getAttribute("value");
                String attrRef = properties.getAttribute("ref");

                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrName) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                propertyValues.addPropertyValue(propertyValue);
            }

            if (getBeanDefinitionRegistry().containsBeanDefinition(beanName)) {
                throw new BindException("duplicate bean definition");
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

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
import org.example.springframework.core.io.DefatultResourceLoader;
import org.example.springframework.core.io.Resource;
import org.example.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.BindException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry registry) {
        super(resourceLoader, registry);
    }

    @Override
    public void loadBeanDefinition(String location) {
        DefatultResourceLoader resourceLoader = new DefatultResourceLoader();
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
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("loadDefinition from resource failed", e);
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
            String scope = bean.getAttribute("scope");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");

            Class<?> clazz = Class.forName(className);
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = clazz.getSimpleName();
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (StrUtil.isNotEmpty(scope)){
                beanDefinition.setScore(scope);
            }
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setDestroyMethod(destroyMethod);

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!(bean.getChildNodes().item(j).getNodeName().equals("property"))) continue;

                Element properties = (Element) bean.getChildNodes().item(j);
                String attrName = properties.getAttribute("name");
                String attrValue = properties.getAttribute("value");
                String attrRef = properties.getAttribute("ref");
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                propertyValues.addProperty(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BindException("duplicated bean definition");
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
}

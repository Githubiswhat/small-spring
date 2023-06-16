package org.example.springframework.context.support;

import cn.hutool.core.bean.BeanException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] locations;

    public ClassPathXmlApplicationContext(String[] locations) {
        this.locations = locations;
        try {
            refresh();
        } catch (Exception e) {
            throw new BeanException("refresh context failed ",e);
        }
    }

    public ClassPathXmlApplicationContext(String location) {
        this(new String[]{location});
    }

    @Override
    protected String[] getConfigLocation() {
        return locations;
    }
}

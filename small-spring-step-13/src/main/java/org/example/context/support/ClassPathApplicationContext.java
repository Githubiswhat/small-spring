package org.example.context.support;

import cn.hutool.core.bean.BeanException;

public class ClassPathApplicationContext extends AbstractXmlApplicationContext{

    private String[] locations;

    public ClassPathApplicationContext(String[] locations) {
        this.locations = locations;
        try {
            refresh();
        } catch (Exception e) {
            throw new BeanException("refresh context failed ",e);
        }
    }

    public ClassPathApplicationContext(String location) {
        this(new String[]{location});
    }

    @Override
    protected String[] getConfigLocation() {
        return locations;
    }
}

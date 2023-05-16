package org.example.springframework.context.support;

import org.example.springframework.beans.BeansException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] locations;

    public ClassPathXmlApplicationContext(String[] locations) {
        this.locations = locations;
        try {
            refresh();
        } catch (Exception e) {
            throw new BeansException("refresh context failed");
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

package org.example.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configurations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configurations) {
        this(new String[] { configurations });
    }

    public ClassPathXmlApplicationContext(String[] configurations) {
        this.configurations = configurations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configurations;
    }
}

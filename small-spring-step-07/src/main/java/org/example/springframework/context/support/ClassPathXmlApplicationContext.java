package org.example.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocation;

    public ClassPathXmlApplicationContext(String[] configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    @Override
    protected String[] getConfigLocation() {
        return configLocation;
    }

}

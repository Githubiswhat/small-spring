package org.example.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocation;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }


    public ClassPathXmlApplicationContext(String[] configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public String[] getConfigLocations() {
        return configLocation;
    }
}

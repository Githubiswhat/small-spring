package org.example.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] locations;

    public ClassPathXmlApplicationContext(String[] locations) {
        this.locations = locations;
        refresh();
    }


    public ClassPathXmlApplicationContext(String location) {
        this(new String[]{location});
    }

    @Override
    protected String[] getConfigLocations() {
        return locations;
    }
}

package org.example.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValue getProperty(String propertyName) {
        for (PropertyValue pv : propertyValues) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    public void addProperty(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

}

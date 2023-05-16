package org.example.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValues) {
            if (pv.getName().equals(propertyValues)) {
                return pv;
            }
        }
        return null;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

}

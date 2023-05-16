package org.example.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValues() {
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValues.toArray(new PropertyValue[0]);
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    public PropertyValue getPropertyValues(String propertyName) {
        for (PropertyValue pv : propertyValues) {
            if (propertyName.equals(pv.getName())) {
                return pv;
            }
        }
        return null;
    }

}

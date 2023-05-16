package org.example.springframework.beans;

import java.util.ArrayList;

public class PropertyValues {


    ArrayList<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValues) {
            if (propertyName.equals(pv.getName())) {
                return pv;
            }
        }
        return null;
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValues.toArray(new PropertyValue[0]);
    }

}

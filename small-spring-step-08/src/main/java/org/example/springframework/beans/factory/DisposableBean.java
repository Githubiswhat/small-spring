package org.example.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;

public interface DisposableBean {
    void destroy() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}

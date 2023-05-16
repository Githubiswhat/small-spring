package org.example.springframework.beans.factory.support;

public interface DisposableBean {
    void destroy() throws Exception;
}

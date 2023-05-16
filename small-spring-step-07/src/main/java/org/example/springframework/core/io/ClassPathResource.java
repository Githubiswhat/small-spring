package org.example.springframework.core.io;

import cn.hutool.core.lang.Assert;
import org.example.springframework.beans.BeansException;
import org.example.springframework.util.ClassUtil;

import java.io.InputStream;

public class ClassPathResource implements Resource {

    private ClassLoader classLoader;

    private String path;

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "path can not be null");
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtil.getDefaultClassLoader() : classLoader;
    }

    public ClassPathResource(String path) {
        this(path, null);
    }

    @Override
    public InputStream getInputStream() {
        try {
            InputStream is = classLoader.getResourceAsStream(path);
            return is;
        } catch (Exception e) {
            throw new BeansException("cloud not open config");
        }
    }
}

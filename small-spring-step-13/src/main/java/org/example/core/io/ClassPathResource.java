package org.example.core.io;

import cn.hutool.core.lang.Assert;
import org.example.springframework.beans.BeansException;
import org.example.springframework.util.ClassUtil;

import java.io.InputStream;

public class ClassPathResource implements Resource {

    private ClassLoader classLoader;

    private String path;

    public ClassPathResource(ClassLoader classLoader, String path) {
        Assert.notNull(path, "path can not be null");
        this.classLoader = classLoader == null ? ClassUtil.getDefaultClassLoader() : classLoader;
        this.path = path;
    }

    public ClassPathResource(String path) {
        this(null, path);
    }

    @Override
    public InputStream getInputStream() {
        try {
            InputStream is = classLoader.getResourceAsStream(path);
            return is;
        } catch (Exception e) {
            throw new BeansException("get resource from " + path + " failed");
        }
    }
}

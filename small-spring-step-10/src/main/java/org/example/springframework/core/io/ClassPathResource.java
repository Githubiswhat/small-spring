package org.example.springframework.core.io;

import cn.hutool.core.lang.Assert;
import org.example.springframework.util.ClassUtil;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "path can not be null");
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtil.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            return classLoader.getResourceAsStream(path);
        } catch (Exception e) {
            throw new IOException("classpath load failed");
        }
    }
}

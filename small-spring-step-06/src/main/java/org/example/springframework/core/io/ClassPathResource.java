package org.example.springframework.core.io;

import cn.hutool.core.lang.Assert;
import org.example.springframework.beans.BeansException;
import org.example.springframework.util.ClassUtil;

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
        this.classLoader = classLoader != null ? classLoader : ClassUtil.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new BeansException("can not load file", e);
        }
        return is;
    }
}

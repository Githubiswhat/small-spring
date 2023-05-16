package org.example.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {

    private final String CLASS_PATH_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASS_PATH_PREFIX)) {
            return new ClassPathResource(location.substring(CLASS_PATH_PREFIX.length()));
        }
        try {
            return new UrlResource(new URL(location));
        } catch (MalformedURLException e) {
            return new FileSystemResource(location);
        }
    }
}

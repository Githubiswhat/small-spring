package org.example.core.io;

import org.example.springframework.beans.BeansException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileSystemResource implements Resource {

    private File file;

    private String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() {
        try {
            InputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        } catch (FileNotFoundException e) {
            throw new BeansException("get resource from " + path + " failed");
        }
    }
}

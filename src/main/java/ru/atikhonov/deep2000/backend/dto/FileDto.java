package ru.atikhonov.deep2000.backend.dto;

import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Date;

/**
 * DTO  файла
 */
public class FileDto implements Resource {
    private String name;
    private byte[] content;
    private long length;

    /**
     * Конструктор
     * @param name - имя
     * @param content - содержание в виде строки
     */
    public FileDto(String name, String content) {
        this.name = name;
        this.content = content.getBytes();
        this.length = content.length();
    }

    /**
     * Конструктор
     * @param name - имя
     * @param content - содержание
     */
    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public URL getURL() throws IOException {
        return null;
    }

    @Override
    public URI getURI() throws IOException {
        return null;
    }

    @Override
    public File getFile() throws IOException {
        return null;
    }

    @Override
    public long contentLength() throws IOException {
        return length;
    }

    @Override
    public long lastModified() throws IOException {
        return new Date().getTime();
    }

    @Override
    public Resource createRelative(String s) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }
}

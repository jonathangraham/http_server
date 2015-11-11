package com.jgraham.httpserver.ResponseBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileContentsBuilder implements iResponseBuilder {
    private String path;

    public FileContentsBuilder(String path) {
        this.path = path;
    }

    public byte[] getResponse() throws Exception{
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(getStatusLine().getBytes());
        response.write(getHeader().getBytes());
        response.write(getBody());
        return response.toByteArray();
    }

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private byte[] getBody() throws Exception{
        return getFileContent();
    }

    private byte[] getFileContent() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream file = ClassLoader.class.getResourceAsStream(path)) {
            byte[] bytes = new byte[1000];
            int numBytes;
            while ((numBytes = file.read(bytes)) != -1) {
                output.write(bytes, 0, numBytes);
            }
        }
        catch (RuntimeException e) {
            output.write("".getBytes());
        }
        return output.toByteArray();
    }
}

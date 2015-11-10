package com.jgraham.httpserver.ResponseBuilder;

import java.io.InputStream;

public class FileContentsBuilder implements iResponseBuilder {

    private String path;

    public FileContentsBuilder(String path) {
        this.path = path;
    }

    public String getResponse() throws Exception{
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString();
    }

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() throws Exception{
        return getFileContent();
    }

    private String getFileContent() throws Exception {
        StringBuilder output = new StringBuilder();
        try (InputStream file = ClassLoader.class.getResourceAsStream(path)) {
            byte[] bytes = new byte[1000];
            int numBytes;
            while ((numBytes = file.read(bytes)) != -1) {
                String s = new String(bytes, 0, numBytes);
                output.append(s);
            }
        }
        catch (RuntimeException e) {
            output.append("");
        }
        return output.toString();
    }
}

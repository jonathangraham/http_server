package com.jgraham.httpserver.ResponseBuilder;

import java.io.ByteArrayOutputStream;

public class ParameterDecodeBuilder implements iResponseBuilder {
    private String path;

    public ParameterDecodeBuilder(String path) {
        this.path = path;
    }

    public byte[] getResponse() throws Exception {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(getStatusLine().getBytes());
        response.write(getHeader().getBytes());
        response.write(getBody());
        return response.toByteArray();
    }

    public void modifyFile() throws Exception {}

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private byte[] getBody() throws Exception {
        path = path.replaceAll("=", " = ");
        String all_parameters = path.split("\\?")[1];
        String[] parameters = all_parameters.split("&");
        String body = "";
        for (int i = 0; i < parameters.length; i++){
            body += java.net.URLDecoder.decode(parameters[i], "UTF-8");
        }
        return body.getBytes();
    }
}
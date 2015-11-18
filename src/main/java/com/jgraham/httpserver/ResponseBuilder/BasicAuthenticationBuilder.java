package com.jgraham.httpserver.ResponseBuilder;

import java.io.ByteArrayOutputStream;

public class BasicAuthenticationBuilder implements iResponseBuilder {

    public byte[] getResponse() throws Exception {
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

    private byte[] getBody() throws Exception {
        return "GET /log HTTP/1.1\r\nPUT /these HTTP/1.1\r\nHEAD /requests HTTP/1.1".getBytes();

    }
}

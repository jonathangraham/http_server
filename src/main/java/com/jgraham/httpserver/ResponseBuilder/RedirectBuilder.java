package com.jgraham.httpserver.ResponseBuilder;

public class RedirectBuilder implements iResponseBuilder {

    public byte[] getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    private String getStatusLine() {
        return "HTTP/1.1 302 Found\r\n";
    }

    private String getHeader() {
        return "Location: http://localhost:5000/\r\n";
    }

    private String getBody() {
        return "";
    }
}

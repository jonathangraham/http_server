package com.jgraham.httpserver.ResponseBuilder;

public class BasicAuthenticationRequiredBuilder implements iResponseBuilder {

    public byte[] getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    private String getStatusLine() {
        return "HTTP/1.1 401 Unauthorized\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "Authentication required";
    }
}

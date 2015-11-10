package com.jgraham.httpserver.ResponseBuilder;

public class FourOhFourBuilder implements iResponseBuilder {

    public String getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString();
    }

    private String getStatusLine() {
        return "HTTP/1.1 404 Not Found\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }
}

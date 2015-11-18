package com.jgraham.httpserver.ResponseBuilder;

public class FormResponseBuilder implements iResponseBuilder {

    public byte[] getResponse() throws Exception{
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }

}

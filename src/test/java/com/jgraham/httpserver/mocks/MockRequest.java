package com.jgraham.httpserver.mocks;

import com.jgraham.httpserver.iRequest;

public class MockRequest implements iRequest {

    private String request;

    public MockRequest(String request) {
        this.request = request;
    }

    public String getRequest() throws Exception {
        return request;
    }

    public String getRequestType() {
        return request.split("\\s+")[0];
    }

    public String getRequestURL() {
        return request.split("\\s+")[1];
    }
}

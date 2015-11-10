package com.jgraham.httpserver.mocks;

import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;

public class MockResponseBuilder implements iResponseBuilder {

    private String response;

    public MockResponseBuilder(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}

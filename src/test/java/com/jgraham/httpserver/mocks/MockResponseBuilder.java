package com.jgraham.httpserver.mocks;

import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;

public class MockResponseBuilder implements iResponseBuilder {

    private byte[] response;

    public MockResponseBuilder(String response) {
        this.response = response.getBytes();
    }

    public byte[] getResponse() {
        return response;
    }
}

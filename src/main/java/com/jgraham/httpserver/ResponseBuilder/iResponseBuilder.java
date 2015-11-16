package com.jgraham.httpserver.ResponseBuilder;

public interface iResponseBuilder {

    public byte[] getResponse() throws Exception;
    public void modifyFile() throws Exception;
}

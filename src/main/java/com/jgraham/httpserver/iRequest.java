package com.jgraham.httpserver;

public interface iRequest {

    public String getRequest() throws Exception;

    public String getRequestType();

    public String getRequestURL();

}
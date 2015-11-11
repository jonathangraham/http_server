package com.jgraham.httpserver.Requests;

import java.util.Map;

public class Request {
    private String requestType;
    private String requestURL;
    private String httpVersion;

    public Request(Map<String, String> parsedRequestComponents) {
        this.requestType = parsedRequestComponents.get("RequestType");
        this.requestURL = parsedRequestComponents.get("RequestURL");
        this.httpVersion = parsedRequestComponents.get("RequestHTTPVersion");
    }

    public String getRequestType() {
        return requestType;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getHTTPVersion() {
        return httpVersion;
    }

}
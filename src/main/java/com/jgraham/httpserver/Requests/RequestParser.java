package com.jgraham.httpserver.Requests;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private String requestType;
    private String requestURL;
    private String requestHTTPVersion;
    private Map<String,String> parsedRequestComponents = new HashMap<>();

    public void generateParsedRequest(String rawRequest) {
        parseRequest(rawRequest);
        generateParsedRequestComponents();
    }

    public Request getNewRequest() {
        return new Request(parsedRequestComponents);
    }

    public String getRequestType() {
        return requestType;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getRequestHTTPVersion() {
        return requestHTTPVersion;
    }

    public Map<String,String> getParsedRequestComponents() {
        return parsedRequestComponents;
    }

    private void parseRequest(String rawRequest) {
        String[] request = rawRequest.split("\r\n");
        String[] requestLine = request[0].split(" ");
            requestType = requestLine[0];
            requestURL = requestLine[1];
            requestHTTPVersion = requestLine[2];
    }

    private void generateParsedRequestComponents() {
        parsedRequestComponents.put("RequestType", requestType);
        parsedRequestComponents.put("RequestURL", requestURL);
        parsedRequestComponents.put("RequestHTTPVersion", requestHTTPVersion);
    }
}
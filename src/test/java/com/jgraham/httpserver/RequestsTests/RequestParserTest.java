package com.jgraham.httpserver.RequestsTests;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.Requests.RequestParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestParserTest {

    @Test
    public void returnsIndividualParsedRequestComponents() {
        RequestParser requestParser = new RequestParser();
        requestParser.generateParsedRequest("GET / HTTP/1.1\r\n\r\nanything else");
        Assert.assertEquals("GET", requestParser.getRequestType());
        Assert.assertEquals("/", requestParser.getRequestURL());
        Assert.assertEquals("HTTP/1.1", requestParser.getRequestHTTPVersion());
    }

    @Test
    public void returnsParsedRequestComponents() {
        RequestParser requestParser = new RequestParser();
        requestParser.generateParsedRequest("GET / HTTP/1.1");
        Map<String,String> testParsedRequestComponents = new HashMap<>();
        testParsedRequestComponents.put("RequestType", "GET");
        testParsedRequestComponents.put("RequestURL", "/");
        testParsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        Assert.assertEquals(testParsedRequestComponents, requestParser.getParsedRequestComponents());
    }

    @Test
    public void generatesNewRequest() {
        RequestParser requestParser = new RequestParser();
        Request newRequest = requestParser.getNewRequest();
        Assert.assertNotNull(newRequest);
    }
}

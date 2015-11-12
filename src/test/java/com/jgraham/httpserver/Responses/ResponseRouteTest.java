package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ResponseRouteTest {

    @Test
    public void getMethodOptionsBuilderTestWithGet() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/method_options");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getMethodOptionsBuilderTestWithPut() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PUT");
        parsedRequestComponents.put("RequestURL", "/method_options");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getFileDirectoryBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FileDirectoryBuilder(null).getClass());
    }

    @Test
    public void getFileContentsBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/file1");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FileContentsBuilder(null).getClass());
    }

    @Test
    public void getFourOhFourBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/foobar");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FourOhFourBuilder().getClass());
    }

    @Test
    public void getRedirectBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/redirect");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new RedirectBuilder().getClass());
    }

    @Test
    public void getPartialContentTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/partial_content.txt");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Range=0-10");
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "10");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PartialContentBuilder(range, "/partial_content.txt").getClass());
    }

    @Test
    public void getPartialContentRangeTest() {
        Map<String,String> testRange = new HashMap<>();
        testRange.put("Start", "10");
        testRange.put("End", "51");
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(testRange, responseRoute.getPartialContentRange("10-50"));
    }

    @Test
    public void getPartialContentRangeTest2() {
        Map<String,String> testRange = new HashMap<>();
        testRange.put("Start", "50");
        testRange.put("End", "-2");
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(testRange, responseRoute.getPartialContentRange("-50"));
    }

    @Test
    public void getPartialContentRangeTest3() {
        Map<String,String> testRange = new HashMap<>();
        testRange.put("Start", "10");
        testRange.put("End", "-1");
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(testRange, responseRoute.getPartialContentRange("10-"));
    }

    @Test
    public void getPartialContentRangeTest4() {
        Map<String,String> testRange = new HashMap<>();
        testRange.put("Start", "0");
        testRange.put("End", "-1");
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(testRange, responseRoute.getPartialContentRange("-"));
    }

}

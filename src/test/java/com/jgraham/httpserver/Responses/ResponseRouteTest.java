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
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute();
        Assert.assertEquals(responseRoute.getResponseBuilder(request, null).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getMethodOptionsBuilderTestWithPut() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PUT");
        parsedRequestComponents.put("RequestURL", "/method_options");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute();
        Assert.assertEquals(responseRoute.getResponseBuilder(request, null).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getFileDirectoryBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute();
        Assert.assertEquals(responseRoute.getResponseBuilder(request, null).getClass(), new FileDirectoryBuilder(null).getClass());
    }

    @Test
    public void getFileContentsBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/file1");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute();
        Assert.assertEquals(responseRoute.getResponseBuilder(request, "/src/main/resources").getClass(), new FileContentsBuilder(null).getClass());
    }

    @Test
    public void getFourOhFourBuilderTest() {
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/foobar");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute();
        Assert.assertEquals(responseRoute.getResponseBuilder(request, null).getClass(), new FourOhFourBuilder().getClass());
    }
}

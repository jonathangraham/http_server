package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ResponseRouteTest {

    @Test
    public void getMethodOptionsBuilderTestWithGet() throws Exception{
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
    public void getMethodOptionsBuilderTestWithPut() throws Exception{
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
    public void getFileDirectoryBuilderTest() throws Exception{
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
    public void getFileContentsBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/file1");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FileContentsBuilder(null, null).getClass());
    }

    @Test
    public void getFourOhFourBuilderTest() throws Exception{
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
    public void getRedirectBuilderTest() throws Exception{
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
    public void getPartialContentTest() throws Exception{
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

    @Test
    public void getParameterDecodeBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/anything?ghjd");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new ParameterDecodeBuilder("/anything?ghjd").getClass());
    }

    @Test
    public void getFormResultTest() throws Exception {

        String path = "/src/main/resources/form";
        Assert.assertFalse(new File(System.getProperty("user.dir") + path).exists());

        Map<String, String> firstParsedRequestComponents = new HashMap<>();
        firstParsedRequestComponents.put("RequestType", "POST");
        firstParsedRequestComponents.put("RequestURL", "/form");
        firstParsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        firstParsedRequestComponents.put("RequestHeader", "");
        Request firstRequest = new Request(firstParsedRequestComponents);
        iResponseRoute firstResponseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(firstResponseRoute.getResponseBuilder(firstRequest).getClass(), new Status200Builder().getClass());

        iResponseBuilder response = new FileContentsBuilder("/form", "/src/main/resources");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\ndata=fatcat");

        Map<String, String> secondParsedRequestComponents = new HashMap<>();
        secondParsedRequestComponents.put("RequestType", "PUT");
        secondParsedRequestComponents.put("RequestURL", "/form");
        secondParsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        secondParsedRequestComponents.put("RequestHeader", "");
        Request secondRequest = new Request(secondParsedRequestComponents);
        iResponseRoute secondResponseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(secondResponseRoute.getResponseBuilder(secondRequest).getClass(), new Status200Builder().getClass());

        iResponseBuilder response2 = new FileContentsBuilder("/form", "/src/main/resources");
        byte[] output2 = response2.getResponse();
        Assert.assertTrue(new String(output2).contains("data=heathcliff"));

        Map<String, String> parsedRequestComponents3 = new HashMap<>();
        parsedRequestComponents3.put("RequestType", "DELETE");
        parsedRequestComponents3.put("RequestURL", "/form");
        parsedRequestComponents3.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents3.put("RequestHeader", "");
        Request thirdRequest = new Request(parsedRequestComponents3);
        iResponseRoute thirdResponseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(thirdResponseRoute.getResponseBuilder(thirdRequest).getClass(), new Status200Builder().getClass());

        Assert.assertFalse(new File(System.getProperty("user.dir") + path).exists());
    }

}

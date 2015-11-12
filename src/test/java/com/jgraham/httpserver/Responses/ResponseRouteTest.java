package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
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
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FileContentsBuilder(null).getClass());
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

//    @Test
//    public void getFormResultTest() throws Exception {
//        Map<String, String> firstParsedRequestComponents = new HashMap<>();
//        firstParsedRequestComponents.put("RequestType", "POST");
//        firstParsedRequestComponents.put("RequestURL", "/form");
//        firstParsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
//        firstParsedRequestComponents.put("RequestHeader", "");
//        Request firstRequest = new Request(firstParsedRequestComponents);
//        iResponseRoute firstResponseRoute = new ResponseRoute("/src/main/resources");
//        firstResponseRoute.getResponseBuilder(firstRequest);
//        Assert.assertEquals(firstResponseRoute.getResponseBuilder(firstRequest).getClass(), new Status200Builder().getClass());
//
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        try (InputStream file = ClassLoader.class.getResourceAsStream("/form")) {
//            byte[] bytes = new byte[1000];
//            int numBytes;
//            while ((numBytes = file.read(bytes)) != -1) {
//                output.write(bytes, 0, numBytes);
//            }
//        } catch (RuntimeException e) {
//            output.write("".getBytes());
//        }
//        String out = output.toString();
//        Assert.assertEquals("data=fatcat", out);

//        Map<String, String> secondParsedRequestComponents = new HashMap<>();
//        secondParsedRequestComponents.put("RequestType", "PUT");
//        secondParsedRequestComponents.put("RequestURL", "/form");
//        secondParsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
//        secondParsedRequestComponents.put("RequestHeader", "");
//        Request secondRequest = new Request(firstParsedRequestComponents);
//        iResponseRoute secondResponseRoute = new ResponseRoute("/src/main/resources");
//        secondResponseRoute.getResponseBuilder(secondRequest);
//        Assert.assertEquals(firstResponseRoute.getResponseBuilder(secondRequest).getClass(), new Status200Builder().getClass());
//
//        ByteArrayOutputStream output2 = new ByteArrayOutputStream();
//        try (InputStream file = ClassLoader.class.getResourceAsStream("/form")) {
//            byte[] bytes = new byte[1000];
//            int numBytes;
//            while ((numBytes = file.read(bytes)) != -1) {
//                output2.write(bytes, 0, numBytes);
//            }
//        } catch (RuntimeException e) {
//            output2.write("".getBytes());
//        }
//        String out2 = output.toString();
//        Assert.assertEquals("data=heathcliff", out2);
//    }

}

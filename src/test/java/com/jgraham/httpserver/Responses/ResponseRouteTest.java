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
        Request request = new Request(parsedRequestComponents);
        ResponseRoute responseRoute = new ResponseRoute(null);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PartialContentBuilder("Range=0-10", "/partial_content.txt").getClass());
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
    public void getFileContentsBuilderWithGetWhenFormDoesNotExistsTest() throws Exception {

        String path = "/src/main/resources/form";
        Assert.assertFalse(new File(System.getProperty("user.dir") + path).exists());

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new Status200Builder().getClass());
    }

    @Test
    public void getFileContentsBuilderWithGetWhenFormExistsTest() throws Exception {
        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        createFile();
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FileContentsBuilder(null, null).getClass());
        deleteFile();
    }

    @Test
    public void getFormResponseBuilderWithPostFormTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "POST");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder(null, null).getClass());
    }

    @Test
    public void getFormResponseBuilderWithPutFormTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PUT");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder(null, null).getClass());
    }

    @Test
    public void getFormResponseBuilderWithDeleteFormTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "DELETE");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder(null, null).getClass());
    }

    @Test
    public void getPatchResponseBuilderTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PATCH");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iResponseRoute responseRoute = new ResponseRoute("/src/main/resources");
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PatchResponseBuilder(null, null).getClass());
    }

    private void createFile() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/form";
        File f = new File(path);
        FileWriter file = new FileWriter(f, false);
        file.write("");
        file.flush();
        file.close();
    }

    private void deleteFile() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/form";
        File f = new File(path);
        f.delete();
    }

}

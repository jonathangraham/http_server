package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;
import com.jgraham.httpserver.mocks.MockFileModifier;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CobspecRouterTest {

    @Test
    public void getMethodOptionsBuilderTestWithGet() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/method_options");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getMethodOptionsBuilderTestWithPut() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PUT");
        parsedRequestComponents.put("RequestURL", "/method_options");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new MethodOptionsBuilder().getClass());
    }

    @Test
    public void getFileDirectoryBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new FileDirectoryBuilder(null).getClass());
    }

    @Test
    public void getFileContentsBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/file1");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter("/src/main/resources", new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new FileContentsBuilder(null, null).getClass());
    }

    @Test
    public void getFourOhFourBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/foobar");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new FourOhFourBuilder().getClass());
    }

    @Test
    public void getRedirectBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/redirect");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new RedirectBuilder().getClass());
    }

    @Test
    public void getPartialContentTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/partial_content.txt");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Range=0-10");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new PartialContentBuilder("Range=0-10", "/partial_content.txt").getClass());
    }

    @Test
    public void getParameterDecodeBuilderTest() throws Exception{
        Map<String,String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/anything?ghjd");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        CobspecRouter cobspecRouter = new CobspecRouter(null, new MockFileModifier());
        Assert.assertEquals(cobspecRouter.getResponseBuilder(request).getClass(), new ParameterDecodeBuilder("/anything?ghjd").getClass());
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
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
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
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
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
        MockFileModifier mfm = new MockFileModifier();
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", mfm);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder().getClass());
        Assert.assertEquals("data=fatcat", mfm.getContents());
    }

    @Test
    public void getFormResponseBuilderWithPutFormTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PUT");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        MockFileModifier mfm = new MockFileModifier();
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", mfm);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder().getClass());
        Assert.assertEquals("data=heathcliff", mfm.getContents());
    }

    @Test
    public void getFormResponseBuilderWithDeleteFormTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "DELETE");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        MockFileModifier mfm = new MockFileModifier();
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", mfm);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new FormResponseBuilder().getClass());
        Assert.assertEquals("file deleted", mfm.getContents());
    }

    @Test
    public void getPatchResponseBuilderTest() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PATCH");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PatchResponseBuilder().getClass());
    }

    @Test
    public void getPatchResponseBuilderWitheTagTest1() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PATCH");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Etag: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec");
        Request request = new Request(parsedRequestComponents);
        MockFileModifier mfm = new MockFileModifier();
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", mfm);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PatchResponseBuilder().getClass());
        Assert.assertEquals("patched content", mfm.getContents());
    }

    @Test
    public void getPatchResponseBuilderWitheTagTest2() throws Exception {

        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "PATCH");
        parsedRequestComponents.put("RequestURL", "/form");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Etag: ab60a0d27dda2eee9f65644cd7e4c9cf11de8bec");
        Request request = new Request(parsedRequestComponents);
        MockFileModifier mfm = new MockFileModifier();
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", mfm);
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new PatchResponseBuilder().getClass());
        Assert.assertEquals("default content", mfm.getContents());
    }

    @Test
    public void getBasicAuthenticationBuilderTest() throws Exception {
        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/logs");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Authorization: Basic YWRtaW46aHVudGVyMg==");
        Request request = new Request(parsedRequestComponents);
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new BasicAuthenticationBuilder().getClass());
    }

    @Test
    public void getBasicAuthenticationRequiredBuilderWhenWrongAuthenticationTest() throws Exception {
        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/logs");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "Authorization: Basic YWRtaW46aHVudGVyMg==j");
        Request request = new Request(parsedRequestComponents);
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new BasicAuthenticationRequiredBuilder().getClass());
    }

    @Test
    public void getBasicAuthenticationRequiredBuilderWhenNoAuthorizationTest() throws Exception {
        Map<String, String> parsedRequestComponents = new HashMap<>();
        parsedRequestComponents.put("RequestType", "GET");
        parsedRequestComponents.put("RequestURL", "/logs");
        parsedRequestComponents.put("RequestHTTPVersion", "HTTP/1.1");
        parsedRequestComponents.put("RequestHeader", "");
        Request request = new Request(parsedRequestComponents);
        iAppRouter responseRoute = new CobspecRouter("/src/main/resources", new MockFileModifier());
        Assert.assertEquals(responseRoute.getResponseBuilder(request).getClass(), new BasicAuthenticationRequiredBuilder().getClass());
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

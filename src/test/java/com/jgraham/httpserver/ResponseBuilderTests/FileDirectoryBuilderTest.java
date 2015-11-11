package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

public class FileDirectoryBuilderTest {

    @Test
    public void returnsFileContentsResponse() throws Exception {
        iResponseBuilder response = new com.jgraham.httpserver.ResponseBuilder.FileDirectoryBuilder("/src/main/resources");
        byte[] output = response.getResponse();
        Assert.assertTrue(new String(output).contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(new String(output).contains("file1"));
        Assert.assertTrue(new String(output).contains("image.gif"));
        Assert.assertTrue(new String(output).contains("<a href=/file2>file2</a><br>"));
    }
}

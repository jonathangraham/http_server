package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.FileContentsBuilder;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

public class FileContentsBuilderTest {

    @Test
    public void returnsFileContentsResponse() throws Exception {
        iResponseBuilder response = new FileContentsBuilder("/file1");
        String output = response.getResponse();
        Assert.assertTrue(output.contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(output.contains("file1 contents"));
    }
}

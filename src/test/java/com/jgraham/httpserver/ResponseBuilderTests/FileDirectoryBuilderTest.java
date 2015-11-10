package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.FileDirectoryBuilder;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

public class FileDirectoryBuilderTest {

    @Test
    public void returnsFileContentsResponse() throws Exception {
        iResponseBuilder response = new FileDirectoryBuilder("/src/main/resources");
        String output = response.getResponse();
        Assert.assertTrue(output.contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(output.contains("file1"));
        Assert.assertTrue(output.contains("image.gif"));
        Assert.assertTrue(output.contains("<a href=/file2>file2</a><br>"));
    }
}

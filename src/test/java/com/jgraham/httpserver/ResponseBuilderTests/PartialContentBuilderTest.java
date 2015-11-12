package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PartialContentBuilderTest {
    @Test
    public void returnsPartialContentsResponse() throws Exception {
        iResponseBuilder response = new PartialContentBuilder("Range=0-76", "/partial_content.txt");
        byte[] output = response.getResponse();
        Assert.assertEquals(output.length - 1, "HTTP/1.1 206 Partial Content\r\n\r\nThis is a file that contains text to read part of in order to fulfill a 206.".getBytes().length);
    }

    @Test
    public void returnsPartialContentsResponse2() throws Exception {
        iResponseBuilder response = new PartialContentBuilder("Range=0-0", "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length + 1;
        Assert.assertEquals(output.length, header);
    }

    @Test
    public void returnsPartialContentsResponse3() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "10");
        iResponseBuilder response = new PartialContentBuilder("Range=0-10", "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length + 1;
        Assert.assertEquals(output.length, header + "This is a ".getBytes().length);
    }

    @Test
    public void returnsPartialContentsResponse4() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "10");
        range.put("End", "20");
        iResponseBuilder response = new PartialContentBuilder("Range=10-20", "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length + 1;
        Assert.assertEquals(output.length, header + "file that ".getBytes().length);
    }

    @Test
    public void returnsPartialContentsResponse5() throws Exception {
        Map<String, String> range = new HashMap<>();
    range.put("Start", "0");
    range.put("End", "-1");
    iResponseBuilder response = new PartialContentBuilder("Range=0-", "/partial_content.txt");
    byte[] output = response.getResponse();
    int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length + 1;
    Assert.assertEquals(output.length, header + "This is a file that contains text to read part of in order to fulfill a 206.".getBytes().length);
}

    @Test
    public void returnsPartialContentsResponse6() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "6");
        range.put("End", "-2");
        iResponseBuilder response = new PartialContentBuilder("Range=-6", "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length + 1;
        Assert.assertEquals(output.length, header + " 206.".getBytes().length);
    }

}


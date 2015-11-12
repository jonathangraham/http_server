package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PartialContentBuilderTest {
    @Test
    public void returnsPartialContentsResponse() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "76");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        Assert.assertEquals(output.length, "HTTP/1.1 206 Partial Content\r\n\r\nThis is a file that contains text to read part of in order to fulfill a 206.".getBytes().length);
    }

    @Test
    public void returnsPartialContentsResponse2() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "0");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        Assert.assertEquals(output.length, "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length);
    }

    @Test
    public void returnsPartialContentsResponse3() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "10");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 206 Partial Content\r\n\r\nThis is a ");
    }

    @Test
    public void returnsPartialContentsResponse4() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "10");
        range.put("End", "20");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 206 Partial Content\r\n\r\nfile that ");
    }

    @Test
    public void returnsPartialContentsResponse5() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "0");
        range.put("End", "-1");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length;
        Assert.assertEquals(output.length, header + 77);
    }

    @Test
    public void returnsPartialContentsResponse6() throws Exception {
        Map<String, String> range = new HashMap<>();
        range.put("Start", "6");
        range.put("End", "-2");
        iResponseBuilder response = new PartialContentBuilder(range, "/partial_content.txt");
        byte[] output = response.getResponse();
        int header = "HTTP/1.1 206 Partial Content\r\n\r\n".getBytes().length;
        Assert.assertEquals(output.length, header + 6);
    }
}


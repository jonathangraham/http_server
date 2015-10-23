package com.jgraham.httpserver;

import com.jgraham.httpserver.mocks.MockHttpSocket;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponseTest {

    @Test
    public void getResponseTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket);
        response.getResponse();
        assertThat(outputStream.toByteArray(), is(equalTo("HTTP/1.1 200 OK\r\n\r\n".getBytes())));
    }
}

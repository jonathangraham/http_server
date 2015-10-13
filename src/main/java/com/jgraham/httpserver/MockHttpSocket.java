package com.jgraham.httpserver;

import java.io.InputStream;
import java.io.OutputStream;

public class MockHttpSocket implements HttpSocket {
    private InputStream mockInput;
    private OutputStream mockOutput;

    public MockHttpSocket(InputStream mockInput, OutputStream mockOutput) {
        this.mockInput = mockInput;
        this.mockOutput = mockOutput;
    }

    public InputStream getInputStream() throws Exception {
        return mockInput;
    }

    public OutputStream getOutputStream() throws Exception {
        return mockOutput;
    }

}

package com.jgraham.httpserver;

import java.io.InputStream;
import java.io.OutputStream;

public interface iHttpSocket {

    public InputStream getInputStream() throws Exception;

    public OutputStream getOutputStream() throws Exception;

    public Boolean isConnected() throws Exception;

}


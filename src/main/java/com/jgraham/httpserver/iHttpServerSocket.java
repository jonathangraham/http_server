package com.jgraham.httpserver;


public interface iHttpServerSocket {
    public iHttpSocket accept() throws Exception;
    public void close() throws Exception;
}
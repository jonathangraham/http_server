package com.jgraham.httpserver.ServerConnection;


public interface iHttpServerSocket {
    public iHttpSocket accept() throws Exception;
    public void close() throws Exception;
}
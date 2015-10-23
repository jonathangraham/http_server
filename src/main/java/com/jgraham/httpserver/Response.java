package com.jgraham.httpserver;

import java.io.File;
import java.io.OutputStream;

public class Response {

    private OutputStream out;
    private String requestType;
    private String requestURL;
    private String directory;

    public Response(iHttpSocket clientSocket, String requestType, String requestURL, String directory) throws Exception {
        this.out = clientSocket.getOutputStream();
        this.requestType = requestType;
        this.requestURL = requestURL;
        this.directory = directory;
    }

    public void getResponse() throws Exception {
        String header = getHeader();
        out.write(header.getBytes());
        out.close();
    }

    public String getHeader() {
        if ("GET".equals(requestType)) {
            String path = getPath();
            if ("/".equals(path)) {
                return "HTTP/1.1 200 OK\r\n\r\n";
            } else if (new File(getFilePath(getRoute(), getPath())).exists()) {
                return "HTTP/1.1 200 OK\r\n\r\n";
            } else {
                return "HTTP/1.1 404 Not Found\r\n\r\n";
            }
        }
        else {
            return "HTTP/1.1 200 OK\r\n\r\n";
        }

    }

    public String getRoute() {
        return (System.getProperty("user.dir")) + directory;
    }

    public String getFilePath(String route, String path) {
        return (route + path);
    }

    public String getPath() {
        return requestURL;
    }
}

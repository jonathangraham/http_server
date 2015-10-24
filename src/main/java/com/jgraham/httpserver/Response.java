package com.jgraham.httpserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Response {

    private OutputStream out;
    private String requestType;
    private String requestURL;
    private String directory;
    private String ok = "HTTP/1.1 200 OK\r\n\r\n";
    private String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";

    public Response(iHttpSocket clientSocket, String requestType, String requestURL, String directory) throws Exception {
        this.out = clientSocket.getOutputStream();
        this.requestType = requestType;
        this.requestURL = requestURL;
        this.directory = directory;
    }

    public void getResponse() throws Exception {
        String header = getHeader();
        out.write(header.getBytes());
        getBody();
        out.close();
    }

    public void getBody() throws Exception{
        if (new File(getFilePath(getRoute(), getPath())).exists()) {
            writeResourceToStream();
        }
    }

    public String getHeader() {
        if ("GET".equals(requestType)) {
            String path = getPath();
            if ("/".equals(path)) {
                return ok;
            } else if (new File(getFilePath(getRoute(), getPath())).exists()) {
                return ok;
            } else {
                return notFound;
            }
        }
        else {
            return ok;
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

    public void writeResourceToStream() throws IOException {
        try (InputStream file = ClassLoader.class.getResourceAsStream(getPath())) {
            byte[] bytes = new byte[1000];
            int numBytes;
            while ((numBytes = file.read(bytes)) != -1) {
                out.write(bytes, 0, numBytes);
            }
        }
        catch (RuntimeException e) {
            out.write("".getBytes());
        }
    }
}

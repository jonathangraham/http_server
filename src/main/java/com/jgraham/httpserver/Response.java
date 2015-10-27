package com.jgraham.httpserver;

import java.io.*;

public class Response {

    private OutputStream out;
    private String requestType;
    private String requestURL;
    private String directory;
    private String ok = "HTTP/1.1 200 OK\r\n";
    private String notFound = "HTTP/1.1 404 Not Found\r\n";
    private String notAllowed = "HTTP/1.1 405 Method Not Allowed\r\n";

    //need Response to take in Request, and then get the parts from here
    public Response(iHttpSocket clientSocket, String requestType, String requestURL, String directory) throws Exception {
        this.out = clientSocket.getOutputStream();
        this.requestType = requestType;
        this.requestURL = requestURL;
        this.directory = directory;
    }

    public void getResponse() throws Exception {
        String status = getStatus();
        out.write(status.getBytes());
        getHeader();
        getBody();
        out.close();
    }

    public void getHeader() throws Exception {
        if ("/method_options".equals(getPath())) {
            out.write("Allow: GET,HEAD,POST,OPTIONS,PUT\r\n".getBytes());
        }
        else {
            out.write("\r\n".getBytes());
        }
    }

    public void getBody() throws Exception {
        if ("/".equals(getPath())) {
            String files = buildDirectoryContents();
            out.write(files.getBytes());
        }
        else if (new File(getFilePath(getRoute(), getPath())).exists()) {
            writeResourceToStream();
        }
        else {
            out.write("".getBytes());
        }
    }

    public String getStatus() {
        if ("GET".equals(requestType)) {
            String path = getPath();
            if ("/".equals(path)) {
                return ok;
            }
            else if ("/method_options".equals(path)) {
                return ok;
            } else if (new File(getFilePath(getRoute(), getPath())).exists()) {
                return ok;
            }
            else {
                return notFound;
            }
        }
        else if (new File(getFilePath(getRoute(), getPath())).exists()) {
            return notAllowed;
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

//refactor into writeResourcesToStream
    public void writePartialResourceToStream(int begin, int finish) throws IOException {
        int length = finish - begin;
        try (InputStream file = ClassLoader.class.getResourceAsStream(getPath())) {
            byte[] bytes = new byte[length];
            file.read(bytes);
            out.write(bytes, begin, finish);
        }
        catch (RuntimeException e) {
            out.write("".getBytes());
        }
    }

    public String buildDirectoryContents() {
        File f = new File(getRoute());
        String[] files = f.list();

        StringBuilder fileNames = new StringBuilder();
        fileNames.append("<!DOCTYPE html>\n");
        fileNames.append("<!DOCTYPE html>\n<html>\n<body>\n");
        for (String file: files) {
            fileNames.append(("<a href=/" + file + ">" + file + "</a><br>"));
        }
        fileNames.append("</body>\n</html>");
        return fileNames.toString();
    }
}
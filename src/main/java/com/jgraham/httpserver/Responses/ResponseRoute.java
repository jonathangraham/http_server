package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;

public class ResponseRoute {

    private iResponseBuilder responseBuilder;

    public iResponseBuilder getResponseBuilder(Request request, String directory) {
        String path = request.getRequestURL();
        if (request.getRequestType().equals("GET")) {
            getGETResponseBuilder(path, directory);
        }
        else if (request.getRequestType().equals("PUT")){
            getPUTResponseBuilder(path, directory);
        }
        else {
            getPUTResponseBuilder(path, directory);
        }

        return responseBuilder;
    }

    private void getGETResponseBuilder(String path, String directory) {
        if ("/".equals(path)) {
            responseBuilder = new FileDirectoryBuilder(directory);
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
                responseBuilder = new FileContentsBuilder(path);
        }
        else if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else {
            responseBuilder = new FourOhFourBuilder();
        }

    }

    private void getPUTResponseBuilder(String path, String directory) {
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
            responseBuilder = new MethodNotAllowedBuilder();
        }
        else {
            responseBuilder = new Status200Builder();
        }
    }

    private String getRoute(String directory) {
        return (System.getProperty("user.dir")) + directory;
    }

    private String getFilePath(String route, String path) {
        return (route + path);
    }
}

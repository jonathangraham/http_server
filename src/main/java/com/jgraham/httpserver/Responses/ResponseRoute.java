package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResponseRoute implements iResponseRoute {

    private String directory;
    private iResponseBuilder responseBuilder;

    public ResponseRoute(String directory) {
        this.directory = directory;
    }

    public iResponseBuilder getResponseBuilder(Request request) throws Exception {
        String path = request.getRequestURL();
        String header = request.getRequestHeader();
        String verb = request.getRequestType();
        if (verb.equals("GET")) {
            getGETResponseBuilder(path, header, directory);
        }
        else {
            getOtherResponseBuilder(path, directory, verb);
        }
        return responseBuilder;
    }

    private void getGETResponseBuilder(String path, String header, String directory) {
        if ("/".equals(path)) {
            responseBuilder = new FileDirectoryBuilder(directory);
        }
        else if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/redirect")) {
            responseBuilder = new RedirectBuilder();
        }
        else if (header.contains("Range")) {
//            Map<String, String> range = getPartialContentRange(header.split("=")[1]);
            responseBuilder = new PartialContentBuilder(header, path);
        }
        else if (path.contains("/form") && (new File(getFilePath(getRoute(directory), path)).exists())) {
            responseBuilder = new FileContentsBuilder(path, directory);
        }
        else if (path.contains("/form")) {
            responseBuilder = new Status200Builder();
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
            responseBuilder = new FileContentsBuilder(path, directory);
        }
        else if (path.contains("?")) {
            responseBuilder = new ParameterDecodeBuilder(path);
        }
        else {
            responseBuilder = new FourOhFourBuilder();
        }

    }

    private void getOtherResponseBuilder(String path, String directory, String verb) throws Exception {
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/form")) {
            modifyForm(path, directory, verb);
            responseBuilder = new Status200Builder();
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
            responseBuilder = new MethodNotAllowedBuilder();
        }
        else {
            responseBuilder = new Status200Builder();
        }
    }

    private void modifyForm(String path, String directory, String verb) throws IOException {
        File f = new File(getFilePath(getRoute(directory), path));
        FileWriter file = new FileWriter(f, false);
        if (verb.equals("PUT")) {
            file.write("data=heathcliff");
            file.flush();
            file.close();
        }
        else if (verb.equals("POST")) {
            file.write("data=fatcat");
            file.flush();
            file.close();
        }
        else {
            f.delete();
        }
    }

    private String getRoute(String directory) {
        return (System.getProperty("user.dir")) + directory;
    }

    private String getFilePath(String route, String path) {
        return (route + path);
    }

}

package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResponseRoute implements iResponseRoute {

    private String directory;
    private iResponseBuilder responseBuilder;

    public ResponseRoute(String directory) {
        this.directory = directory;
    }

    public iResponseBuilder getResponseBuilder(Request request) {
        String path = request.getRequestURL();
        String header = request.getRequestHeader();
        if (request.getRequestType().equals("GET")) {
            getGETResponseBuilder(path, header, directory);
        }
        else if (request.getRequestType().equals("PUT")){
            getPUTResponseBuilder(path, directory);
        }
        else {
            getPUTResponseBuilder(path, directory);
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
            Map<String, String> range = getPartialContentRange(header.split("=")[1]);
            responseBuilder = new PartialContentBuilder(range, path);
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
            responseBuilder = new FileContentsBuilder(path);
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

    public Map<String, String> getPartialContentRange(String header) {
        Map<String, String> range = new HashMap<>();
        //need to catch array out of bounds if no values for start or end given here
        String start;
        String end;
        String[] s = header.split("-");
        //if no range given will read all the file. length of file not known at this stage, so given -1.
        if (s.length == 0) {
            start = "0";
            end = "-1";
        }
        //if first number isn't given, will start back from the end by the second number and read to end. -2 gives handle for this.
        else if (s[0].length() == 0) {
            start = s[1];
            end = "-2";
        }
        //if no second number, will read to the end
        else if (s.length == 1) {
            start = s[0];
            end = "-1";
        }
        //cob_spec requires a file size that is 1 byte too short for the file that is stored on github.
        else {
            start = s[0];
            end = Integer.toString(Integer.parseInt(s[1]) + 1);
        }
        range.put("Start", start);
        range.put("End", end);
        return range;
    }
}

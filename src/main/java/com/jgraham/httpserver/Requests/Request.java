package com.jgraham.httpserver.Requests;

import java.util.Map;

public class Request {
    private String requestType;
    private String requestURL;
    private String httpVersion;

    public Request(Map<String, String> parsedRequestComponents) {
        this.requestType = parsedRequestComponents.get("RequestType");
        this.requestURL = parsedRequestComponents.get("RequestURL");
        this.httpVersion = parsedRequestComponents.get("RequestHTTPVersion");
    }

    public String getRequestType() {
        return requestType;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getHTTPVersion() {
        return httpVersion;
    }

}

//package com.jgraham.httpserver;
//
//        import java.io.BufferedReader;
//
//public class Request implements iRequest {
//
//    private BufferedReader rawRequest;
//    private String request;
//
//    public Request(BufferedReader in) throws Exception {
//        this.rawRequest = in;
//    }
//
//    public String getRequest() throws Exception {
//        this.request = rawRequest.readLine();
//        return request;
//    }
//
//    public String getRequestType() {
//        return request.split("\\s+")[0];
//    }
//
//    public String getRequestURL() {
//        return request.split("\\s+")[1];
//    }
//}



//Start looking at Partial Content
//public class Request implements iRequest {
//
//    private String request;
//
//    public Request(iHttpSocket clientSocket) throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//        StringBuilder req = new StringBuilder();
//        String line = "";
//        while( (line = in.readLine()) != null) {
//            req.append(line);
//            req.append("\r\n");
//        }
//
//        this.request = req.toString();
//    }
//
//    public String getRequest() throws Exception {
//        return request;
//    }
//
//    public String[] getRequestLines() {
//        return request.split("\n\r");
//    }
//
//    public String getRequestLine() {
//        return getRequestLines()[0];
//    }
//
//    public String getHeaderLine() {
//        return getRequestLines()[getRequestLines().length -1];
//    }
//
//    public String getRequestType() {
//        return request.split("\\s+")[0];
//    }
//
//    public String getRequestURL() {
//        return request.split("\\s+")[1];
//    }
//
//    public String getRangeStart() {
//        if (getHeaderLine().contains("Range")) {
//            String range = getHeaderLine().split("=")[1];
//            return range.split("-")[0];
//        }
//        else {
//            return "";
//        }
//    }
//
//    public String getRangeEnd() {
//        if (getHeaderLine().contains("Range")) {
//            String range = getHeaderLine().split("=")[1];
//            return range.split("-")[1];
//        }
//        else {
//            return "";
//        }
//    }
//}
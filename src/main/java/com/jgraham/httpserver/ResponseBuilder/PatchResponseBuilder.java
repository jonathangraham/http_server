package com.jgraham.httpserver.ResponseBuilder;


import com.jgraham.httpserver.Responses.ModifyFile;

import java.io.File;

public class PatchResponseBuilder implements iResponseBuilder {

    private String header;
    private File f;

    public PatchResponseBuilder(String header, File f) {

        this.header = header;
        this.f = f;
    }

    public byte[] getResponse() throws Exception{
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    private String getStatusLine() {
        return "HTTP/1.1 204 Not Found\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }

    public void modifyFile() throws Exception{
        if (header.contains("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")) {
            new ModifyFile(f).modifyFile("patched content");
        }
        else {
            new ModifyFile(f).modifyFile("default content");
        }
    }

}

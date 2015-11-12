package com.jgraham.httpserver.ResponseBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

public class PartialContentBuilder implements iResponseBuilder {

    private Map<String, String> range;
    private String path;

    public PartialContentBuilder(Map<String, String> range, String path) {

        this.range = range;
        this.path = path;
    }

    public byte[] getResponse() throws Exception {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(getStatusLine().getBytes());
        response.write(getHeader().getBytes());
        response.write(getBody());
        return response.toByteArray();
    }

    private String getStatusLine() {
        return "HTTP/1.1 206 Partial Content\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private byte[] getBody() throws Exception {
        return getFileContent();

    }


    private byte[] getFileContent() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream file = ClassLoader.class.getResourceAsStream(path)) {
            byte[] bytes = new byte[1];
            int numBytes;
            while ((numBytes = file.read(bytes)) != -1) {
                output.write(bytes, 0, numBytes);
            }
        }
        catch (RuntimeException e) {
            output.write("".getBytes());
        }
        int numBytes = output.toByteArray().length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream file = ClassLoader.class.getResourceAsStream(path)) {
            byte[] b = new byte[1000];
            file.read(b);

            String s = range.get("Start");
            int start = Integer.parseInt(s);
            String e = range.get("End");
            int end = Integer.parseInt(e);
            if (end == -1 ) {
                end = numBytes;
            }
            else if (end == -2) {
                end = numBytes;
                start = end - start;
            }
            int length = end - start;


            out.write(b, start, length);
        } catch (RuntimeException e) {
            out.write("".getBytes());
        }
        return out.toByteArray();
    }
}
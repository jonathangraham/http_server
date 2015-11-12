package com.jgraham.httpserver.ResponseBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PartialContentBuilder implements iResponseBuilder {

    private String header;
    private String path;

    public PartialContentBuilder(String header, String path) {

        this.header = header;
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


    public byte[] getFileContent() throws Exception {

        Map<String, String> range = getPartialContentRange(header.split("=")[1]);

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
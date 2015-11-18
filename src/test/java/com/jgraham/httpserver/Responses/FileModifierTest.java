package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.ResponseBuilder.FileContentsBuilder;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FileModifierTest {

    @Test
    public void createsNewFileIfDoesNotExistTest() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/foobar";
        File f = new File(path);
        Assert.assertFalse(f.exists());
        FileModifier mf = new FileModifier();
        mf.writeContentToFile("hello", f);
        Assert.assertTrue(f.exists());

        iResponseBuilder response = new FileContentsBuilder("/foobar", "/src/main/resources");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\nhello");

        f.delete();
    }

    @Test
    public void modifiesFileTest() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/foobar";
        File f = new File(path);
        FileModifier mf = new FileModifier();
        mf.writeContentToFile("hello", f);
        mf.writeContentToFile("goodbye", f);
        Assert.assertTrue(f.exists());

        iResponseBuilder response = new FileContentsBuilder("/foobar", "/src/main/resources");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\ngoodbye");

        f.delete();
    }

    @Test
    public void deletesFileTest() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/foobar";
        File f = new File(path);
        FileModifier mf = new FileModifier();
        mf.writeContentToFile("hello", f);
        Assert.assertTrue(f.exists());
        mf.deleteFile(f);
        Assert.assertFalse(f.exists());
    }
}

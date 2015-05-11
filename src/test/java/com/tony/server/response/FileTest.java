package com.tony.server.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class FileTest {
    public void writeTestFile(String filePath, String body) {
        try(PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            writer.print(body);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void deleteTestFile(String filePath) {
       new File(filePath).delete();
    }
}

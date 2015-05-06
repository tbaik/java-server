package com.tony.server.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class PartialContentResponse extends Response{
    private static final int BEGINNING_INDEX = 0;
    private static final int ENDING_INDEX = 1;
    private final String filePath;
    private String rangeHeader;

    public byte[] respond(){
        try {
            String wholeFileString;
            File file = new File(filePath);
            if(file.isFile()){
                wholeFileString = new String(Files.readAllBytes(file.toPath()));
                setBody(getPartialContent(wholeFileString));
            } else {
                return new InternalErrorResponse("No Such File!").respond();
            }
        } catch (IOException e) {
            return new InternalErrorResponse(e.toString()).respond();
        } catch (StringIndexOutOfBoundsException e) {
            return new InternalErrorResponse(e.toString()).respond();
        }
        return ResponseBuilder.buildResponse(getStatus(),
                getHeaders(), getBody()).getBytes();
    }

    public PartialContentResponse(String filePath) {
        this.filePath = filePath;
        setStatus(Status.PARTIAL_CONTENT);
        setHeaders(new HashMap<>());
    }

    public String getRangeHeader() {
        return rangeHeader;
    }

    public void setRangeHeader(String rangeHeader) {
        this.rangeHeader = rangeHeader;
    }

    public String getPartialContent(String wholeFileString) {
        int beginningIndex = 0;
        int endingIndex = wholeFileString.length();

        String range = rangeHeader.split("=")[1];
        String[] indices = range.split("-");

        if(!range.startsWith("-")){
            beginningIndex = Integer.parseInt(indices[BEGINNING_INDEX]);
        } else {
            beginningIndex = endingIndex - Integer.parseInt(indices[ENDING_INDEX]);
            return wholeFileString.subSequence(beginningIndex, endingIndex).toString();
        }

        if(!range.endsWith("-")){
            endingIndex = Integer.parseInt(indices[ENDING_INDEX]) + 1;
        }
        return wholeFileString.subSequence(beginningIndex, endingIndex).toString();
    }
}

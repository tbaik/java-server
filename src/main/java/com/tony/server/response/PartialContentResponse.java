package com.tony.server.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PartialContentResponse extends Response{
    private static final int BEGINNING_INDEX = 0;
    private static final int ENDING_INDEX = 1;
    private static final int RANGE = 1;
    public static final String INDEX_SEPARATOR = "-";

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
                setBody("");
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
    }

    public String getRangeHeader() {
        return rangeHeader;
    }

    public void setRangeHeader(String rangeHeader) {
        this.rangeHeader = rangeHeader;
    }

    public String getPartialContent(String wholeFileString) {
        String range = getRange();
        String[] indices = range.split(INDEX_SEPARATOR);
        int wholeFileLength = wholeFileString.length();

        int beginningIndex = getBeginningIndex(wholeFileLength, range, indices);
        int endingIndex = getEndingIndex(wholeFileLength, range, indices);

        return wholeFileString.subSequence(beginningIndex, endingIndex).toString();
    }

    protected int getBeginningIndex(int wholeFileLength, String range, String[] indices) {
        if(range.startsWith(INDEX_SEPARATOR)){
            return wholeFileLength - Integer.parseInt(indices[ENDING_INDEX]);
        } else {
            return Integer.parseInt(indices[BEGINNING_INDEX]);
        }
    }

    protected int getEndingIndex(int wholeFileLength, String range, String[] indices) {
        if(range.startsWith(INDEX_SEPARATOR) || range.endsWith(INDEX_SEPARATOR)) {
            return wholeFileLength;
        } else {
            return Integer.parseInt(indices[ENDING_INDEX]) + 1;
        }
    }

    protected String getRange() {
        return rangeHeader.split("=")[RANGE];
    }
}

package com.heaerie.sparrow.service;

public class SparrowBadFormatException extends Exception {
    final int httpCode= 404;
    public SparrowBadFormatException(String msg) {
        super(msg);
    }

    public int getHttpCode() {
        return httpCode;
    }
}

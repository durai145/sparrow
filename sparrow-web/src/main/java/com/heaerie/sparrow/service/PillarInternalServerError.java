package com.heaerie.sparrow.service;

public class PillarInternalServerError extends Throwable {
    final int httpCode = 500;
    public PillarInternalServerError(Exception e) {
        super(e.getMessage());

    }
    public int getHttpCode() {
        return httpCode;
    }
}

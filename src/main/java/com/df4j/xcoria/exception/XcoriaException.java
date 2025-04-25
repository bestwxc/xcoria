package com.df4j.xcoria.exception;

public class XcoriaException extends RuntimeException {

    public XcoriaException() {
    }

    public XcoriaException(String message) {
        super(message);
    }

    public XcoriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public XcoriaException(Throwable cause) {
        super(cause);
    }

    public XcoriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

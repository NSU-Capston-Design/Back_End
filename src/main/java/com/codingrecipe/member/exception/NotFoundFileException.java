package com.codingrecipe.member.exception;

public class NotFoundFileException extends Exception{
    public NotFoundFileException() {
    }

    public NotFoundFileException(String message) {
        super(message);
    }

    public NotFoundFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundFileException(Throwable cause) {
        super(cause);
    }

    public NotFoundFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

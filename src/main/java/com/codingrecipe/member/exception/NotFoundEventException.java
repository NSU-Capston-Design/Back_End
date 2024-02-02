package com.codingrecipe.member.exception;

public class NotFoundEventException extends Exception{
    public NotFoundEventException() {
        super();
    }

    public NotFoundEventException(String message) {
        super(message);
    }

    public NotFoundEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEventException(Throwable cause) {
        super(cause);
    }

    protected NotFoundEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

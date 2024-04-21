package com.codingrecipe.member.exception;

public class NotEnoughInvenException extends Exception{
    public NotEnoughInvenException() {
    }

    public NotEnoughInvenException(String message) {
        super(message);
    }

    public NotEnoughInvenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughInvenException(Throwable cause) {
        super(cause);
    }

    public NotEnoughInvenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

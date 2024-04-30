package com.codingrecipe.member.exception;

public class NotFoundProductException extends Exception{
    public NotFoundProductException() {
    }

    public NotFoundProductException(String message) {
        super(message);
    }

    public NotFoundProductException(String message, Long id){
        super(message);
        System.out.println("id = " + id);
    }
    public NotFoundProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundProductException(Throwable cause) {
        super(cause);
    }

    public NotFoundProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

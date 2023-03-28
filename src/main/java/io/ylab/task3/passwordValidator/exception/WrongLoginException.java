package io.ylab.task3.passwordValidator.exception;

public class WrongLoginException extends Exception{

    public WrongLoginException(String message) {
        super(message);
    }
}

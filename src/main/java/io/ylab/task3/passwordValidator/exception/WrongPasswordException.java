package io.ylab.task3.passwordValidator.exception;

public class WrongPasswordException extends Exception{

    public WrongPasswordException(String message) {
        super(message);
    }
}

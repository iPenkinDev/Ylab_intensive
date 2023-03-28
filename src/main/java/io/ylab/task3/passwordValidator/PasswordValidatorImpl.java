package io.ylab.task3.passwordValidator;

import io.ylab.task3.passwordValidator.exception.WrongLoginException;
import io.ylab.task3.passwordValidator.exception.WrongPasswordException;

public class PasswordValidatorImpl {

    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            validateLogin(login);
            validatePassword(password);
            validateConfirmPassword(password, confirmPassword);
            return true;
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void validateLogin(String login) throws WrongLoginException {
        if (!login.matches("[a-zA-Z0-9_]+")) {
            throw new WrongLoginException("Логин содержит недопустимые символы");
        }
        if (login.length() >= 20) {
            throw new WrongLoginException("Логин слишком длинный");
        }
    }

    private static void validatePassword(String password) throws WrongPasswordException {
        if (!password.matches("[a-zA-Z0-9_]+")) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        }
        if (password.length() >= 20) {
            throw new WrongPasswordException("Пароль слишком длинный");
        }
    }

    private static void validateConfirmPassword(String password, String confirmPassword)
            throws WrongPasswordException {
        if (!password.equals(confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }
}

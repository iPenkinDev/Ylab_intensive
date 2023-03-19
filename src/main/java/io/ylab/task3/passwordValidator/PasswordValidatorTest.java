package io.ylab.task3.passwordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "my_login_123";
        String password = "my_password_456";
        String confirmPassword = "my_password_456";

        boolean isValid = PasswordValidatorImpl.validate(login, password, confirmPassword);
        System.out.println("Is valid: " + isValid);
        System.out.println();

        String login2 = "my_login_123!";
        String password2 = "my_passord_456";
        String confirmPassword2 = "my_passworer@^#%d_123";

        boolean isValid2 = PasswordValidatorImpl.validate(login2, password2, confirmPassword2);
        System.out.println("Is valid: " + isValid2);
        System.out.println();

        String login3 = "my_login_123";
        String password3 = "my_pass#^ord_456";
        String confirmPassword3 = "my_passworer@^#%d_123";

        boolean isValid3 = PasswordValidatorImpl.validate(login3, password3, confirmPassword3);
        System.out.println("Is valid: " + isValid3);
        System.out.println();

        String login4 = "my_login_123";
        String password4 = "my_passord_456";
        String confirmPassword4 = "my_passworer@^#%d_123";

        boolean isValid4 = PasswordValidatorImpl.validate(login4, password4, confirmPassword4);
        System.out.println("Is valid: " + isValid4);
    }
}

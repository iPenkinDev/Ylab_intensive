package io.ylab.task2.snilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (snils == null || snils.length() != 11) {
            return false;
        }

        // Проверяем, что все символы - цифры
        if (!snils.matches("\\d+")) {
            return false;
        }

        // Вычисляем контрольное число
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(snils.charAt(i)) * (9 - i);
        }

        int checkDigit = Integer.parseInt(snils.substring(9));
        return checkDigit == getCheckDigit(sum);
    }

    // Метод для вычисления контрольного числа
    private static int getCheckDigit(int sum) {
        int checkDigit;
        if (sum < 100) {
            checkDigit = sum;
        } else if (sum == 100 || sum == 101) {
            checkDigit = 0;
        } else {
            checkDigit = sum % 101;
            if (checkDigit == 100) {
                checkDigit = 0;
            }
        }
        return checkDigit;
    }
}
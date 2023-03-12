package io.ylab.task2.snilsValidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("15233453445678901")); //false
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
        System.out.println(new SnilsValidatorImpl().validate("test345437525")); //false
    }
}

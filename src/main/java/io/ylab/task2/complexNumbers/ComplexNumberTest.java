package io.ylab.task2.complexNumbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber z1 = new ComplexNumberImpl(1);
        ComplexNumber z2 = new ComplexNumberImpl(3, 5);

        ComplexNumber sum = z1.add(z2);
        System.out.println(z1 + " + " + z2 + " = " + sum);

        ComplexNumber difference = z1.subtract(z2);
        System.out.println(z1 + " - " + z2 + " = " + difference);

        ComplexNumber product = z1.multiply(z2);
        System.out.println(z1 + " * " + z2 + " = " + product);

        double modulus = z1.modul();
        System.out.println("|" + z1 + "| = " + modulus);

        String stringRepresentation = z1.toString();
        System.out.println("String representation of " + z1 + " = " + stringRepresentation);
    }
}
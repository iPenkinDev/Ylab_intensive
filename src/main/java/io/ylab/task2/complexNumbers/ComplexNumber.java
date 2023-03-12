package io.ylab.task2.complexNumbers;

public interface ComplexNumber {
    ComplexNumber add(ComplexNumber other);

    ComplexNumber subtract(ComplexNumber other);

    ComplexNumber multiply(ComplexNumber other);

    double modul();

    double getRealPart();

    void setRealPart(double realPart);

    double getImaginaryPart();

    void setImaginaryPart(double imaginaryPart);
}

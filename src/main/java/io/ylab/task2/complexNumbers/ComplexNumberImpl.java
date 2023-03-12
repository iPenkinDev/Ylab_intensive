package io.ylab.task2.complexNumbers;

public class ComplexNumberImpl implements ComplexNumber {
    private double realPart;
    private double imaginaryPart;

    public ComplexNumberImpl(double realPart) {
        this.setRealPart(realPart);
    }

    public ComplexNumberImpl(double realPart, double imaginaryPart) {
        this.setRealPart(realPart);
        this.setImaginaryPart(imaginaryPart);
    }

    @Override
    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumberImpl(
                this.getRealPart() + other.getRealPart(),
                this.getImaginaryPart() + other.getImaginaryPart()
        );
    }

    @Override
    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumberImpl(
                this.getRealPart() - other.getRealPart(),
                this.getImaginaryPart() - other.getImaginaryPart()
        );
    }

    @Override
    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumberImpl(
                this.getRealPart() * other.getRealPart() - this.getImaginaryPart() * other.getImaginaryPart(),
                this.getRealPart() * other.getImaginaryPart() + this.getImaginaryPart() * other.getRealPart()
        );
    }

    @Override
    public double modul() {
        return Math.sqrt(this.getRealPart() * this.getRealPart() + this.getImaginaryPart() * this.getImaginaryPart());
    }

    @Override
    public String toString() {
        return String.format("%.2f + %.2fi", getRealPart(), getImaginaryPart());
    }

    @Override
    public double getRealPart() {
        return realPart;
    }

    @Override
    public void setRealPart(double realPart) {
        this.realPart = realPart;
    }

    @Override
    public double getImaginaryPart() {
        return imaginaryPart;
    }

    @Override
    public void setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }
}
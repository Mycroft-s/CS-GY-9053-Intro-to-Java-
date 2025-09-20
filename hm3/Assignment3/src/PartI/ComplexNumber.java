package PartI;

public class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public double magnitude() {
        return Math.hypot(real, imaginary);
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real,
                                 this.imaginary + other.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real,
                                 this.imaginary - other.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double a = this.real, b = this.imaginary;
        double c = other.real, d = other.imaginary;
        return new ComplexNumber(a * c - b * d, a * d + b * c);
    }

    public ComplexNumber divide(ComplexNumber divisor) {
        double c = divisor.real, d = divisor.imaginary;
        double denom = c * c + d * d;
        if (denom == 0.0) {
            throw new ArithmeticException("Division by zero complex number");
        }
        double a = this.real, b = this.imaginary;
        double realPart = (a * c + b * d) / denom;
        double imagPart = (b * c - a * d) / denom;
        return new ComplexNumber(realPart, imagPart);
    }

    @Override
    public String toString() {
        if (imaginary >= 0) {
            return real + " + i" + imaginary;
        } else {
            return real + " - i" + Math.abs(imaginary);
        }
    }

    public static void main(String[] args) {
        ComplexNumber z1 = new ComplexNumber(7.5, 4.2);
        ComplexNumber z2 = new ComplexNumber(8.2, 9.4);

        System.out.println("z1 = " + z1);
        System.out.println("z2 = " + z2);
        System.out.println("z1 + z2 = " + z1.add(z2));
        System.out.println("z1 - z2 = " + z1.subtract(z2));
        System.out.println("z1 * z2 = " + z1.multiply(z2));
        System.out.println("z1 / z2 = " + z1.divide(z2));
        System.out.println("|z1| = " + z1.magnitude());
        System.out.println("|z2| = " + z2.magnitude());
    }
}

package model;

public class Triangle extends Shape {
    private double a, b, c;

    public Triangle(String shapeColor, double a, double b, double c) {
        super(shapeColor);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calcArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public String toString() {
        return super.toString() +
                ", a=" + String.format("%.2f", a) +
                ", b=" + String.format("%.2f", b) +
                ", c=" + String.format("%.2f", c);
    }
}
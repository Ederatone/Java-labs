package model;

import java.text.DecimalFormat;

public abstract class Shape implements Drawable {
    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return "class=" + this.getClass().getSimpleName() +
                ", color=" + shapeColor;
    }

    @Override
    public void draw() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(this + ", area=" + df.format(calcArea()));
    }
}
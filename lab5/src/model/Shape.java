package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public abstract class Shape implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;
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
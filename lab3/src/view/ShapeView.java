package view;

import model.Shape;
import java.text.DecimalFormat;

public class ShapeView {
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            printMessage("The array of shapes is empty.");
            return;
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void printTotalArea(double totalArea) {
        DecimalFormat df = new DecimalFormat("#.##");
        printMessage("Total area of all shapes: " + df.format(totalArea));
    }

    public void printAreaByType(String shapeType, double area) {
        DecimalFormat df = new DecimalFormat("#.##");
        printMessage("Total area of shapes of type '" + shapeType + "': " + df.format(area));
    }

    public void printNotFoundMessage(String shapeType) {
        printMessage("No shapes of type '" + shapeType + "' were found.");
    }
}
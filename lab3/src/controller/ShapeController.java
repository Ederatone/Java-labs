package controller;

import model.*;
import view.ShapeView;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController() {
        this.view = new ShapeView();
        this.shapes = createShapes();
    }

    private Shape[] createShapes() {
        return new Shape[]{
                new Rectangle("Red", 10, 5),
                new Circle("Blue", 12),
                new Triangle("Green", 5, 5, 5),
                new Rectangle("Blue", 7, 8),
                new Circle("Yellow", 4.5),
                new Triangle("Red", 10, 8, 6),
                new Rectangle("Purple", 3, 11),
                new Circle("Green", 8),
                new Triangle("Blue", 7, 7, 4),
                new Rectangle("Yellow", 9, 9)
        };
    }

    public void processShapes() {
        view.printMessage("------------ Initial Set of Shapes ------------");
        view.printShapes(shapes);
        view.printMessage("\n");

        double totalArea = calculateTotalArea();
        view.printTotalArea(totalArea);
        view.printMessage("\n");

        view.printMessage("------------ Random Search for a Shape Type ------------");
        Class<?>[] searchableTypes = {Rectangle.class, Circle.class, Triangle.class};
        Random random = new Random();
        Class<?> randomShapeType = searchableTypes[random.nextInt(searchableTypes.length)];

        Double randomArea = calculateAreaByType(randomShapeType);
        if (randomArea != null) {
            view.printAreaByType(randomShapeType.getSimpleName(), randomArea);
        } else {
            view.printNotFoundMessage(randomShapeType.getSimpleName());
        }
        view.printMessage("\n");

        view.printMessage("------------ Searching for a Non-Existent Type ------------");
        Class<?> nonExistentType = String.class;
        Double nonExistentArea = calculateAreaByType(nonExistentType);
        if (nonExistentArea != null) {
            view.printAreaByType(nonExistentType.getSimpleName(), nonExistentArea);
        } else {
            view.printNotFoundMessage(nonExistentType.getSimpleName());
        }
        view.printMessage("\n");

        view.printMessage("------------ Sorting by Area (Ascending) ------------");
        sortByArea();
        view.printShapes(shapes);
        view.printMessage("\n");

        view.printMessage("------------ Sorting by Color (Alphabetical) ------------");
        sortByColor();
        view.printShapes(shapes);
    }

    private double calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    private Double calculateAreaByType(Class<?> shapeType) {
        double sum = 0;
        boolean found = false;
        for (Shape shape : shapes) {
            if (shapeType.isInstance(shape)) {
                sum += shape.calcArea();
                found = true;
            }
        }
        return found ? sum : null;
    }

    private void sortByArea() {
        Arrays.sort(shapes, new ShapeAreaComparator());
    }

    private void sortByColor() {
        Arrays.sort(shapes, new ShapeColorComparator());
    }
}

class ShapeAreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape s1, Shape s2) {
        return Double.compare(s1.calcArea(), s2.calcArea());
    }
}

class ShapeColorComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape s1, Shape s2) {
        return s1.getShapeColor().compareTo(s2.getShapeColor());
    }
}
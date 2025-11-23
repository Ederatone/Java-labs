package view;

import model.Shape;
import java.text.DecimalFormat;

public class ShapeView {

    public void printMenu() {
        System.out.println("\n========= MENU =========");
        System.out.println("1. Show current shapes");
        System.out.println("2. Generate new random shapes");
        System.out.println("3. Calculate Total Area");
        System.out.println("4. Sort Shapes");
        System.out.println("5. SAVE Shapes to File");
        System.out.println("6. LOAD Shapes from File");
        System.out.println("7. Find Max Words Line in File");
        System.out.println("8. Encrypt/Decrypt File");
        System.out.println("9. Count HTML Tags from URL");
        System.out.println("10. DEMO: Run ALL Lab 5 Tasks automatically");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            printMessage("Shape list is empty.");
            return;
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void printTotalArea(double totalArea) {
        DecimalFormat df = new DecimalFormat("#.##");
        printMessage("Total area: " + df.format(totalArea));
    }
}
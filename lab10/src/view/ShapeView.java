package view;

import model.Shape;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShapeView {

    private ResourceBundle bundle;

    public ShapeView() {
        setLocale(Locale.ENGLISH);
    }

    public void setLocale(Locale locale) {
        this.bundle = ResourceBundle.getBundle("resources.location.messages", locale);
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public void printMenu() {
        System.out.println("\n" + getString("menu.title"));
        System.out.println(getString("menu.1"));
        System.out.println(getString("menu.2"));
        System.out.println(getString("menu.3"));
        System.out.println(getString("menu.4"));
        System.out.println(getString("menu.5"));
        System.out.println(getString("menu.6"));
        System.out.println(getString("menu.7"));
        System.out.println(getString("menu.8"));
        System.out.println(getString("menu.9"));
        System.out.println(getString("menu.10"));
        System.out.println(getString("menu.lang"));
        System.out.println(getString("menu.0"));
        System.out.print(getString("menu.select"));
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printBundleMessage(String key) {
        System.out.println(getString(key));
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            printBundleMessage("msg.empty");
            return;
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void printTotalArea(double totalArea) {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(getString("msg.total") + df.format(totalArea));
    }
}
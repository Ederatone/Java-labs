package controller;

import model.*;
import utils.*;
import view.ShapeView;
import java.io.*;
import java.util.*;

public class ShapeController {
    private Shape[] shapes;
    private ShapeView view;
    private FileManager fileManager;
    private Scanner scanner;

    public ShapeController() {
        this.view = new ShapeView();
        this.fileManager = new FileManager();
        this.scanner = new Scanner(System.in);
        this.shapes = createShapes();
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.printMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        view.printShapes(shapes);
                        break;
                    case "2":
                        this.shapes = createShapes();
                        view.printMessage("New random shapes generated.");
                        break;
                    case "3":
                        view.printTotalArea(calculateTotalArea());
                        break;
                    case "4":
                        handleSorting();
                        break;
                    case "5":
                        handleSave();
                        break;
                    case "6":
                        handleLoad();
                        break;
                    case "7":
                        handleMaxWordsTask();
                        break;
                    case "8":
                        handleEncryptionTask();
                        break;
                    case "9":
                        handleUrlTask();
                        break;
                    case "10":
                        runAutoDemo();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        view.printMessage("Invalid option.");
                }
            } catch (Exception e) {
                view.printMessage("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void runAutoDemo() {
        view.printMessage("\n=== AUTOMATED DEMO START ===");

        String demoTextFile = "demo_text.txt";
        createDemoFile(demoTextFile);
        view.printMessage("Created temporary file: " + demoTextFile);

        view.printMessage("\n--- Task 1: Find Max Words ---");
        try {
            String res = fileManager.findLineWithMaxWords(demoTextFile);
            view.printMessage(res);
        } catch (IOException e) {
            view.printMessage("Task 1 failed: " + e.getMessage());
        }

        view.printMessage("\n--- Task 2: Save and Load Shapes ---");
        String demoShapeFile = "demo_shapes.dat";
        try {
            fileManager.saveShapes(shapes, demoShapeFile);
            Shape[] loaded = fileManager.loadShapes(demoShapeFile);
            view.printMessage("Shapes saved and loaded successfully. Count: " + loaded.length);
        } catch (Exception e) {
            view.printMessage("Task 2 failed: " + e.getMessage());
        }

        view.printMessage("\n--- Task 3: Encryption (Key: 'A') ---");
        String encFile = "demo_encrypted.txt";
        String decFile = "demo_decrypted.txt";
        char key = 'A';
        try {
            try (Reader reader = new FileReader(demoTextFile);
                 Writer writer = new CipherStreams.EncryptionWriter(new FileWriter(encFile), key)) {
                int c;
                while ((c = reader.read()) != -1) writer.write(c);
            }
            view.printMessage("Encrypted to " + encFile);

            try (Reader reader = new CipherStreams.DecryptionReader(new FileReader(encFile), key);
                 Writer writer = new FileWriter(decFile)) {
                int c;
                while ((c = reader.read()) != -1) writer.write(c);
            }
            view.printMessage("Decrypted to " + decFile);

        } catch (IOException e) {
            view.printMessage("Task 3 failed: " + e.getMessage());
        }

        view.printMessage("\n--- Task 4: URL Tags (youtube.com) ---");
        new HtmlTagCounter().analyzeTags("https://www.youtube.com");

        view.printMessage("\n=== DEMO COMPLETE ===");
    }

    private void createDemoFile(String filename) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println("Hello world this is a test");
            out.println("This line has significantly more words than the first line to test the counter");
            out.println("Short line");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleSave() {
        view.printMessage("Enter filename to save (e.g., shapes.dat): ");
        String path = scanner.nextLine();
        try {
            fileManager.saveShapes(shapes, path);
        } catch (IOException e) {
            view.printMessage("Save failed: " + e.getMessage());
        }
    }

    private void handleLoad() {
        view.printMessage("Enter filename to load: ");
        String path = scanner.nextLine();
        try {
            shapes = fileManager.loadShapes(path);
            view.printMessage("Shapes loaded!");
            view.printShapes(shapes);
        } catch (Exception e) {
            view.printMessage("Load failed: " + e.getMessage());
        }
    }

    private void handleMaxWordsTask() {
        view.printMessage("Enter file path to analyze: ");
        String path = scanner.nextLine();
        try {
            String result = fileManager.findLineWithMaxWords(path);
            view.printMessage(result);
        } catch (IOException e) {
            view.printMessage("Error reading file: " + e.getMessage());
        }
    }

    private void handleEncryptionTask() {
        view.printMessage("Enter 'e' to encrypt or 'd' to decrypt: ");
        String mode = scanner.nextLine();
        view.printMessage("Enter input file path: ");
        String inFile = scanner.nextLine();
        view.printMessage("Enter output file path: ");
        String outFile = scanner.nextLine();
        view.printMessage("Enter key character (single char): ");
        String keyStr = scanner.nextLine();

        if (keyStr.length() != 1) {
            view.printMessage("Key must be exactly one character.");
            return;
        }
        char key = keyStr.charAt(0);

        try {
            if (mode.equalsIgnoreCase("e")) {
                try (Reader reader = new FileReader(inFile);
                     Writer writer = new CipherStreams.EncryptionWriter(new FileWriter(outFile), key)) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        writer.write(c);
                    }
                }
                view.printMessage("Encryption complete.");
            } else if (mode.equalsIgnoreCase("d")) {
                try (Reader reader = new CipherStreams.DecryptionReader(new FileReader(inFile), key);
                     Writer writer = new FileWriter(outFile)) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        writer.write(c);
                    }
                }
                view.printMessage("Decryption complete.");
            } else {
                view.printMessage("Unknown mode.");
            }
        } catch (IOException e) {
            view.printMessage("IO Error: " + e.getMessage());
        }
    }

    private void handleUrlTask() {
        view.printMessage("Enter URL (e.g., https://google.com): ");
        String url = scanner.nextLine();
        new HtmlTagCounter().analyzeTags(url);
    }

    private void handleSorting() {
        view.printMessage("Sort by: a) Area, b) Color");
        String sortType = scanner.nextLine();
        if (sortType.equalsIgnoreCase("a")) {
            Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
            view.printShapes(shapes);
        } else if (sortType.equalsIgnoreCase("b")) {
            Arrays.sort(shapes, Comparator.comparing(Shape::getShapeColor));
            view.printShapes(shapes);
        } else {
            view.printMessage("Invalid sort type.");
        }
    }

    private double calculateTotalArea() {
        return Arrays.stream(shapes).mapToDouble(Shape::calcArea).sum();
    }

    private Shape[] createShapes() {
        Random random = new Random();
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Black", "White"};
        Shape[] newShapes = new Shape[10];

        for (int i = 0; i < newShapes.length; i++) {
            String color = colors[random.nextInt(colors.length)];
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    double w = 1 + random.nextDouble() * 20;
                    double h = 1 + random.nextDouble() * 20;
                    newShapes[i] = new Rectangle(color, w, h);
                    break;
                case 1:
                    double r = 1 + random.nextDouble() * 15;
                    newShapes[i] = new Circle(color, r);
                    break;
                case 2:
                    double side = 5 + random.nextDouble() * 15;
                    newShapes[i] = new Triangle(color, side, side, side);
                    break;
            }
        }
        return newShapes;
    }
}
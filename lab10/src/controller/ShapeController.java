package controller;

import model.*;
import utils.*;
import view.ShapeView;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class ShapeController {
    private Shape[] shapes;
    private ShapeView view;
    private FileManager fileManager;
    private Scanner scanner;

    private static final Logger logger = Logger.getLogger("Main");
    private boolean isEnglish = true;

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
                logger.info("User selected option: " + choice);

                switch (choice) {
                    case "1":
                        view.printShapes(shapes);
                        break;
                    case "2":
                        this.shapes = createShapes();
                        view.printBundleMessage("msg.generated");
                        logger.info("New shapes generated.");
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
                    case "11":
                        changeLanguage();
                        break;
                    case "0":
                        running = false;
                        logger.info("User exited the application.");
                        break;
                    default:
                        view.printMessage("Invalid option.");
                        logger.warning("Invalid menu option selected: " + choice);
                }
            } catch (Exception e) {
                String errorMsg = view.getString("msg.error") + e.getMessage();
                view.printMessage(errorMsg);
                logger.severe("Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void changeLanguage() {
        if (isEnglish) {
            view.setLocale(new Locale("ua"));
            isEnglish = false;
        } else {
            view.setLocale(Locale.ENGLISH);
            isEnglish = true;
        }
        view.printBundleMessage("msg.lang.changed");
        logger.info("Language switched. English: " + isEnglish);
    }

    private void handleSave() {
        view.printMessage("Enter filename to save (e.g., shapes.dat): ");
        String path = scanner.nextLine();
        try {
            fileManager.saveShapes(shapes, path);
            view.printBundleMessage("msg.saved");
            logger.info("Shapes saved to " + path);
        } catch (IOException e) {
            logger.severe("Save failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleLoad() {
        view.printMessage("Enter filename to load: ");
        String path = scanner.nextLine();
        try {
            shapes = fileManager.loadShapes(path);
            view.printBundleMessage("msg.loaded");
            view.printShapes(shapes);
            logger.info("Shapes loaded from " + path);
        } catch (Exception e) {
            logger.severe("Load failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleMaxWordsTask() {
        view.printMessage("Enter file path to analyze: ");
        String path = scanner.nextLine();
        try {
            String result = fileManager.findLineWithMaxWords(path);
            view.printMessage(result);
            logger.info("Max words task completed for file: " + path);
        } catch (IOException e) {
            logger.severe("Max words task failed: " + e.getMessage());
            throw new RuntimeException(e);
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
                logger.info("File encrypted: " + inFile);
            } else if (mode.equalsIgnoreCase("d")) {
                try (Reader reader = new CipherStreams.DecryptionReader(new FileReader(inFile), key);
                     Writer writer = new FileWriter(outFile)) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        writer.write(c);
                    }
                }
                view.printMessage("Decryption complete.");
                logger.info("File decrypted: " + inFile);
            } else {
                view.printMessage("Unknown mode.");
            }
        } catch (IOException e) {
            logger.severe("Encryption task failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleUrlTask() {
        view.printMessage("Enter URL (e.g., https://google.com): ");
        String url = scanner.nextLine();
        new HtmlTagCounter().analyzeTags(url);
        logger.info("URL analysis completed: " + url);
    }

    private void runAutoDemo() {
        view.printMessage("\n=== STARTING AUTOMATED DEMO ===");
        logger.info("Demo run started.");

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
        logger.info("Demo run finished.");
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

    private void handleSorting() {
        view.printMessage("Sort by: a) Area, b) Color");
        String sortType = scanner.nextLine();
        if (sortType.equalsIgnoreCase("a")) {
            Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
            view.printShapes(shapes);
            logger.info("Shapes sorted by Area");
        } else if (sortType.equalsIgnoreCase("b")) {
            Arrays.sort(shapes, Comparator.comparing(Shape::getShapeColor));
            view.printShapes(shapes);
            logger.info("Shapes sorted by Color");
        }
    }

    private double calculateTotalArea() {
        return Arrays.stream(shapes).mapToDouble(Shape::calcArea).sum();
    }

    private Shape[] createShapes() {
        Random random = new Random();
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Purple"};
        Shape[] newShapes = new Shape[5];
        for (int i = 0; i < newShapes.length; i++) {
            String color = colors[random.nextInt(colors.length)];
            int type = random.nextInt(2);
            if (type == 0) {
                double w = 1 + random.nextDouble() * 10;
                double h = 1 + random.nextDouble() * 10;
                newShapes[i] = new Rectangle(color, w, h);
            } else {
                double r = 1 + random.nextDouble() * 10;
                newShapes[i] = new Circle(color, r);
            }
        }
        return newShapes;
    }
}
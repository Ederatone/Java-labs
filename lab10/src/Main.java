import controller.ShapeController;
import java.io.IOException;
import java.util.logging.*;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setupLogger();

        logger.info("Application started.");
        ShapeController controller = new ShapeController();
        controller.run();
        logger.info("Application finished.");
    }

    private static void setupLogger() {
        try {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setEncoding("UTF-8");
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("app_log.txt", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setEncoding("UTF-8");
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }
}
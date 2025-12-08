package utils;

import model.Shape;

import java.io.*;

public class FileManager {

    public void saveShapes(Shape[] shapes, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
            System.out.println("Shapes saved successfully to " + filePath);
        }
    }

    public Shape[] loadShapes(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Shape[]) ois.readObject();
        }
    }

    public String findLineWithMaxWords(String filePath) throws IOException {
        String maxLine = "";
        int maxCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().split("\\s+");
                if (words.length > maxCount) {
                    maxCount = words.length;
                    maxLine = line;
                }
            }
        }
        return "Max words (" + maxCount + "): " + maxLine;
    }
}
import java.lang.reflect.Field;
import java.util.Scanner;

public class ReflectionTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String literalString = "Java";

        System.out.print("Enter a string: ");
        String inputString = scanner.next();

        System.out.println("\n--- Before Modification ---");
        System.out.println("Literal: " + literalString);
        System.out.println("Input:   " + inputString);

        System.out.print("\nEnter new value for replacement: ");
        String newValue = scanner.next();

        try {
            modifyString(literalString, newValue);
            modifyString(inputString, newValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n--- After Modification ---");
        System.out.println("Literal: " + literalString);
        System.out.println("Input:   " + inputString);

        System.out.print("Proof of literal pool change: ");
        System.out.println("Java");
    }

    private static void modifyString(String target, String newValue) throws Exception {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);

        Object value = valueField.get(target);

        if (value instanceof char[]) {
            char[] newChars = newValue.toCharArray();
            valueField.set(target, newChars);
        } else if (value instanceof byte[]) {
            byte[] newBytes = newValue.getBytes();
            valueField.set(target, newBytes);
        }
    }
}
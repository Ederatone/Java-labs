import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        String input = "Java lambda test";

        String[] result = findWordWithMinDistinct(input);

        System.out.println("Input text: \"" + input + "\"");

        if (result.length > 0) {
            System.out.println("Result: " + result[0]);
        } else {
            System.out.println("Result: ");
        }
    }

    public static String[] findWordWithMinDistinct(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new String[]{};
        }

        String minWord = Arrays.stream(text.trim().split("\\s+"))
                .min(Comparator.comparingLong(word -> word.chars().distinct().count()))
                .orElse(null);

        return minWord != null ? new String[]{minWord} : new String[]{};
    }
}
import java.util.HashSet;
import java.util.Set;

public class MinimalUniqueChars {

    public static void main(String[] args) {

        String inputText = "Java programming language test";

        String[] result = findWord(inputText);

        System.out.println("Input text: \"" + inputText + "\"");
        System.out.println("Result: " + result[0]);

    }

    public static String[] findWord(String text) {

        if (text == null || text.trim().isEmpty()) {
            return new String[0];
        }
        String[] words = text.trim().split("\\s+");

        String wordWithMinUniqueChars = null;
        int minCount = Integer.MAX_VALUE;

        for (String currentWord : words) {

            Set<Character> uniqueChars = new HashSet<>();
            for (char c : currentWord.toCharArray()) {
                uniqueChars.add(c);
            }
            int currentUniqueCount = uniqueChars.size();

            if (currentUniqueCount < minCount) {
                minCount = currentUniqueCount;
                wordWithMinUniqueChars = currentWord;
            }
        }

        if (wordWithMinUniqueChars == null) {
            return new String[0];
        }

        return new String[]{wordWithMinUniqueChars};
    }

}
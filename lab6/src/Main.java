import java.util.*;
import java.util.regex.Pattern;

class Translator {
    private Map<String, String> dictionary;

    public Translator() {
        this.dictionary = new LinkedHashMap<>();
    }

    public void addWord(String englishWord, String ukrainianWord) {
        dictionary.put(englishWord.toLowerCase(), ukrainianWord.toLowerCase());
    }

    public String translatePhrase(String phrase) {
        String result = phrase;

        List<String> keys = new ArrayList<>(dictionary.keySet());

        keys.sort((s1, s2) -> Integer.compare(s2.length(), s1.length()));

        for (String key : keys) {
            String translation = dictionary.get(key);
            String regex = "(?i)\\b" + Pattern.quote(key) + "\\b";
            result = result.replaceAll(regex, translation);
        }

        return result;
    }

    public void showAllWords() {
        if (dictionary.isEmpty()) {
            System.out.println("The dictionary is empty.");
        } else {
            System.out.println("\n--- All Words in Dictionary ---");
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("Total words: " + dictionary.size());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
        translator.addWord("programming", "програмування");
        translator.addWord("language", "мова");

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add new words/phrases");
            System.out.println("2. Translate phrase");
            System.out.println("3. Show all words");
            System.out.println("0. Exit program");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    while (true) {
                        System.out.println("\n--- Add Mode (Type '0' to go back) ---");
                        System.out.print("Enter English word or phrase: ");
                        String eng = scanner.nextLine().trim();

                        if (eng.equals("0")) break;

                        System.out.print("Enter Ukrainian translation: ");
                        String ukr = scanner.nextLine().trim();

                        if (ukr.equals("0")) break;

                        if (!eng.isEmpty() && !ukr.isEmpty()) {
                            translator.addWord(eng, ukr);
                            System.out.println("Saved: " + eng + " -> " + ukr);
                        } else {
                            System.out.println("Error: Fields cannot be empty.");
                        }
                    }
                    break;

                case "2":
                    while (true) {
                        System.out.println("\n--- Translate Mode (Type '0' to go back) ---");
                        System.out.print("Enter phrase in English: ");
                        String phrase = scanner.nextLine().trim();

                        if (phrase.equals("0")) break;

                        if (!phrase.isEmpty()) {
                            String result = translator.translatePhrase(phrase);
                            System.out.println("Translation: " + result);
                        }
                    }
                    break;

                case "3":
                    translator.showAllWords();
                    break;

                case "0":
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
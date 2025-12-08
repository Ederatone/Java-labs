package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagCounter {

    public void analyzeTags(String urlString) {
        Map<String, Integer> tagCounts = new HashMap<>();

        try {
            URL url = new URL(urlString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            Pattern pattern = Pattern.compile("<([a-zA-Z][a-zA-Z0-9]*)\\b[^>]*>");

            while ((inputLine = in.readLine()) != null) {
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    String tagName = matcher.group(1).toLowerCase();
                    tagCounts.put(tagName, tagCounts.getOrDefault(tagName, 0) + 1);
                }
            }
            in.close();

            if (tagCounts.isEmpty()) {
                System.out.println("No tags found.");
                return;
            }

            System.out.println("\n--- Tags (Alphabetical Order) ---");
            TreeMap<String, Integer> sortedByAlpha = new TreeMap<>(tagCounts);
            sortedByAlpha.forEach((k, v) -> System.out.println(k + ": " + v));

            System.out.println("\n--- Tags (Frequency Order - Ascending) ---");
            tagCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        } catch (Exception e) {
            System.err.println("Error reading URL: " + e.getMessage());
        }
    }
}
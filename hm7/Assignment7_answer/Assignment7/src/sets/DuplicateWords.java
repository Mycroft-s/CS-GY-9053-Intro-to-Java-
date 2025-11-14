package sets;
import java.util.*;
import java.io.*;

public class DuplicateWords {

    public static String cleanString(String originalString) {
        String cleanedString = originalString.replaceAll("\\p{Punct}", "");
        cleanedString = cleanedString.replaceAll("[“’”]+", "");
        return cleanedString.toLowerCase();
    }

    public static void main(String[] args) throws IOException {
        String path = args.length > 0 ? args[0] : "little_women.txt";

        Set<String> once = new HashSet<>();
        Set<String> dup = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    w = cleanString(w);
                    if (w.isEmpty()) continue;

                    if (dup.contains(w)) continue;
                    if (once.contains(w)) {
                        once.remove(w);
                        dup.add(w);
                    } else {
                        once.add(w);
                    }
                }
            }
        }

        System.out.println("number of duplicate words: " + dup.size());
        System.out.println("number of single words: " + once.size());
    }
}

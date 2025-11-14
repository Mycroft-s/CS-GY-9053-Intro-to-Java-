package maps;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class UrlLog {

    private static final int MAX_RECENT = 5;

    public static void main(String[] args) {
        // default file path, can also pass as argument
        String path = args.length > 0 ? args[0] : "server_log.csv";

        // user -> recent 5 URLs
        Map<String, ArrayDeque<String>> userToRecent = new LinkedHashMap<>();
        // url -> visit count
        Map<String, Integer> urlCount = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // skip header line if present
                if (first) {
                    first = false;
                    if (line.toLowerCase(Locale.ROOT).startsWith("timestamp,")) {
                        continue;
                    }
                }

                // split CSV: timestamp,userid,url
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;
                String timestamp = parts[0].trim(); // not used but kept
                String user = parts[1].trim();
                String url = parts[2].trim();

                // record in userToRecent
                ArrayDeque<String> dq = userToRecent.computeIfAbsent(user, k -> new ArrayDeque<>());
                if (dq.size() == MAX_RECENT) dq.pollFirst(); // remove oldest
                dq.addLast(url);

                // count url frequency
                urlCount.merge(url, 1, Integer::sum);
            }
        } catch (IOException e) {
            System.err.println("Cannot read file: " + path);
            e.printStackTrace();
            return;
        }

        // --- print user recent logs ---
        System.out.println("Recent URLs per user:");
        for (Map.Entry<String, ArrayDeque<String>> e : userToRecent.entrySet()) {
            List<String> list = new ArrayList<>(e.getValue());
            System.out.printf("%s -> %s%n", e.getKey(), list);
        }

        // --- print URL visit counts ---
        System.out.println("\nURL Visit Counts:");
        List<Map.Entry<String, Integer>> sorted = urlCount.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toList());

        int maxLen = sorted.stream().mapToInt(a -> a.getKey().length()).max().orElse(0);
        for (Map.Entry<String, Integer> e : sorted) {
            System.out.printf("  %-" + maxLen + "s : %d%n", e.getKey(), e.getValue());
        }
    }
}

import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
    private CsvUtils() {}

    public static String[] parse(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inQuotes) {
                if (ch == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"'); i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(ch);
                }
            } else {
                if (ch == ',') {
                    out.add(cur.toString());
                    cur.setLength(0);
                } else if (ch == '"') {
                    inQuotes = true;
                } else {
                    cur.append(ch);
                }
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    public static String join(List<String> fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(escape(fields.get(i)));
        }
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        boolean needQuote = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String v = s.replace("\"", "\"\"");
        return needQuote ? "\"" + v + "\"" : v;
    }
}

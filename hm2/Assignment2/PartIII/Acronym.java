
public class Acronym {

    public static String createAcronym(String input) {
        String result = "";

        String[] words = input.split(" ");
        for (String word : words) {
            if (word.length() > 0) {
                char c = Character.toUpperCase(word.charAt(0));
                result += c;  // 
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s1 = "as soon as possible";
        String s2 = "The quick brown fox jumps over the lazy dog";

        System.out.println("Acronym of s1: " + createAcronym(s1)); //  ASAP
        System.out.println("Acronym of s2: " + createAcronym(s2)); // TQBFJOTLD
    }
}


public class MirrorString {

    public static String mirrorString(String input) {
        String reversed = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed += input.charAt(i);
        }
        return input + reversed;
    }

    public static void main(String[] args) {
        String input1 = "hello";
        String input2 = "java";
        String input3 = "a";

        System.out.println("Mirror of " + input1 + " is " + mirrorString(input1));
        System.out.println("Mirror of " + input2 + " is " + mirrorString(input2));
        System.out.println("Mirror of " + input3 + " is " + mirrorString(input3));
    }
}

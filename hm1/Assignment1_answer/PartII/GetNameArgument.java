public class GetNameArgument {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide your name as a command line argument.");
            return;
        }

       
        String name = args[0];

        // print
        System.out.println("Hello, " + name + "!");
    }
}

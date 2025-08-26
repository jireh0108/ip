package Nova.parser;

public class Parser {
    public static String parse(String line) {
        String[] parts = line.split(" ", 2);
        return parts[0];
    }
}

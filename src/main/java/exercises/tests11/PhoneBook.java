package exercises.tests11;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PhoneBook {
    public static final String NAMEPATTERN = "^.*[<](.*)[>].*$";
    public static final String TELEPHONEPATTERN = "^.*[+](\\d{1,2}-\\d{3}-\\d{3}-\\d{4}).*$";

    public static String ExtractMatchValue(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches() ? m.group(1) : "";
    }

    public static String ExtractAddress(String str) {
        String value = ExtractMatchValue(str, TELEPHONEPATTERN);
        String result = str.replace(value, "");
        value = ExtractMatchValue(result, NAMEPATTERN);
        value.trim();
        result = result.replace(value, "");
        result = result.replaceAll("_", " ");
        result = result.replaceAll("[^a-zA-Z0-9-\\s.]", "");
        result = result.replaceAll("  ", " ");
        return result.trim();
    }

    public static String FindContact(String strng, String num) {
        List<String> list = Arrays.asList(strng.split("\n"));
        Function<String, Stream<String>> p = (i) -> list.stream().filter(s -> {
            String b = ExtractMatchValue(s, TELEPHONEPATTERN);
            return b.equals(i);
        });
        if (p.apply(num).count() > 1)
            return "Error => Too many people: " + num;
        else if (p.apply(num).count() == 0)
            return "Error => Not found: " + num;
        String value = p.apply(num)
                .findFirst()
                .orElse("");
        String result = "Phone => " + ExtractMatchValue(value, TELEPHONEPATTERN);
        result += ", Name => " + ExtractMatchValue(value, NAMEPATTERN);
        result += ", Address => " + ExtractAddress(value);
        return result;
    }
}

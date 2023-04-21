package project.mainframe.api.project.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generator sanitizer.
 */
public class GeneratorGPTSanitizer {

    /**
     * The regex used to parse the JSON.
     */
    private static final String REGEX = "\\{(?:[^{}]|\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\})*\\}";
    
    /**
     * Sanitize the given string.
     * 
     * @param string The string to sanitize.
     * @return The sanitized string.
     */
    public static String sanitize(String input) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return input;
        }
    }
}

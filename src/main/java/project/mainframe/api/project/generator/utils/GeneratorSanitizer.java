package project.mainframe.api.project.generator.utils;

/**
 * Generator sanitizer.
 */
public class GeneratorSanitizer {

    /**
     * The regex used to remove all whitespace.
     */
    private static final String REGEX = "\\s+";
    
    /**
     * Sanitize the given string.
     * 
     * @param string The string to sanitize.
     * @return The sanitized string.
     */
    public static String sanitize(String input) {

        return input
            .replaceAll(REGEX, "")
            .toLowerCase();
    }
}

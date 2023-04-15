package project.mainframe.api.security.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import project.mainframe.api.security.exceptions.JwtSanitizerException;

/**
 * A sanitizer for JWT tokens.
 * 
 * Details:
 * The sanitizer is used to sanitize JWT tokens. The sanitizer is used to prevent
 * JWT
 * tokens from being too long. The sanitizer is used to prevent JWT tokens from
 * containing
 * malicious code.
 */
public class JwtSanitizer {

    /**
     * Maximum length of a JWT token allowed by the sanitizer.
     */
    private int MAX_TOKEN_LENGTH;

    /**
     * Constructor.
     * 
     * @param maxTokenLength the maximum length of a JWT token allowed by the
     *                       sanitizer
     */
    public JwtSanitizer(int maxTokenLength) {
        this.MAX_TOKEN_LENGTH = maxTokenLength;
    }

    /**
     * Sanitizes the given JWT token.
     * 
     * Details:
     * The token is split into three parts: header, payload and signature. The
     * header
     * is decoded from base64 and then sanitized. The header is then encoded back to
     * base64. The three parts are then concatenated to form the sanitized token.
     * 
     * @param token the token to sanitize
     * 
     * @return the sanitized token
     * 
     * @throws JwtSanitizerException if the token is null or empty
     * 
     * @throws JwtSanitizerException if the token does not have three parts
     * 
     * @throws JwtSanitizerException if the token has empty parts
     * 
     * @throws JwtSanitizerException if the token length exceeds the maximum length
     */
    public String sanitize(String token) throws JwtSanitizerException {
        if (token == null || token.isEmpty()) {
            throw new JwtSanitizerException("Token is null or empty");
        }

        // Validate token format (assuming JWT with three parts)
        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            throw new JwtSanitizerException("Token does not have three parts");
        } else if (parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) {
            throw new JwtSanitizerException("Token has empty parts");
        } else if (token.length() > MAX_TOKEN_LENGTH) {
            throw new JwtSanitizerException("Token length exceeds maximum length");
        }

        String header = sanitizeHeader(parts[0]);
        String payload = parts[1];
        String signature = parts[2];

        return constructTroken(header, payload, signature);
    }

    /**
     * Sanitizes the given JWT header.
     * 
     * Details:
     * The header is decoded from base64 and then sanitized. The header is then
     * encoded back to base64.
     * 
     * @param header the header to sanitize
     * 
     * @return the sanitized header
     */
    private String sanitizeHeader(String header) {
        header = new String(Base64.getUrlDecoder().decode(header), StandardCharsets.UTF_8);
        header = sanitizeString(header);
        header = Base64.getUrlEncoder().encodeToString(header.getBytes());
        return header;
    }

    /**
     * Constructs a JWT token from the given header, payload and signature.
     * 
     * @param header the header of the token
     * 
     * @param payload the payload of the token
     * 
     * @param signature the signature of the token
     * 
     * @return the constructed token
     */
    private String constructTroken(String header, String payload, String signature) {
        return header + "." + payload + "." + signature;
    }

    /**
     * Sanitizes the given string.
     * 
     * Details:
     * The string is sanitized by removing all whitespace.
     * 
     * @param input the string to sanitize
     * 
     * @return the sanitized string
     */
    private String sanitizeString(String input) throws JwtSanitizerException {
        // Remove whitespace
        input = input.replaceAll("\\s", "");

        return input;
    }
}

package project.mainframe.api.security.exceptions;

/**
 * An exception that is thrown when the JWT sanitizer fails to sanitize a JWT.
 */
public class JwtSanitizerException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param msg The message
     */
    public JwtSanitizerException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     *
     * @param msg The message
     * @param cause The cause
     */
    public JwtSanitizerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

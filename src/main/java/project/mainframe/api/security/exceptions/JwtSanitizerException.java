package project.mainframe.api.security.exceptions;

/*
 * An exception that is thrown when the JWT sanitizer fails to sanitize a JWT.
 */
public class JwtSanitizerException extends RuntimeException {
    
        public JwtSanitizerException(String msg) {
            super(msg);
        }
    
        public JwtSanitizerException(String msg, Throwable cause) {
            super(msg, cause);
        }
}

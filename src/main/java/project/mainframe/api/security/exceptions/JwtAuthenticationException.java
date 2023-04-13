package project.mainframe.api.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/*
 * An exception that is thrown when the JWT authentication fails.
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

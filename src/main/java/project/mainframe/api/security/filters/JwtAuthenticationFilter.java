package project.mainframe.api.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.mainframe.api.security.exceptions.JwtAuthenticationException;
import project.mainframe.api.security.exceptions.JwtSanitizerException;
import project.mainframe.api.security.services.JwtRevocationService;
import project.mainframe.api.security.utils.JwtSanitizer;
import project.mainframe.api.security.utils.JwtUtils;

import java.io.IOException;
import java.util.List;

/**
 * This filter is responsible for authenticating users using JWT tokens.
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Error message for whether or not the token is expired
     */
    private static final String TOKEN_EXPIRED_MSG = "JWT token is expired";

    /**
     * Error message for whether or not the token is revoked
     */
    private static final String TOKEN_REVOKED_MSG = "JWT token is revoked";

    /**
     * Error message for whether or not the token is invalid
     */
    private static final String TOKEN_INVALID_MSG = "JWT token is invalid";

    /**
     * Error message for whether or not the token is missing a claim
     */
    private static final String TOKEN_MISSING_CLAIM_MSG = "JWT token is missing a claim";
    
    /**
     * Error message for whether or not the token has an invalid claim
     */
    private static final String TOKEN_INVALID_CLAIM_MSG = "JWT token has an invalid claim";
    
    /**
     * Error message for whether or not the token has an invalid signature
     */
    private static final String TOKEN_INVALID_SIGNATURE_MSG = "JWT token signature is invalid";

    /**
     * The utility that is used to validate and parse JWT tokens.
     */
    private JwtUtils jwtUtils;

    /**
     * The sanitizer that is used to sanitize the JWT token.
     */
    private JwtSanitizer jwtSanitizer;

    /**
     * The service that handles revocation of JWT tokens.
     */
    private JwtRevocationService jwtRevocationService;

    /**
     * Constructor.
     * 
     * @param requestMatcher the request matcher
     * @param jwtUtils the JWT utility
     * @param authenticationManager the authentication manager
     * @param jwtRevocationService the JWT revocation service
     * @param maxTokenLength the maximum length of the token
     */
    public JwtAuthenticationFilter(
            RequestMatcher requestMatcher,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager,
            JwtRevocationService jwtRevocationService,
            int maxTokenLength) {
        super(requestMatcher);
        this.jwtUtils = jwtUtils;
        this.jwtRevocationService = jwtRevocationService;
        this.jwtSanitizer = new JwtSanitizer(maxTokenLength);
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Extracts the JWT token from the request and tries to authenticate the user.
     * 
     * Details:
     * The token is extracted from the Authorization header of the request. The
     * token
     * is then sanitized and validated. If the token is valid, the user is
     * authenticated.
     * 
     * @param request the request to extract the token from
     * 
     * @param response the response to send the authentication result to
     * 
     * @return the authentication result
     * 
     * @throws IOException if an I/O error occurs
     * 
     * @throws ServletException if an error occurs
     * 
     * @throws JwtAuthenticationException if the token is invalid
     * 
     * @throws JwtSanitizerException if the token is too long
     * 
     * @throws ExpiredJwtException if the token is expired
     * 
     * @throws SignatureException if the token signature is invalid
     * 
     * @throws MissingClaimException if the token is missing a claim
     * 
     * @throws InvalidClaimException if the token has an invalid claim
     * 
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String token = extractTokenFromRequest(request);

            if (jwtRevocationService.isRevoked(token)) {
                throw new JwtAuthenticationException(TOKEN_REVOKED_MSG);
            }

            Claims claims = jwtUtils.parseAndValidateJwtToken(token);

            return getAuthentication(claims);

        } catch (JwtSanitizerException e) {
            throw new JwtAuthenticationException(TOKEN_INVALID_MSG);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(TOKEN_EXPIRED_MSG);
        } catch (SignatureException e) {
            throw new JwtAuthenticationException(TOKEN_INVALID_SIGNATURE_MSG);
        } catch (MissingClaimException e) {
            throw new JwtAuthenticationException(TOKEN_MISSING_CLAIM_MSG);
        } catch (InvalidClaimException e) {
            throw new JwtAuthenticationException(TOKEN_INVALID_CLAIM_MSG);
        } catch (Exception e) {
            throw new JwtAuthenticationException(TOKEN_INVALID_MSG);
        }
    }

    /**
     * Sets the authentication object in the SecurityContext.
     * 
     * Details:
     * The authentication object is set to the PreAuthenticatedAuthenticationToken
     * object
     * created in the attemptAuthentication() method.
     * This method is called after the attemptAuthentication() method,
     * if the attemptAuthentication() method does not throw an exception.
     * 
     * @param request the request to extract the token from
     * 
     * @param response the response to send the authentication result to
     * 
     * @param chain the filter chain
     * 
     * @param authResult the authentication result
     * 
     * @throws IOException if an I/O error occurs
     * 
     * @throws ServletException if an error occurs
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    /**
     * Creates a PreAuthenticatedAuthenticationToken object from the JWT token.
     * 
     * Details:
     * The username is extracted from the subject of the JWT token.
     * The authorities are extracted from the claims of the JWT token.
     * The PreAuthenticatedAuthenticationToken object is created with an empty
     * password, because the password is not stored in the JWT token,
     * and the identity of the user is verified by the JWT token instead.
     * 
     * @param claims the claims of the JWT token
     * 
     * @return the PreAuthenticatedAuthenticationToken object
     */
    private PreAuthenticatedAuthenticationToken getAuthentication(Claims claims) {
        String username = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = jwtUtils.getAuthorities(claims);
        return new PreAuthenticatedAuthenticationToken(username, "", authorities);
    }

    /**
     * Extracts the JWT token from the request header.
     * 
     * Details:
     * Expects the token with the "Bearer " prefix in the Authorization header.
     * e.g. "Authorization": "Bearer <token>".
     * The token is sanitized before it is returned, to prevent malicious code.
     * 
     * @param request the request to extract the token from
     * 
     * @return the JWT token
     * 
     * @throws JwtAuthenticationException if the token is invalid
     * 
     * @throws JwtSanitizerException if the token is too long
     */
    private String extractTokenFromRequest(HttpServletRequest request)
            throws JwtSanitizerException, JwtAuthenticationException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return jwtSanitizer.sanitize(token.substring(7));
        }

        throw new JwtAuthenticationException(TOKEN_INVALID_MSG);
    }
}

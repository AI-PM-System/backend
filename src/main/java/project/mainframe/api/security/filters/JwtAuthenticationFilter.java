package project.mainframe.api.security.filters;

import io.jsonwebtoken.Claims;
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
import project.mainframe.api.security.utils.JwtUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(RequestMatcher requestMatcher, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        super(requestMatcher);
        this.jwtUtils = jwtUtils;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = extractTokenFromRequest(request);

        if (token != null && !jwtUtils.isJwtTokenExpired(token)) {            
            Claims claims = jwtUtils.parseJwtToken(token);
            String username = claims.getSubject();
            List<String> authoritiesList = Arrays.asList(claims.get("authorities").toString().split(","));
            List<SimpleGrantedAuthority> authorities = authoritiesList.stream()
                .flatMap(authority -> Arrays.stream(authority.split(",")))
                .map(a -> new SimpleGrantedAuthority(a.replace("[", "").replace("]", "")))
                .collect(Collectors.toList());
            return new PreAuthenticatedAuthenticationToken(username, "", authorities);
        } else {
            throw new JwtAuthenticationException("Invalid JWT token");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}

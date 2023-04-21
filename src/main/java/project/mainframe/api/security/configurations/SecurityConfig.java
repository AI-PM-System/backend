package project.mainframe.api.security.configurations;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import project.mainframe.api.security.filters.JwtAuthenticationFilter;
import project.mainframe.api.security.services.AuthenticatableDetailsService;
import project.mainframe.api.security.services.JwtRevocationService;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * Security configuration.
 */
@Configuration
public class SecurityConfig {

    /**
     * This service is responsible for loading users from the database,
     * but it differs from the one above in that it implements the
     * UserDetailsService interface which is required by Spring Security
     * to create the authentication provider.
     */
    private AuthenticatableDetailsService authenticatableDetailsService;

    /**
     * This service is responsible for revoking JWT tokens.
     */
    private JwtRevocationService jwtRevocationService;

    /**
     * This service is responsible for generating and validating JWT tokens.
     */
    private JwtUtils jwtUtils;

    /**
     * This is the maximum length of a JWT token.
     */
    @Value("${jwt.max-token-length}")
    private int MAX_TOKEN_LENGTH;

    /**
     * 
     * Constructor.
     * 
     * @param authenticatableDetailsService The service used to load the user from the database
     * @param jwtRevocationService The service used to revoke JWT tokens
     * @param jwtUtils The service used to generate and validate JWT tokens
     */
    public SecurityConfig(                 
        AuthenticatableDetailsService authenticatableDetailsService,
        JwtRevocationService jwtRevocationService,
        JwtUtils jwtUtils
    ) {     
        this.authenticatableDetailsService = authenticatableDetailsService;
        this.jwtRevocationService = jwtRevocationService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Configures the security filter chain.
     * 
     * @param http the HTTP security
     * @return the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {   
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable();
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/h2*/**").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/actuator/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/public/**").permitAll()
            .requestMatchers(HttpMethod.PUT, "/api/v1/public/**").permitAll()
            .requestMatchers(HttpMethod.PATCH, "/api/v1/public/**").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/v1/public/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v1/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAnyRole("USER", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v1/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasRole("ADMIN")
            
        .anyRequest().authenticated())
        .addFilterBefore(
            new JwtAuthenticationFilter(
                new AntPathRequestMatcher("/api/v1/user/**"), 
                jwtUtils, 
                authenticationManager(), 
                jwtRevocationService, 
                MAX_TOKEN_LENGTH
            ), 
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
            new JwtAuthenticationFilter(
                new AntPathRequestMatcher("/api/v1/admin/**"), 
                jwtUtils, 
                authenticationManager(), 
                jwtRevocationService, 
                MAX_TOKEN_LENGTH
            ), 
            UsernamePasswordAuthenticationFilter.class); 

    return http.build();
    }    


    /**
     * Configures the password encoder.
     * 
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager.
     * 
     * @return the authentication manager
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(
            Collections.singletonList(authenticationProvider())
        );
    }

    /**
     * Configures the authentication provider.
     * 
     * @return the authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticatableDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

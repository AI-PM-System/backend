package project.mainframe.api.security.configurations;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

import project.mainframe.api.security.entities.Authenticatable;
import project.mainframe.api.security.filters.JwtAuthenticationFilter;
import project.mainframe.api.security.repositories.AuthenticatableRepository;
import project.mainframe.api.security.services.AuthenticatableDetailsService;
import project.mainframe.api.security.services.JwtRevocationService;
import project.mainframe.api.security.utils.JwtUtils;

@Configuration
public class SecurityConfig implements ApplicationRunner {

    /*
     * This service is responsible for loading users from the database.
     */
    private AuthenticatableRepository authenticatableRepository;

    /*
     * This service is responsible for loading users from the database,
     * but it differs from the one above in that it implements the
     * UserDetailsService interface which is required by Spring Security
     * to create the authentication provider.
     */
    private AuthenticatableDetailsService authenticatableDetailsService;

    /*
     * This service is responsible for revoking JWT tokens.
     */
    private JwtRevocationService jwtRevocationService;

    /*
     * This service is responsible for generating and validating JWT tokens.
     */
    private JwtUtils jwtUtils;

    /*
     * This is the default username and password that will be created
     * when the application starts.
     */
    @Value("${default.user.name}")
    private String defaultUserName;

    /*
     * This is the default username and password that will be created
     * when the application starts.
     */
    @Value("${default.user.password}")
    private String defaultUserPassword;

    /*
     * This is the maximum length of a JWT token.
     */
    @Value("${jwt.max-token-length}")
    private int MAX_TOKEN_LENGTH;

    public SecurityConfig(
        AuthenticatableRepository authenticatableRepository,                   
        AuthenticatableDetailsService authenticatableDetailsService,
        JwtRevocationService jwtRevocationService,
        JwtUtils jwtUtils) {
        this.authenticatableRepository = authenticatableRepository;        
        this.authenticatableDetailsService = authenticatableDetailsService;
        this.jwtRevocationService = jwtRevocationService;
        this.jwtUtils = jwtUtils;
    }

    /*
     * Creates the default user.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (authenticatableRepository.existsById(defaultUserName)) {
            return;
        }

        authenticatableRepository.save(
            Authenticatable.builder()
                .username(defaultUserName)
                .password(passwordEncoder().encode(defaultUserPassword))
                .authorities(Collections.singletonList("ROLE_ADMIN"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());
    }

    /*
     * Configures the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {   
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable();
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/h2*/**").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
        .anyRequest().authenticated())
        .addFilterBefore(
            new JwtAuthenticationFilter(
                new AntPathRequestMatcher("/api/**"), 
                jwtUtils, 
                authenticationManager(), 
                jwtRevocationService, 
                MAX_TOKEN_LENGTH), 
            // Add the JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
            UsernamePasswordAuthenticationFilter.class); 

    return http.build();
    }    


    /*
     * Configures the password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Configures the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(
            Collections.singletonList(authenticationProvider())
        );
    }

    /*
     * Configures the authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticatableDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

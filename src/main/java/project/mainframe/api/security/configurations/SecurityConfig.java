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
import project.mainframe.api.security.utils.JwtUtils;

@Configuration
public class SecurityConfig implements ApplicationRunner {

    private AuthenticatableRepository authenticatableRepository;
    private AuthenticatableDetailsService authenticatableDetailsService;

    private JwtUtils jwtUtils;

    @Value("${default.user.name}")
    private String defaultUserName;

    @Value("${default.user.password}")
    private String defaultUserPassword;

    public SecurityConfig(AuthenticatableRepository authenticatableRepository, JwtUtils jwtUtils,
            AuthenticatableDetailsService authenticatableDetailsService) {
        this.authenticatableRepository = authenticatableRepository;
        this.jwtUtils = jwtUtils;
        this.authenticatableDetailsService = authenticatableDetailsService;
    }

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
            new JwtAuthenticationFilter(new AntPathRequestMatcher("/api/**"), jwtUtils, authenticationManager()), 
            // Add the JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
            UsernamePasswordAuthenticationFilter.class); 

    return http.build();
    }    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(
            Collections.singletonList(authenticationProvider())
        );
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticatableDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

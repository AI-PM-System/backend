package project.mainframe.api.security.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:5173",
        "https://bergandersen.com",
        "https://ai-pm-system.github.io",
        "https://static.bergandersen.com"
    };

    private static final String[] ALLOWED_METHODS = {
        "GET",
        "POST",
        "PUT",
        "DELETE",
        "OPTIONS"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(ALLOWED_ORIGINS)
            .allowedMethods(ALLOWED_METHODS);
    }
}

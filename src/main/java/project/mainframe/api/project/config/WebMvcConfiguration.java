package project.mainframe.api.project.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import project.mainframe.api.project.resolvers.AuthorizationHeaderMethodArgumentResolver;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationHeaderMethodArgumentResolver authorizationHeaderMethodArgumentResolver;

    public WebMvcConfiguration(AuthorizationHeaderMethodArgumentResolver authorizationHeaderMethodArgumentResolver) {
        this.authorizationHeaderMethodArgumentResolver = authorizationHeaderMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authorizationHeaderMethodArgumentResolver);
    }
}

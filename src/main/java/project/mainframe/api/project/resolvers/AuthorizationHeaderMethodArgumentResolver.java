package project.mainframe.api.project.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.entities.User;

@Component
public class AuthorizationHeaderMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserResolver userResolver;

    public AuthorizationHeaderMethodArgumentResolver(UserResolver userResolver) {
        this.userResolver = userResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Authorization.class) != null && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");
        return userResolver.resolve(authorization);
    }
}


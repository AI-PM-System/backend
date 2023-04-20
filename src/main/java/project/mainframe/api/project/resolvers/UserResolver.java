package project.mainframe.api.project.resolvers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import project.mainframe.api.project.entities.User;
import project.mainframe.api.security.services.AuthenticatableIdentityService;
import project.mainframe.api.security.utils.JwtUtils;

@Component
public class UserResolver extends AuthenticatableIdentityService<User, String> {

    /**
     * Constructor.
     * 
     * @param jwtUtils The jwt utils.
     * @param authenticatableRepository The authenticatable repository.
     */
    public UserResolver(JwtUtils jwtUtils, JpaRepository<User, String> authenticatableRepository) {
        super(jwtUtils, authenticatableRepository);
    }

    public User resolve(String authorization) {
        return getAuthenticatableIdentity(authorization);
    }
}

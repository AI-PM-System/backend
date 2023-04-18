package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.project.entities.User;
import project.mainframe.api.security.services.AuthenticatableIdentityService;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * The User Identity Service.
 */
@Service
public class UserIdentityService extends AuthenticatableIdentityService<User, String> {

    /**
     * Constructor.
     * 
     * @param jwtUtils The jwt utils.
     * @param authenticatableRepository The authenticatable repository.
     */
    public UserIdentityService(JwtUtils jwtUtils, JpaRepository<User, String> authenticatableRepository) {
        super(jwtUtils, authenticatableRepository);
    }
}

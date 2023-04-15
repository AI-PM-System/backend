package project.mainframe.api.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.mainframe.api.security.entities.Authenticatable;
import project.mainframe.api.security.repositories.AuthenticatableRepository;

/**
 * This class is used by Spring Security to load the user from the database.
 */
@Service
public class AuthenticatableDetailsService implements UserDetailsService {
    
    /**
     * The repository used to load the user from the database
     */
    private AuthenticatableRepository authenticatableRepository;

    /**
     * Constructor.
     * 
     * @param authenticatableRepository The repository used to load the user from the database
     */
    public AuthenticatableDetailsService(AuthenticatableRepository authenticatableRepository) {
        this.authenticatableRepository = authenticatableRepository;
    }

    /**
     * This method is called by Spring Security when a user tries to authenticate.
     * It is used to load the user from the database.
     * 
     * @param username The username of the user
     * @return The user
     * 
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public Authenticatable loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticatableRepository
            .findById(username)
            .orElseThrow(() -> 
                new UsernameNotFoundException("User not found"));
    }
}

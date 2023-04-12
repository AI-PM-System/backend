package project.mainframe.api.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.mainframe.api.security.entities.Authenticatable;
import project.mainframe.api.security.repositories.AuthenticatableRepository;

@Service
public class AuthenticatableDetailsService implements UserDetailsService {
    
    private AuthenticatableRepository authenticatableRepository;

    public AuthenticatableDetailsService(AuthenticatableRepository authenticatableRepository) {
        this.authenticatableRepository = authenticatableRepository;
    }

    @Override
    public Authenticatable loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticatableRepository
            .findById(username)
            .orElseThrow(() -> 
                new UsernameNotFoundException("User not found"));
    }
}

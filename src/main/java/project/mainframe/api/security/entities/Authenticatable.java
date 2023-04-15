package project.mainframe.api.security.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An authenticatable is a user that can be authenticated
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class Authenticatable implements UserDetails {

    /**
     * The username of the authenticatable
     */
    @Id
    private String username;

    /**
     * The password of the authenticatable
     */
    private String password;

    /**
     * Whether the account is expired
     */
    private boolean accountNonExpired;

    /**
     * Whether the account is locked
     */
    private boolean accountNonLocked;

    /**
     * Whether the credentials are expired
     */
    private boolean credentialsNonExpired;

    /**
     * Whether the account is enabled
     */
    private boolean enabled;

    /**
     * The authorities of the authenticatable
     */
    private List<String> authorities;

    /**
     * Get the authorities of the authenticatable
     * 
     * @return The authorities of the authenticatable
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> (GrantedAuthority) () -> authority)
                .toList();
    }

    /**
     * Check if the account is expired
     * 
     * @return Whether the account is expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * Check if the account is locked
     * 
     * @return Whether the account is locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Check if the credentials are expired
     * 
     * @return Whether the credentials are expired  
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Check if the account is enabled
     * 
     * @return Whether the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * No-args constructor
     */
    public Authenticatable() {}
}

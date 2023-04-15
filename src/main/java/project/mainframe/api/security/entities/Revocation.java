package project.mainframe.api.security.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A revocation is a token that is no longer valid
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Revocation {
    
    /**
     * The token that is no longer valid
     */
    @Id
    private String token;

    /**
     * When was the token revoked?
     */
    private LocalDateTime until;
}

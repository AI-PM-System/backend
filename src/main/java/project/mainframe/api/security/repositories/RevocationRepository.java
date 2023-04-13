package project.mainframe.api.security.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import project.mainframe.api.security.entities.Revocation;

public interface RevocationRepository extends JpaRepository<Revocation, String> {
    
    /*
     * Delete all revocations that are expired
     * 
     * @param now the current time
     */
    void deleteByUntilBefore(LocalDateTime now);
}

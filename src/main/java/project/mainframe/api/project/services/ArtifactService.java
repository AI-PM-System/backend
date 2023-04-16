package project.mainframe.api.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.dto.artifact.ArtifactResponse;
import project.mainframe.api.project.repositories.ArtifactRepository;

/**
 * Artifact service.
 * 
 * Does not extend BaseCrudService because it only needs one simple methods at the moment.
 */
@Service
public class ArtifactService {
    
    /**
     * The artifact repository.
     */
    private ArtifactRepository artifactRepository;

    /**
     * Constructor.
     * 
     * @param artifactRepository The artifact repository.
     */
    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    /**
     * Find all artifacts 
     * 
     * @return List of artifacts.
     */
    public List<ArtifactResponse> findAll() {
        return artifactRepository.findAll().stream()
            .map(ArtifactResponse::new)
            .collect(Collectors.toList());
    }
}

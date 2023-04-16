package project.mainframe.api.project.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.dto.artifact.ArtifactResponse;
import project.mainframe.api.project.services.ArtifactService;

/**
 * Artifact controller.
 * 
 * Does not extend BaseCrudController because it only needs one simple methods at the moment.
 */
@RestController
@RequestMapping("/api/v1/user/artifacts")
public class ArtifactController {
    
    /**
     * The artifact service.
     */
    private ArtifactService artifactService;

    /**
     * Constructor.
     * 
     * @param artifactService The artifact service.
     */
    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    /**
     * Find all artifacts 
     * 
     * @return List of artifacts.
     */
    @GetMapping
    public List<ArtifactResponse> findAll() {
        return artifactService.findAll(); 
    }
}

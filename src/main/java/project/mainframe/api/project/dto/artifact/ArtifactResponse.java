package project.mainframe.api.project.dto.artifact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Artifact;

/**
 * Artifact response.
 */
@AllArgsConstructor
@Getter
@Setter
public class ArtifactResponse {
    
    /**
     * The id of the artifact
     */
    private Long id;

    /**
     * The artifact's project
     */
    private ProjectResponse project;    

    /**
     * Constructor.
     * @param artifact The artifact to map.
     */
    public ArtifactResponse(Artifact artifact) {
        this.id = artifact.getId();
        //this.project = new ProjectResponse(artifact.getProject());
    }

    /**
     * No-args constructor
     */
    public ArtifactResponse() {}
}

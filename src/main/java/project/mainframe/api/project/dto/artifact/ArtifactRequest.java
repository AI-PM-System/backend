package project.mainframe.api.project.dto.artifact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArtifactRequest {
    
    /*
     * The id of the artifact
     */
    private Long id;

    /*
     * The artifact's project id
     */
    private Long projectId;
}

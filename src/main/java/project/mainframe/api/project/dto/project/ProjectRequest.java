package project.mainframe.api.project.dto.project;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectRequest {

    /*
     * The name of the project
     */
    private String name;

    /*
     * The description of the project
     */
    private String description;

    /*
     * The project's event ids
     */
    private List<Long> eventIds;

    /*
     * The project's member ids
     */
    private List<Long> memberIds;

    /*
     * The project's role ids
     */
    private List<Long> roleIds;

    /*
     * The project's artifact ids
     */
    private List<Long> artifactIds;
}

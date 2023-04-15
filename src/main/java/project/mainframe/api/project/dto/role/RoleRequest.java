package project.mainframe.api.project.dto.role;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleRequest {

    /*
     * The id of the role
     */
    private Long id;

    /*
     * The name of the role
     */    
    private String name;
    
    /*
     * The description of the role
     */
    private String description;
    
    /*
     * The role's member ids
     */
    private List<Long> memberIds;

    /*
     * The role's project id
     */
    private Long projectId;
}

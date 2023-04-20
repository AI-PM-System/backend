package project.mainframe.api.task.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.task.entities.Board;

/**
 * Board Response.
 */
@AllArgsConstructor
@Getter
@Setter
public class BoardResponse {
    
    /**
     * The id of the board.
     */
    private Long id;
    
    /**
     * The name of the board.
     */
    private String name;
    
    /**
     * The description of the board.
     */
    private String description;
    
    /**
     * The project id
     */
    private Long projectId;
    
    /**
     * no-args constructor
     */
    public BoardResponse() {}

    /**
     * Constructor
     * 
     * @param board The board
     */
    public BoardResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.projectId = board.getProject().getId();
    }
}

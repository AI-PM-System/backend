package project.mainframe.api.task.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Board request.
 */
@AllArgsConstructor
@Getter
@Setter
public class BoardRequest {
    
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
    public BoardRequest() {}
}
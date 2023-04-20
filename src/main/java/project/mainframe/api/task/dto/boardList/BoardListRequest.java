package project.mainframe.api.task.dto.boardList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Board list request.
 */
@AllArgsConstructor
@Getter
@Setter
public class BoardListRequest {
    
     /**
     * The name of the board list.
     */
    private String name;

    /**
     * The board id
     */
    private Long boardId;

    /**
     * no-args constructor
     */
    public BoardListRequest() {}
}

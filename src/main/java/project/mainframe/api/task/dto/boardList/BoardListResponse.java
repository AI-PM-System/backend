package project.mainframe.api.task.dto.boardList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.task.dto.task.TaskResponse;
import project.mainframe.api.task.entities.BoardList;

/**
 * Board list response.
 */
@AllArgsConstructor
@Getter
@Setter
public class BoardListResponse {
    
    /**
     * The id of the board list.
     */
    private Long id;

    /**
     * The name of the board list.
     */
    private String name;

    /**
     * The board id
     */
    private Long boardId;

    /**
     * The board list's tasks
     */
    private List<TaskResponse> tasks;

    /**
     * no-args constructor
     */
    public BoardListResponse() {}

    /**
     * Constructor
     * 
     * @param boardList The board list
     */
    public BoardListResponse(BoardList boardList) {
        this.id = boardList.getId();
        this.name = boardList.getName();
        this.boardId = boardList.getBoard().getId();
        this.tasks = boardList.getTasks() != null ? boardList.getTasks().stream().map(TaskResponse::new).collect(Collectors.toList()) : new ArrayList<TaskResponse>();
    }
}

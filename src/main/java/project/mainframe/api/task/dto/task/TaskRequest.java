package project.mainframe.api.task.dto.task;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Task request.
 */
@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The board list id
     */
    private Long boardListId;

    /**
     * The member id
     */
    private Long memberId;

    /**
     * The task's due date
     */
    private LocalDateTime dueDate;

    /**
     * no-args constructor
     */
    public TaskRequest() {}
}

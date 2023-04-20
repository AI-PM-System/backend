package project.mainframe.api.task.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.task.dto.task.TaskRequest;
import project.mainframe.api.task.dto.task.TaskResponse;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.entities.Task;
import project.mainframe.api.task.repositories.BoardListRepository;
import project.mainframe.api.task.repositories.TaskRepository;

/**
 * Task service.
 */
@Service
public class TaskService {
    
    /**
     * The task repository.
     */
    private final TaskRepository taskRepository;

    /**
     * The board list repository.
     */
    private final BoardListRepository boardListRepository;

    /**
     * The member repository.
     */
    private final MemberRepository memberRepository;

    /**
     * The user task restriction service.
     */
    private final UserTaskRestrictionService userTaskRestrictionService;

    /**
     * Constructor.
     * 
     * @param taskRepository The task repository.
     * @param boardListRepository The board list repository.
     * @param userTaskRestrictionService The user task restriction service.
     * @param memberRepository The member repository.
     */
    public TaskService(
        TaskRepository taskRepository,
        BoardListRepository boardListRepository,
        UserTaskRestrictionService userTaskRestrictionService,
        MemberRepository memberRepository
    ) {
        this.taskRepository = taskRepository;
        this.boardListRepository = boardListRepository;
        this.userTaskRestrictionService = userTaskRestrictionService;
        this.memberRepository = memberRepository;
    }

    /**
     * Find all tasks.
     * @param boardListId The board list id.
     * @param actor The actor.
     * @return The list of responses.
     */
    public List<TaskResponse> findAllByBoardListId(Long boardListId, User actor) {
        if (userTaskRestrictionService.isNotMember(boardListId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board list's board's project.");
        }

        return taskRepository
            .findAllByBoardListId(boardListId)
            .stream()
            .map(TaskResponse::new)
            .toList();
    }

    /**
     * Find by id.
     * 
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    public TaskResponse findById(Long id, User actor) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
        if (userTaskRestrictionService.isNotMember(task.getBoardList().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board list's board's project.");
        }

        return new TaskResponse(task);
    }

    /**
     * Create.
     * 
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public TaskResponse create(TaskRequest request, User actor) {
        if (userTaskRestrictionService.isNotMember(request.getBoardListId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board list's board's project.");
        }

        BoardList boardList = boardListRepository.findById(request.getBoardListId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board list not found."));
        Member member = memberRepository.findById(request.getMemberId()).orElse(null);

        // TODO: Check if the member is a member of the board's project.
        // TODO: Check if the board list belongs to the board's project.
        
        Task task = new Task();
        task.setBoardList(boardList);
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setMember(member);
        taskRepository.save(task);

        List<Task> boardListTasks = boardList.getTasks();
        boardListTasks.add(task);
        boardList.setTasks(boardListTasks);
        boardListRepository.save(boardList);

        return new TaskResponse(task);
    }

    /**
     * Update.
     * 
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public TaskResponse update(Long id, TaskRequest request, User actor) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
        if (userTaskRestrictionService.isNotMember(task.getBoardList().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board list's board's project.");
        }

        BoardList boardList = boardListRepository.findById(request.getBoardListId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board list not found."));
        Member member = memberRepository.findById(request.getMemberId()).orElse(null);

        // TODO: Check if the member is a member of the board's project.
        // TODO: Check if the board list belongs to the board's project.

        removeTaskFromBoardList(task);

        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setMember(member);
        task.setBoardList(boardList);
        task = taskRepository.save(task);

        List<Task> boardListTasks = boardList.getTasks();
        boardListTasks.add(task);
        boardList.setTasks(boardListTasks);
        boardListRepository.save(boardList);

        return new TaskResponse(task);
    }

    /**
     * Delete.
     * 
     * @param id The id.
     * @param actor The actor.
     */
    public void delete(Long id, User actor) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
        if (userTaskRestrictionService.isNotMember(task.getBoardList().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board list's board's project.");
        }

        taskRepository.delete(task);
    }

    private void removeTaskFromBoardList(Task task) {
        BoardList boardList = task.getBoardList();
        List<Task> boardListTasks = boardList.getTasks();
        boardListTasks.remove(task);
        boardList.setTasks(boardListTasks);
        boardListRepository.save(boardList);
    }
}

package project.mainframe.api.project.generator.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.entities.Task;

/**
 * The GPT parser.
 * 
 * Can be used to parse entities from the GPT response.
 */
public class GeneratorGPTParser {

    /**
     * The GPT response.
     */
    private GPTResponse gptResponse;

    /**
     * The parsed project
     */
    private Project project;
    
    /**
     * The parsed roles 
     */
    private List<Role> roles;

    /**
     * The parsed members
     */
    private List<Member> members;

    /**
     * The parsed events
     */
    private List<Event> events;

    /**
     * The parsed boards
     */
    private List<Board> boards;

    /**
     * The parsed board lists
     */
    private List<BoardList> boardLists;

    /**
     * The parsed tasks
     */
    private List<Task> tasks;

    /**
     * Constructor.
     * 
     * @param gptResponse The GPT response.
     */
    public GeneratorGPTParser(GPTResponse gptResponse) {
        this.gptResponse = gptResponse;
    }
    
    /**
     * Parses the project.
     * 
     * @return The project.
     */
    public Project getProject() {
        if (project != null) {
            return project;
        }

        project = new Project();
        project.setName(gptResponse.getProject().get("name"));
        project.setDescription(gptResponse.getProject().get("description"));
        return project;
    }

    /**
     * Parses the roles.
     * 
     * @return The roles.
     */
    public List<Role> getRoles() {
        if (roles != null) {
            return roles;
        }
        
        roles = new ArrayList<>();
        List<Map<String, String>> gptRoles = gptResponse.getRoles();
        for (Map<String, String> gptRole : gptRoles) {
            Role role = new Role();
            role.setName(gptRole.get("name"));
            role.setDescription(gptRole.get("description"));
            roles.add(role);
        }
        return roles;
    }

    /**
     * Parses the members.
     * 
     * @return The members.
     */
    public List<Member> getMembers() {
        if (members != null) {
            return members;
        }

        members = new ArrayList<>();
        List<Map<String, Object>> gptMembers = gptResponse.getMembers();
        for (int i = 0; i < gptMembers.size(); i++) {
            Member newMember = new Member();
            members.add(newMember);
        }
        return members;
    }

    /**
     * Parses the events.
     * 
     * @return The events.
     */
    public List<Event> getEvents() {
        if (events != null) {
            return events;
        }

        events = new ArrayList<>();
        List<Map<String, Object>> gptEvents = gptResponse.getEvents();
        for (Map<String, Object> gptEvent : gptEvents) {
            Event event = new Event();
            event.setName((String) gptEvent.get("name"));
            //event.setStartDateTime(LocalDateTime.parse((String) gptEvent.get("startDateTime")));
            //event.setEndDateTime(LocalDateTime.parse((String) gptEvent.get("endDateTime")));
            event.setLocation((String) gptEvent.get("location"));
            event.setAgenda((String) gptEvent.get("agenda"));
            events.add(event);
        }
        return events;
    }

    /**
     * Parses the boards.
     * 
     * @return The boards.
     */
    public List<Board> getBoards() {
        if (boards != null) {
            return boards;
        }

        boards = new ArrayList<>();
        List<Map<String, Object>> gptBoards = gptResponse.getBoards();
        for (Map<String, Object> board : gptBoards) {
            Board newBoard = new Board();
            newBoard.setName((String) board.get("name"));
            boards.add(newBoard);
        }
        return boards;
    }

    /**
     * Parses the board lists.
     * 
     * @return The board lists.
     */
    public List<BoardList> getBoardLists() {
        if (boardLists != null) {
            return boardLists;
        }

        boardLists = new ArrayList<>();
        List<Map<String, Object>> gptBoardLists = gptResponse.getBoardLists();
        for (Map<String, Object> boardList : gptBoardLists) {
            BoardList newBoardList = new BoardList();
            newBoardList.setName((String) boardList.get("name"));
            boardLists.add(newBoardList);
        }
        return boardLists;
    }

    /**
     * Parses the tasks.
     * 
     * @return The tasks.
     */
    public List<Task> getTasks() {
        if (tasks != null) {
            return tasks;
        }

        tasks = new ArrayList<>();
        List<Map<String, Object>> gptTasks = gptResponse.getTasks();
        for (Map<String, Object> task : gptTasks) {
            Task newTask = new Task();
            newTask.setName((String) task.get("name"));
            newTask.setDescription((String) task.get("description"));
            tasks.add(newTask);
        }
        return tasks;
    }
}

package project.mainframe.api.chat.services;

import java.util.List;

import org.springframework.stereotype.Service;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.entities.Task;

@Service
public class InfoMessageService {

    /**
     * The chat message repository.
     */
    private MessageRepository messageRepository;

    /**
     * The string builder.
     */
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * Constructor.
     * 
     * @param messageRepository The chat message repository.
     */
    public InfoMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Adds info message to a chat about the project.
     * 
     * @param project The project.
     * @param chat The chat.
     */
    public void addInfoFromProject(Project project, Chat chat) {
        stringBuilder.setLength(0);

        messageRepository.save(new Message(chat, null, null, 
            "<h4>🗄️ Project</h4> <p><strong>Name:</strong> " + project.getName() + ";</p><p><strong>Description:</strong> " + project.getDescription() + ";</p>"));
    }

    /**
     * Adds info message to a chat about the events
     * 
     * @param events The events.
     * @param chat The chat.
     */
    public void addInfoFromEvents(List<Event> events, Chat chat) {
        stringBuilder.setLength(0);

        String eventFormatString = "<p><strong>Name:</strong> %s;<br> <strong>Start:</strong> %s;<br> <strong>End:</strong> %s;<br> <strong>Location:</strong> %s;<br> <strong>Agenda:</strong> %s;</p>";
        stringBuilder.append("<h4>📅 Events</h4>");
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            stringBuilder.append(String.format(eventFormatString, event.getName(), event.getStartDateTime(), 
                event.getEndDateTime(), event.getLocation(), event.getAgenda()));
            stringBuilder.append("<hr>");
        }
        stringBuilder.append("<a href=\"/#/events\" class=\"a\">Go to events</a>");
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }

    /**
     * Adds info message to a chat about the roles.
     * 
     * @param roles The roles.
     * @param chat The chat.
     */
    public void addInfoFromRoles(List<Role> roles, Chat chat) {
        stringBuilder.setLength(0);

        String roleFormatString = "<p><strong>Name:</strong> %s;<br> <strong>Description:</strong> %s;</p>";
        stringBuilder.append("<h4>🏷️ Roles</h4>");
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            stringBuilder.append(String.format(roleFormatString, role.getName(), role.getDescription()));
            stringBuilder.append("<hr>");
        }
        stringBuilder.append("<a href=\"/#/roles\" class=\"a\">Go to roles</a>");
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }

    /**
     * Adds info message to a chat about the members.
     * 
     * @param members The members.
     * @param chat The chat.
     */
    public void addInfoFromMembers(List<Member> members, Chat chat) {
        stringBuilder.setLength(0);

        String memberFormatString = "<p><strong>Name:</strong> %s;<br> <strong>Role:</strong> %s;</p>";
        stringBuilder.append("<h4>👥 Members</h4>");
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            String user = member.getUser() == null ? "Not assigned" : member.getUser().getUsername();
            String role = member.getRoles() == null ? "No role" : member.getRoles().get(0).getName();
            
            stringBuilder.append(String.format(memberFormatString, user, role));
            stringBuilder.append("<hr>");
        }
        stringBuilder.append("<a href=\"/#/members\" class=\"a\">Go to members</a>");
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }

    /**
     * Adds info message to a chat about the boards.
     * 
     * @param boards The boards.
     * @param chat The chat.
     */
    public void addInfoFromBoards(List<Board> boards, Chat chat) {
        stringBuilder.setLength(0);

        String boardFormatString = "<a href=\"/#/board/%d\" class=\"a\">%d. Name: %s;</a>";
        stringBuilder.append("<h4>📔 Boards</h4>");
        for (int i = 0; i < boards.size(); i++) {
            Board board = boards.get(i);
            stringBuilder.append(String.format(boardFormatString, board.getId(), i + 1, board.getName()));
        }
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }

    /**
     * Adds info message to a chat about the board lists.
     * 
     * @param boardLists The board lists.
     * @param chat The chat.
     */
    public void addInfoFromBoardLists(List<BoardList> boardLists, Chat chat) {
        stringBuilder.setLength(0);

        String boardListFormatString = "<a href=\"/#/board/%d\" class=\"a\">%d. Name: %s; Board: %s;</a>";
        stringBuilder.append("<h4>📜 Board Lists</h4>");
        for (int i = 0; i < boardLists.size(); i++) {
            BoardList boardList = boardLists.get(i);
            stringBuilder.append(String.format(boardListFormatString, boardList.getBoard().getId(), i + 1, boardList.getName(), 
                boardList.getBoard().getName()));
        }
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }
    
    /**
     * Adds info message to a chat about the tasks.
     * 
     * @param tasks The tasks.
     * @param chat The chat.
     * @return The message.
     */
    public void addInfoFromTasks(List<Task> tasks, Chat chat) {
        stringBuilder.setLength(0);

        String taskFormatString = "<a href=\"/#/board/%d\" class=\"a\">%d. Name: %s; Description: %s; Board list: %s;</a>";
        stringBuilder.append("<h4>📝 Tasks</h4>");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            stringBuilder.append(String.format(taskFormatString, task.getBoardList().getBoard().getId(), i + 1, task.getName(), 
                task.getDescription(), task.getBoardList().getName()));
        }
        messageRepository.save(new Message(chat, null, null, stringBuilder.toString()));
    }
}

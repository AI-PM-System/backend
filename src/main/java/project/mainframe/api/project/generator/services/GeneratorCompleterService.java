package project.mainframe.api.project.generator.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.entities.Generator;
import project.mainframe.api.project.generator.repositories.GeneratorRepository;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.entities.Task;
import project.mainframe.api.task.repositories.BoardListRepository;
import project.mainframe.api.task.repositories.BoardRepository;
import project.mainframe.api.task.repositories.TaskRepository;

@Service
public class GeneratorCompleterService {
    
    /**
     * The project repository
     */
    private ProjectRepository projectRepository;

    /**
     * The role repository
     */
    private RoleRepository roleRepository;

    /**
     * The member repository
     */
    private MemberRepository memberRepository;

    /**
     * The event repository
     */
    private EventRepository eventRepository;

    /**
     * The chat repository
     */
    private ChatRepository chatRepository;

    /**
     * The message repository
     */
    private MessageRepository messageRepository;

    /**
     * The generator repository
     */
    private GeneratorRepository generatorRepository;

    /**
     * The board repository
     */
    private BoardRepository boardRepository;

    /**
     * The board list repository
     */
    private BoardListRepository boardListRepository;

    /**
     * The task repository
     */
    private TaskRepository taskRepository;

    /**
     * The generator GPT service
     */
    private GeneratorGPTService generatorGPTService;

    /**
     * Constructor
     * 
     * @param projectRepository the project repository
     * @param roleRepository the role repository
     * @param memberRepository the member repository
     * @param eventRepository the event repository
     * @param chatRepository the chat repository
     * @param generatorRepository the generator repository
     * @param boardRepository the board repository
     * @param boardListRepository the board list repository
     * @param taskRepository the task repository
     * @param generatorGPTService the generator GPT service
     * @param messageRepository the message repository
     */
    public GeneratorCompleterService(
        ProjectRepository projectRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository,
        EventRepository eventRepository,
        ChatRepository chatRepository,
        GeneratorRepository generatorRepository,
        BoardRepository boardRepository,
        BoardListRepository boardListRepository,
        TaskRepository taskRepository,
        GeneratorGPTService generatorGPTService,
        MessageRepository messageRepository
    ) {
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.chatRepository = chatRepository;
        this.generatorRepository = generatorRepository;
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.taskRepository = taskRepository;
        this.generatorGPTService = generatorGPTService;
        this.messageRepository = messageRepository;
    }

    /**
     * Complete a generation
     * 
     * @param generator the generator
     * @param GTPResponse the GTP response
     * @return void
     */
    public Chat complete(Generator generator, GPTResponse GTPResponse, User user) {
        // Save project
        Project project = new Project();
        project.setName(GTPResponse.getProject().get("name"));
        project.setDescription(GTPResponse.getProject().get("description"));
        project = projectRepository.save(project);
        
        // Save roles
        List<Role> roles = new ArrayList<>();
        List<Map<String, String>> gptRoles = GTPResponse.getRoles();
        for (Map<String, String> role : gptRoles) {
            Role newRole = new Role();
            newRole.setName(role.get("name"));
            newRole.setDescription(role.get("description"));
            newRole.setProject(project);
            newRole = roleRepository.save(newRole);
            roles.add(newRole);
        } 

        // Save members
        int i = 0;
        List<Member> members = new ArrayList<>();
        List<Map<String, Object>> gptMembers = GTPResponse.getMembers();
        for (Map<String, Object> member : gptMembers) {
            Role role = roles.get((Integer) member.get("roleId"));
            Member newMember = new Member();
            newMember.setRoles(Collections.singletonList(role));
            newMember.setProject(project);
            newMember = memberRepository.save(newMember);
            if (i == 0) {
                newMember.setUser(user);
                newMember.setAdmin(true);
                i++;
            }
            members.add(newMember);
        }

        // Save events
        List<Event> events = new ArrayList<>();
        List<Map<String, Object>> gptEvents = GTPResponse.getEvents();
        for (Map<String, Object> event : gptEvents) {
            Event newEvent = new Event();
            newEvent.setName((String) event.get("name"));
            //newEvent.setStartDateTime(LocalDateTime.parse((String) event.get("startDateTime")));
            //newEvent.setEndDateTime(LocalDateTime.parse((String) event.get("endDateTime")));
            newEvent.setLocation((String) event.get("location"));
            newEvent.setAgenda((String) event.get("agenda"));
            newEvent.setProject(project);
            newEvent = eventRepository.save(newEvent);
            events.add(newEvent);
        }

        // Create main chat
        Chat chat = new Chat();
        chat.setName("Main chat");
        chat.setType(ChatType.MAIN);
        chat.setProject(project);
        chat.setMembers(members); 
        chat = chatRepository.save(chat);        

        // Create boards
        List<Board> boards = new ArrayList<>();
        List<Map<String, Object>> gptBoards = GTPResponse.getBoards();
        for (Map<String, Object> board : gptBoards) {
            Board newBoard = new Board();
            newBoard.setName((String) board.get("name"));
            newBoard.setProject(project);
            newBoard = boardRepository.save(newBoard);
            boards.add(newBoard);
        }

        // Create board lists
        List<BoardList> boardLists = new ArrayList<>();
        List<Map<String, Object>> gptBoardLists = GTPResponse.getBoardLists();
        for (Map<String, Object> boardList : gptBoardLists) {
            BoardList newBoardList = new BoardList();
            Board board = boards.get((Integer) boardList.get("boardId"));
            newBoardList.setName((String) boardList.get("name"));
            newBoardList.setBoard(board);
            newBoardList = boardListRepository.save(newBoardList);
            boardLists.add(newBoardList);
        }

        // Create tasks
        List<Task> tasks = new ArrayList<>();
        List<Map<String, Object>> gptTasks = GTPResponse.getTasks();
        for (Map<String, Object> task : gptTasks) {
            Task newTask = new Task();
            BoardList boardList = boardLists.get((Integer) task.get("boardListId"));
            newTask.setName((String) task.get("name"));
            newTask.setDescription((String) task.get("description"));
            newTask.setBoardList(boardList);
            newTask = taskRepository.save(newTask);
            tasks.add(newTask);

            List<Task> _tasks = boardList.getTasks();
            if (_tasks == null) {
                _tasks = new ArrayList<>();
            }
            _tasks.add(newTask);
            boardList.setTasks(_tasks);
            boardListRepository.save(boardList);
        }

        // Generate fun welcome message.
        CompletionResponse _welcomeResponse = generatorGPTService.getWelcomeMessage(project.getName());
        messageRepository.save(new Message(
            chat, 
            null, 
            null, 
            _welcomeResponse.getChoices().get(0).getText()
        ));

        // Generate info messages.
        messageRepository.save(new Message(chat, null, null, "I added the following items based on your description:"));
        
        // project info message
        messageRepository.save(new Message(chat, null, null, "A project named: " + project.getName() + "; with description " + project.getDescription()));
        
        // event info message
        String eventFormatString = "%d. name: %s, start date: %s, end date: %s, location: %s, agenda: %s";
        String eventMessage = "The following events: <br>";
        int itemNumber = 1;
        for (Event event : events) {
            eventMessage += String.format(eventFormatString, itemNumber, event.getName(), event.getStartDateTime(), event.getEndDateTime(), event.getLocation(), event.getAgenda());
            eventMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, eventMessage));

        // role info message
        String roleFormatString = "%d. name: %s, description: %s";
        String roleMessage = "The following roles: <br>";
        itemNumber = 1;
        for (Role role : roles) {
            roleMessage += String.format(roleFormatString, itemNumber, role.getName(), role.getDescription());
            roleMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, roleMessage));

        // member info message
        String memberFormatString = "%d. user: %s, role: %s";
        String memberMessage = "The following members: <br>";
        itemNumber = 1;
        for (Member member : members) {
            String username = member.getUser() == null ? "Not assigned" : member.getUser().getUsername();
            String roleName = member.getRoles() == null ? "No role" : member.getRoles().get(0).getName();
            memberMessage += String.format(memberFormatString, itemNumber, username, roleName);
            memberMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, memberMessage));

        // board info message
        String boardFormatString = "%d. name: %s";
        String boardMessage = "The following boards: <br>";
        itemNumber = 1;
        for (Board board : boards) {
            boardMessage += String.format(boardFormatString, itemNumber, board.getName());
            boardMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, boardMessage));

        // board list info message
        String boardListFormatString = "%d. name: %s, board: %s";
        String boardListMessage = "The following board lists: <br>";
        itemNumber = 1;
        for (BoardList boardList : boardLists) {
            boardListMessage += String.format(boardListFormatString, itemNumber, boardList.getName(), boardList.getBoard().getName());
            boardListMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, boardListMessage));

        // task info message
        String taskFormatString = "%d. name: %s, description: %s, board list: %s";
        String taskMessage = "The following tasks: <br>";
        itemNumber = 1;
        for (Task task : tasks) {
            taskMessage += String.format(taskFormatString, itemNumber, task.getName(), task.getDescription(), task.getBoardList().getName());
            taskMessage += "<br>";
            itemNumber++;
        }
        messageRepository.save(new Message(chat, null, null, taskMessage));

        /*
        // Update project
        project.setMembers(members);
        project.setEvents(events);
        project.setRoles(roles);
        project.setChats(Collections.singletonList(chat));
        project = projectRepository.save(project); */

        // Complete generator
        generator.setCompleted(true);
        generatorRepository.save(generator);

        return chat;
    }
}

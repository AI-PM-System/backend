package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.chat.services.InfoMessageService;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.entities.Generator;
import project.mainframe.api.project.generator.repositories.GeneratorRepository;
import project.mainframe.api.project.generator.utils.GeneratorGPTParser;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;
import project.mainframe.api.project.repositories.UserRepository;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.entities.Task;
import project.mainframe.api.task.repositories.BoardListRepository;
import project.mainframe.api.task.repositories.BoardRepository;
import project.mainframe.api.task.repositories.TaskRepository;

/**
 * The generator creator service.
 * 
 * This service is used to build a project from a GPT response.
 * It will parse and create all the entities and save them in the database.
 */
@Service
public class GeneratorCreatorService {
    
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
     * The info message service
     */
    private InfoMessageService infoMessageService;

    /**
     * The user repository
     */
    private UserRepository userRepository;

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
     * @param infoMessageService the info message service
     * @param userRepository the user repository
     */
    public GeneratorCreatorService(
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
        MessageRepository messageRepository,
        InfoMessageService infoMessageService,
        UserRepository userRepository
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
        this.infoMessageService = infoMessageService;
        this.userRepository = userRepository;
    }

    /**
     * Complete a generation
     * 
     * @param generator the generator
     * @param GTPResponse the GTP response
     * @return void
     */
    public Chat complete(Generator generator, GPTResponse gptResponse, String singularizedMessage, User user) {

        GeneratorGPTParser parser = new GeneratorGPTParser(gptResponse);

        // Parse project, save to database and return
        Project project = projectRepository.save(parser.getProject());

        // Parse roles, connect to project and save to database
        List<Role> roles = saveRoles(parser, project);

        // Parse members, connect to project, connect to roles and save to database
        List<Member> members = saveMembers(parser, gptResponse, project, roles, user);

        // Parse events, connect to project and save to database
        List<Event> events = saveEvents(parser, project);

        // Create main chat, connect to project and save to database
        Chat chat = createMainChat(project);

        // Parse boards, connect to project and save to database
        List<Board> boards = saveBoards(parser, project);

        // Parse board lists, connect to boards and save to database
        List<BoardList> boardLists = saveBoardLists(parser, gptResponse, boards);
       
        // Parse tasks, connect to board lists and save to database
        List<Task> tasks = saveTasks(parser, gptResponse, boardLists);

        // Save info messages about the created entities for the main chat
        saveInfoMessages(project, events, roles, members, boards, boardLists, 
            tasks, chat, singularizedMessage, generator);

        // Complete generator
        generator.setCompleted(true);
        generatorRepository.save(generator);

        // Subtract one credit from user
        user.setCredits(user.getCredits() - 1);
        userRepository.save(user);

        return chat;
    }

    /**
     * Create roles.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param project The project.
     * @return The created roles.
     */
    private List<Role> saveRoles(GeneratorGPTParser parser, Project project) {
        // Parse roles, connect to project and save to database
        List<Role> roles = parser.getRoles();
        roles.forEach(role -> role.setProject(project));
        return roleRepository.saveAll(roles);
    }

    /**
     * Create members.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param GTPResponse The GPT response.
     * @param project The project.
     * @param roles The roles.
     * @param user The user.
     * @return The created members.
     */
    private List<Member> saveMembers(GeneratorGPTParser parser, GPTResponse gptResponse, Project project, List<Role> roles, User user) {
        List<Member> members = parser.getMembers();
        for (Member member : members) {
            int memberIndex = members.indexOf(member);
            int roleId = (Integer) gptResponse.getMembers().get(memberIndex).get("roleId");
            Role role = roles.get(roleId);
            member.setRoles(Collections.singletonList(role));
            member.setProject(project);

            List<Member> roleMembers = role.getMembers();
            if (roleMembers == null) {
                roleMembers = new ArrayList<>();
            }
            roleMembers.add(member);
            role.setMembers(roleMembers);
        }
        // Set user to first member, and set member as admin
        members.get(0).setUser(user);
        members.get(0).setAdmin(true);
        members = memberRepository.saveAll(members);
        roles = roleRepository.saveAll(roles);

        return members;
    }

    /**
     * Create events.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param project The project.
     * @return The created events.
     */
    private List<Event> saveEvents(GeneratorGPTParser parser, Project project) {
        List<Event> events = parser.getEvents();
        events.forEach(event -> event.setProject(project));
        return eventRepository.saveAll(events);
    }

    /**
     * Create main chat.
     * 
     * @param project The project.
     * @return The created chat.
     */
    private Chat createMainChat(Project project) {
        Chat chat = new Chat();
        chat.setName("Main chat");
        chat.setType(ChatType.MAIN);
        chat.setProject(project);
        chat = chatRepository.save(chat);
        return chat;
    }

    /**
     * Create boards.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param project The project.
     * @return The created boards.
     */
    private List<Board> saveBoards(GeneratorGPTParser parser, Project project) {
        List<Board> boards = parser.getBoards();
        boards.forEach(board -> board.setProject(project));
        return boardRepository.saveAll(boards);
    }

    /**
     * Create board lists.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param GTPResponse The GPT response.
     * @param boards The boards.
     * @return The created board lists.
     */
    private List<BoardList> saveBoardLists(GeneratorGPTParser parser, GPTResponse gptResponse, List<Board> boards) {
        List<BoardList> boardLists = parser.getBoardLists();
        for (BoardList boardList : boardLists) {
            int boardListIndex = boardLists.indexOf(boardList);
            Board board = boards.get((Integer) gptResponse.getBoardLists().get(boardListIndex).get("boardId"));
            boardList.setBoard(board);
        }
        return boardListRepository.saveAll(boardLists);
    }

    /**
     * Create tasks.
     * 
     * @param GeneratorGPTParser The GPT parser.
     * @param GTPResponse The GPT response.
     * @param boardLists The board lists.
     * @return The created tasks.
     */
    private List<Task> saveTasks(GeneratorGPTParser parser, GPTResponse gptResponse, List<BoardList> boardLists) {
        List<Task> tasks = parser.getTasks();
        for (Task task : tasks) {
            int taskIndex = tasks.indexOf(task);
            BoardList boardList = boardLists.get((Integer) gptResponse.getTasks().get(taskIndex).get("boardListId"));
            task.setBoardList(boardList);

            List<Task> currentTasks = boardList.getTasks();
            if (currentTasks == null) {
                currentTasks = new ArrayList<>();
            }
            currentTasks.add(task);
            boardList.setTasks(currentTasks);
        }
        tasks = taskRepository.saveAll(tasks);
        boardLists = boardListRepository.saveAll(boardLists);
        return tasks;
    }

    /**
     * Save info messages.
     * 
     * @param project The project.
     * @param events The events.
     * @param roles The roles.
     * @param members The members.
     * @param boards The boards.
     * @param boardLists The board lists.
     * @param tasks The tasks.
     * @param chat The chat.
     * @param singularizedMessage The singularized message.
     * @param generator The generator.
     * @return The created tasks.
     */
    private void saveInfoMessages(Project project, List<Event> events, List<Role> roles, List<Member> members, List<Board> boards, 
        List<BoardList> boardLists, List<Task> tasks, Chat chat, String singularizedMessage, Generator generator) {
        // Generate fun welcome message.
        CompletionResponse _welcomeResponse = generatorGPTService.getWelcomeMessage(project.getName(), 
            generator.getActor().getName(), generator.getTone().getTone());
        messageRepository.save(new Message(
            chat, 
            null, 
            null, 
            _welcomeResponse.getChoices().get(0).getText()
        ));

        // Generate info messages.
        String generatorDescription = "<p>I added the following items based on your description:</p> " + singularizedMessage;
        messageRepository.save(new Message(chat, null, null, generatorDescription));
        infoMessageService.addInfoFromProject(project, chat);
        infoMessageService.addInfoFromEvents(events, chat);
        infoMessageService.addInfoFromRoles(roles, chat);
        infoMessageService.addInfoFromMembers(members, chat);
        infoMessageService.addInfoFromBoards(boards, chat);
        infoMessageService.addInfoFromBoardLists(boardLists, chat);
        infoMessageService.addInfoFromTasks(tasks, chat);
    }
}

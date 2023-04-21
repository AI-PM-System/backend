package project.mainframe.api.project.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.entities.User;
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
 * Sample data.
 */
@Configuration
public class SampleData implements ApplicationRunner {

    /*
     * The user repository.
     */
    private UserRepository userRepository;

    /*
     * The project repository.
     */
    private ProjectRepository projectRepository;

    /*
     * The role repository.
     */
    private RoleRepository roleRepository;

    /*
     * The member repository.
     */
    private MemberRepository memberRepository;

    /*
     * The event repository.
     */
    private EventRepository eventRepository;

    /*
     * The chat repository.
     */
    private ChatRepository chatRepository;

    /*
     * The password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /*
     * The board repository.
     */
    private BoardRepository boardRepository;

    /*
     * The board list repository.
     */
    private BoardListRepository boardListRepository;

    /*
     * The task repository.
     */
    private TaskRepository taskRepository;

    /**
     * Constructor.
     * 
     * @param userRepository      The user repository.
     * @param projectRepository   The project repository.
     * @param roleRepository      The role repository.
     * @param memberRepository    The member repository.
     * @param eventRepository     The event repository.
     * @param chatRepository      The chat repository.
     * @param passwordEncoder     The password encoder.
     * @param boardRepository     The board repository.
     * @param boardListRepository The board list repository.
     * @param taskRepository      The task repository.
     */
    public SampleData(
        UserRepository userRepository,
        ProjectRepository projectRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository,
        EventRepository eventRepository,
        ChatRepository chatRepository,
        PasswordEncoder passwordEncoder,
        BoardRepository boardRepository,
        BoardListRepository boardListRepository,
        TaskRepository taskRepository
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.chatRepository = chatRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.taskRepository = taskRepository;
    }
    
    /**
     * Runs the application.
     * @param args Application arguments.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
    
        // Create project
        Project project = new Project();
        project.setName("Test Project 1");
        project.setDescription("Test Project 1 description");
        if (projectRepository.findByName(project.getName()) != null)
            return;
        project = projectRepository.save(project);

        // Create roles
        Role role1 = new Role();
        role1.setName("Developer Team");
        role1.setDescription("The Development Team is responsible for delivering a potentially releasable increment of the product at the end of each sprint. They self-organize, plan and execute their work, and collaborate to deliver high-quality software. The Development Team is cross-functional, with all the skills necessary to deliver the product increment, and is responsible for continuously improving their skills, practices, and the product they are building.");
        role1.setProject(project);
        role1 = roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName("SCRUM Master");
        role2.setDescription("The Scrum Team is a self-organizing, cross-functional team responsible for delivering a potentially releasable increment of the product at the end of each sprint. It consists of the Scrum Master, Product Owner, and Development Team, who work together collaboratively to deliver high-quality software.");
        role2.setProject(project);
        role2 = roleRepository.save(role2);

        Role role3 = new Role();
        role3.setName("Product Owner");
        role3.setDescription("The Product Owner is responsible for maximizing the value of the product resulting from work of the Development Team. The Product Owner is the sole person responsible for managing the Product Backlog. Product Owner may do this by him/herself or by using the Product Owner Team.");
        role3.setProject(project);
        role3 = roleRepository.save(role3);

        // Create users
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setAuthorities(Collections.singletonList("ROLE_ADMIN"));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("user@example.com");
        user.setPhoneNumber("1234567890");
        user.setCredits(10);
        user = userRepository.save(user);

        // Create members
        Member member = new Member();
        member.setProject(project);
        member.setRoles(Collections.singletonList(role1));
        member.setUser(user);
        member.setAdmin(true);
        member = memberRepository.save(member);

        role1.setMembers(Collections.singletonList(member));
        role1 = roleRepository.save(role1);

        Member member2 = new Member();
        member2.setProject(project);
        member2.setRoles(Collections.singletonList(role2));
        member2 = memberRepository.save(member2);

        role2.setMembers(Collections.singletonList(member2));
        role2 = roleRepository.save(role2);

        // Create events
        Event event = new Event();
        event.setName("Sprint 1");
        event.setAgenda("Agenda for Sprint 1");
        event.setLocation("Location for Sprint 1");
        event.setStartDateTime(LocalDateTime.now());
        event.setEndDateTime(LocalDateTime.now().plusHours(1));
        event.setProject(project);
        event = eventRepository.save(event);

     
        Event event2 = new Event();
        event2.setName("Sprint 2");
        event2.setAgenda("Agenda for Sprint 2");
        event2.setLocation("Location for Sprint 2");
        event2.setStartDateTime(LocalDateTime.now());
        event2.setEndDateTime(LocalDateTime.now().plusHours(1));
        event2.setProject(project);
        event2 = eventRepository.save(event2);

        // Convert role1 and role2 to a list
        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);

        // Convert member and member2 to a list
        List<Member> members = new ArrayList<>();
        members.add(member);
        members.add(member2);

        // Create main chat
        Chat chat = new Chat();
        chat.setName("Main chat");
        chat.setType(ChatType.MAIN);
        chat.setProject(project);
        chat.setMessages(Collections.emptyList());
        chat.setMembers(members); 
        chat = chatRepository.save(chat);

        // Create board
        Board board = new Board();
        board.setName("Main board");
        board.setProject(project);
        board = boardRepository.save(board);

        // Create board list
        BoardList boardList1 = new BoardList();
        boardList1.setName("To-do");
        boardList1.setBoard(board);
        boardList1 = boardListRepository.save(boardList1);

        BoardList boardList2 = new BoardList();
        boardList2.setName("Progress");
        boardList2.setBoard(board);
        boardList2 = boardListRepository.save(boardList2);

        BoardList boardList3 = new BoardList();
        boardList3.setName("Done");
        boardList3.setBoard(board);
        boardList3 = boardListRepository.save(boardList3);

        // Create tasks
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setDescription("Task 1 description");
        task1.setBoardList(boardList1);
        task1 = taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Task 2 description");
        task2.setBoardList(boardList1);
        task2 = taskRepository.save(task2);

        boardList1.setTasks(Arrays.asList(task1, task2));
        boardList1 = boardListRepository.save(boardList1);

        // Update project
        project.setMembers(members);
        project.setEvents(Collections.singletonList(event));
        project.setRoles(roles);
        project.setChats(Collections.singletonList(chat));
        project = projectRepository.save(project);
        
    }
}

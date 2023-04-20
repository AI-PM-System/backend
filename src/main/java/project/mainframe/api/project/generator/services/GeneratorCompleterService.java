package project.mainframe.api.project.generator.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
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
     * The generator repository
     */
    private GeneratorRepository generatorRepository;

    /**
     * Constructor
     * 
     * @param projectRepository the project repository
     * @param roleRepository the role repository
     * @param memberRepository the member repository
     * @param eventRepository the event repository
     * @param chatRepository the chat repository
     * @param generatorRepository the generator repository
     */
    public GeneratorCompleterService(
        ProjectRepository projectRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository,
        EventRepository eventRepository,
        ChatRepository chatRepository,
        GeneratorRepository generatorRepository
    ) {
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.chatRepository = chatRepository;
        this.generatorRepository = generatorRepository;
    }

    /**
     * Complete a generation
     * 
     * @param generator the generator
     * @param GTPResponse the GTP response
     * @return void
     */
    public Long complete(Generator generator, GPTResponse GTPResponse, User user) {
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
        chat.setName("Main Chat");
        chat.setType(ChatType.MAIN);
        chat.setProject(project);
        chat.setMembers(members); 
        chat = chatRepository.save(chat);

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

        return chat.getId();
    }
}

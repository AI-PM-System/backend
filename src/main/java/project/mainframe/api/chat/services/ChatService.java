package project.mainframe.api.chat.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.chat.dto.chat.ChatRequest;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.services.UserProjectRestrictionService;

/**
 * Chat service.
 */
@Service
public class ChatService  {

    /**
     * The chat repository.
     */
    private ChatRepository chatRepository;

    /**
     * The project repository.
     */
    private ProjectRepository projectRepository;

    /**
     * The member repository.
     */
    private MemberRepository memberRepository;

    /**
     * User project restriction service
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param chatRepository The repository to use for CRUD operations.
     * @param projectRepository The project repository.
     * @param memberRepository The member repository.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public ChatService(
        ChatRepository chatRepository,
        ProjectRepository projectRepository,
        MemberRepository memberRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.chatRepository = chatRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }

    /**
     * Find all by project id
     * 
     * @param projectId The project id.
     * @param user the user.
     * @return list of chats.
     */
    public List<ChatResponse> findAllByProjectId(Long projectId, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return chatRepository
            .findAllByProjectId(projectId)
            .stream()
            .map(ChatResponse::new)
            .toList();
    }

    /**
     * Find by id.
     * 
     * @param id The id.
     * @param user The user.
     * @return The chat.
     */
    public ChatResponse findById(Long id, User actor) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found."));

        if (userProjectRestrictionService.isNotMember(chat.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new ChatResponse(chat);
    }

    /**
     * Create a new chat.
     * 
     * @param chatRequest The chat request.
     * @param user The user.
     * @return The chat.
     */
    public ChatResponse create(ChatRequest chatRequest, User actor) {
        if (userProjectRestrictionService.isNotMember(chatRequest.getProjectId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Chat chat = new Chat();
        chat.setName(chatRequest.getName());
        chat.setProject(projectRepository.findById(chatRequest.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found.")));
        chat.setType(ChatType.valueOf(chatRequest.getType()));

        return new ChatResponse(chatRepository.save(chat));
    }

    /**
     * Update a chat.
     * 
     * @param id The id.
     * @param chatRequest The chat request.
     * @param user The user.
     * @return The chat.
     */
    public ChatResponse update(Long id, ChatRequest chatRequest, User actor) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found."));

        if (userProjectRestrictionService.isNotMember(chat.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        chat.setName(chatRequest.getName());
        chat.setType(ChatType.valueOf(chatRequest.getType()));

        return new ChatResponse(chatRepository.save(chat));
    }

    /**
     * Find by type.
     * 
     * @param projectId The project id.
     * @param type The type.
     * @param user The user.
     * @return The chat.
     */
    public ChatResponse findByTypeAndProjectId(Long projectId, String type, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new ChatResponse(chatRepository.findByTypeAndProjectId(ChatType.valueOf(type), projectId));
    }
}

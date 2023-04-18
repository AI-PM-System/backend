package project.mainframe.api.chat.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.chat.ChatRequest;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * Chat service.
 */
@Service
public class ChatService extends BaseCrudService<ChatRequest, ChatResponse, Chat, Long>  {

    /**
     * The project repository.
     */
    private ProjectRepository projectRepository;

    /**
     * The member repository.
     */
    private MemberRepository memberRepository;

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     * @param projectRepository The project repository.
     * @param memberRepository The member repository.
     */
    public ChatService(
        JpaRepository<Chat, Long> jpaRepository,
        ProjectRepository projectRepository,
        MemberRepository memberRepository
    ) {
        super(jpaRepository);
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return EventResponse The response.
     */
    @Override
    protected ChatResponse mapToResponse(Chat entity) {
        return new ChatResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Event The entity.
     */
    @Override
    protected Chat mapToEntity(ChatRequest request) {
        Chat chat = new Chat();
        chat.setName(request.getName());
        chat.setProject(getAssociatedEntity(projectRepository, request.getProjectId()));
        chat.setMembers(getAssociatedEntities(memberRepository, request.getMemberIds()));

        return chat;
    }

    /**
     * Create a new chat by type and name.
     * 
     * @param type The type of chat.
     * @param name The name of the chat.
     * @return ChatResponse The response.
     */
    public ChatResponse create(ChatType type, String name) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setType(type);

        return mapToResponse(jpaRepository.save(chat));
    }
}

package project.mainframe.api.chat.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.project.repositories.MemberRepository;

/**
 * Message service.
 */
@Service
public class MessageService extends BaseCrudService<MessageRequest, MessageResponse, Message, Long>  {

    /**
     * The chat repository.
     */
    private ChatRepository chatRepository;

    /**
     * The member repository.
     */
    private MemberRepository memberRepository;

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     * @param chatRepository The chat repository.
     * @param memberRepository The member repository.
     */
    public MessageService(
        JpaRepository<Message, Long> jpaRepository,
        ChatRepository chatRepository,
        MemberRepository memberRepository
    ) {
        super(jpaRepository);
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return EventResponse The response.
     */
    @Override
    protected MessageResponse mapToResponse(Message entity) {
        return new MessageResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Event The entity.
     */
    @Override
    protected Message mapToEntity(MessageRequest request) {
        Message message = new Message();
        message.setChat(chatRepository.findById(request.getChatId()).get());
        message.setMember(memberRepository.findById(request.getMemberId()).get());
        message.setContent(request.getContent());

        return message;
    }
}
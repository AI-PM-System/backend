package project.mainframe.api.chat.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.UserRepository;

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
     * The user repository.
     */
    private UserRepository userRepository;

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     * @param chatRepository The chat repository.
     * @param memberRepository The member repository.
     * @param userRepository The user repository.
     */
    public MessageService(
        JpaRepository<Message, Long> jpaRepository,
        ChatRepository chatRepository,
        MemberRepository memberRepository,
        UserRepository userRepository
    ) {
        super(jpaRepository);
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return MessageResponse The response.
     */
    @Override
    protected MessageResponse mapToResponse(Message entity) {
        return new MessageResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Message The entity.
     */
    @Override
    protected Message mapToEntity(MessageRequest request) {
        Message message = new Message();
        message.setChat(chatRepository.findById(request.getChatId()).get());
        message.setMember(memberRepository.findById(request.getMemberId()).get());
        message.setUser(userRepository.findById(request.getUsername()).get());
        message.setContent(request.getContent());

        return message;
    }

    /**
     * Finds a list of messages by chat id.
     * 
     * @param chatId The chat id of the messages.
     * @return List of message responses.
     */
    public List<MessageResponse> findAllByChatId(Long chatId) {
        MessageRepository messageRepository = (MessageRepository) jpaRepository;
        return messageRepository.findAllByChatId(chatId).stream().map(MessageResponse::new).toList();
    }
}
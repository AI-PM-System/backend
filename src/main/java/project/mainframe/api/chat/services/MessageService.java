package project.mainframe.api.chat.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.chat.repositories.MessageRepository;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.UserRepository;

/**
 * Message service.
 */
@Service
public class MessageService  {

    /**
     * The message repository.
     */
    private MessageRepository messageRepository;

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
     * The user message restriction service
     */
    private UserMessageRestrictionService userMessageRestrictionService;

    /**
     * Constructor.
     * 
     * @param messageRepository The repository to use for CRUD operations.
     * @param chatRepository The chat repository.
     * @param memberRepository The member repository.
     * @param userRepository The user repository.
     * @param userMessageRestrictionService The user message restriction service.
     */
    public MessageService(
        MessageRepository messageRepository,
        ChatRepository chatRepository,
        MemberRepository memberRepository,
        UserRepository userRepository,
        UserMessageRestrictionService userMessageRestrictionService
    ) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.userMessageRestrictionService = userMessageRestrictionService;
    }

    /**
     * Finds a list of messages by chat id.
     * 
     * @param chatId The chat id of the messages.
     * @return List of message responses.
     */
    public List<MessageResponse> findAllByChatId(Long chatId, User actor) {
        if (userMessageRestrictionService.isNotMember(chatId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the chat's project.");
        }

        return messageRepository
            .findAllByChatId(chatId)
            .stream()
            .map(MessageResponse::new)
            .toList();
    }

    /**
     * Find message by id.
     * 
     * @param id The id of the message.
     * @param user The user.
     * @return The message response.
     */
    public MessageResponse findById(Long id, User actor) {
        Message message = messageRepository.findById(id).get();

        if (userMessageRestrictionService.isNotMember(message.getChat().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the chat's project.");
        }

        return new MessageResponse(message);
    }

    /**
     * Creates a message.
     * 
     * @param request The message request.
     * @param user The user.
     * @return The message response.
     */
    public MessageResponse create(MessageRequest request, User actor) {
        if (userMessageRestrictionService.isNotMember(request.getChatId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the chat's project.");
        }

        Message message = mapToEntity(request);
        messageRepository.save(message);

        return new MessageResponse(message);
    }

    /**
     * Updates a message.
     * 
     * @param id The id of the message.
     * @param request The message request.
     * @param user The user.
     * @return The message response.
     */
    public MessageResponse update(Long id, MessageRequest request, User actor) {
        Message message = messageRepository.findById(id).get();

        if (userMessageRestrictionService.isNotMember(message.getChat().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the chat's project.");
        }

        message.setContent(request.getContent());
        messageRepository.save(message);

        return new MessageResponse(message);
    }

    /**
     * Deletes a message.
     * 
     * @param id The id of the message.
     * @param user The user.
     */
    public void delete(Long id, User actor) {
        Message message = messageRepository.findById(id).get();

        if (userMessageRestrictionService.isNotMember(message.getChat().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the chat's project.");
        }

        messageRepository.deleteById(id);
    }

    /**
     * Maps a message request to a message entity.
     * 
     * @param request The message request.
     * @return The message entity.
     */
    private Message mapToEntity(MessageRequest request) {
        Message message = new Message();
        message.setChat(chatRepository.findById(request.getChatId()).get());
        message.setMember(memberRepository.findById(request.getMemberId()).get());
        message.setUser(userRepository.findById(request.getUsername()).get());
        message.setContent(request.getContent());

        return message;
    }
}
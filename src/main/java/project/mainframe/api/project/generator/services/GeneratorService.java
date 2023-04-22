package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.generator.*;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.dto.message.*;
import project.mainframe.api.project.generator.entities.Generator;
import project.mainframe.api.project.generator.entities.GeneratorActor;
import project.mainframe.api.project.generator.entities.GeneratorMessage;
import project.mainframe.api.project.generator.entities.GeneratorTone;
import project.mainframe.api.project.generator.repositories.GeneratorActorRepository;
import project.mainframe.api.project.generator.repositories.GeneratorMessageRepository;
import project.mainframe.api.project.generator.repositories.GeneratorRepository;
import project.mainframe.api.project.generator.repositories.GeneratorToneRepository;

/**
 * Generator service.
 */
@Service
public class GeneratorService {

    /**
     * Generator repository.
     */
    private GeneratorRepository generatorRepository;

    /**
     * Message repository.
     */
    private GeneratorMessageRepository generatorMessageRepository;

    /**
     * The generator gpt service
     */
    private GeneratorGPTService generatorGPTService;

    /**
     * The generator completer service.
     */
    private GeneratorCreatorService generatorCreatorService;

    /**
     * The generator actor repository.
     */
    private GeneratorActorRepository generatorActorRepository;

    /**
     * The generator tone repository.
     */
    private GeneratorToneRepository generatorToneRepository;

    /**
     * Constructor.
     *
     * @param generatorRepository The generator repository.
     * @param messageRepository The message repository.
     * @param generatorCreatorService The generator creator service.
     * @param generatorGPTService The generator gpt service.
     * @param generatorActorRepository The generator actor repository.
     * @param generatorToneRepository The generator tone repository.
     */
    public GeneratorService(
        GeneratorRepository generatorRepository,
        GeneratorMessageRepository generatorMessageRepository,
        GeneratorGPTService generatorGPTService,
        GeneratorCreatorService generatorCreatorService,
        GeneratorActorRepository generatorActorRepository,
        GeneratorToneRepository generatorToneRepository
    ) {
        this.generatorRepository = generatorRepository;
        this.generatorMessageRepository = generatorMessageRepository;
        this.generatorGPTService = generatorGPTService;
        this.generatorCreatorService = generatorCreatorService;
        this.generatorActorRepository = generatorActorRepository;
        this.generatorToneRepository = generatorToneRepository;
    }

    /**
     * Generate a new project.
     *
     * @param request The generator request.
     * @return Generator response.
     */
    public GeneratorResponse generate(GeneratorRequest request, User user) {
        if (user.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no credits.");
        }

        GeneratorActor actor = generatorActorRepository.findById(request.getActorId()).get();
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor not found.");
        }

        GeneratorTone tone = generatorToneRepository.findById(request.getToneId()).get();
        if (tone == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tone not found.");
        }

        Generator generator = generatorRepository.save(new Generator(user, actor, tone));

        List<GeneratorMessage> generatorMessages = new ArrayList<>();
        generatorMessages.add(saveUserMessage(request.getContent(), user, generator));
        generatorMessages.add(saveHelpMessage(request.getContent(), user, generator));
        
        updateGeneratorMessages(generator, generatorMessages);

        return new GeneratorResponse(
            generator.getId(), 
            Collections.singletonList(new MessageResponse(generatorMessages.get(1), null)),
            generator.isCompleted(), 
            0L);
    }

    /**
     * Proceed with a chat generation.
     * 
     * @param request The generator request.
     * @return Generator response.
     */
    public GeneratorResponse proceed(MessageRequest request, User user) {
        if (user.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no credits.");
        }

        Generator generator = generatorRepository.findById(request.getGeneratorId()).get();
        if (generator.isCompleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Generator is already completed.");
        }

        if (generator.getUser().getUsername() != user.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the generator.");
        }
         
        List<GeneratorMessage> generatorMessages = new ArrayList<>();
        generatorMessages.add(saveUserMessage(request.getContent(), user, generator));
        updateGeneratorMessages(generator, generatorMessages);

        String singularizedMessage = toSingularMessage(generator.getMessages());
        generatorMessages.add(saveHelpMessage(singularizedMessage, user, generator));

        return new GeneratorResponse(
            generator.getId(), 
            Collections.singletonList(new MessageResponse(generatorMessages.get(1), null)),
            generator.isCompleted(), 
            0L);
    }

    /**
     * Complete a generator.
     * 
     * @param id The generator id.
     * @param user The user.
     * @return Generator response.
     */
    public GeneratorResponse complete(Long id, User user) {
        if (user.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no credits.");
        }

        Generator generator = generatorRepository.findById(id).get();
        if (generator.isCompleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Generator is already completed.");
        }

        if (generator.getUser().getUsername() != user.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the generator.");
        }

        // Get GPT response based on the generator's messages.
        String singularizedMessage = toSingularMessage(generator.getMessages());
        GPTResponse gptResponse = generatorGPTService.getProjectJSON(singularizedMessage, 
            generator.getActor().getName(), generator.getTone().getTone());
        // Complete the generator.
        Chat mainChat = generatorCreatorService.complete(generator, gptResponse, singularizedMessage, user);

        GeneratorMessage completionMessage = generatorMessageRepository.save(new GeneratorMessage(
            "Project completed!",
            generator,
            null
        ));

        updateGeneratorMessages(generator, Collections.singletonList(completionMessage));

        return new GeneratorResponse(
            generator.getId(), 
            Collections.singletonList(new MessageResponse(completionMessage, null)),
            generator.isCompleted(), 
            mainChat.getId());
    }

    /**
     * Get any incomplete generator.
     * 
     * @param user The user.
     * @return Generator response.
     */
    public GeneratorResponse getIncompleteGenerator(User user) {
        Generator generator = generatorRepository.findFirstByUserAndCompletedFalse(user);
        if (generator == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No incomplete generator found!");
        }

        List<MessageResponse> messages = new ArrayList<>();
        for (GeneratorMessage message : generator.getMessages()) {
            messages.add(new MessageResponse(message, null));
        }

        return new GeneratorResponse(
            generator.getId(), 
            messages, 
            generator.isCompleted(), 
            0L);
    }

    /**
     * Cancel a generator.
     * 
     * @param authorization The authorized user.
     * @param generatorId The generator id.
     * @return Generator response.
     */
    public GeneratorResponse cancel(Long generatorId, User user) {
        if (user.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no credits.");
        }

        Generator generator = generatorRepository.findById(generatorId).get();
        if (generator == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Generator not found!");
        }

        if (generator.getUser().getUsername() != user.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the generator.");
        }

        generator.setCompleted(true);
        generatorRepository.save(generator);

        GeneratorMessage completionMessage = generatorMessageRepository.save(new GeneratorMessage(
            "Project cancelled!",
            generator,
            null
        ));

        updateGeneratorMessages(generator, Collections.singletonList(completionMessage));

        return new GeneratorResponse(
            generator.getId(), 
            Collections.singletonList(new MessageResponse(completionMessage, null)),
            generator.isCompleted(), 
            0L);
    }

    /**
     * Save user message
     * 
     * @param request The message request.
     * @param user The user.
     * @param generator The generator.
     * @return Generator message.
     */
    private GeneratorMessage saveUserMessage(String content, User user, Generator generator) {
        return generatorMessageRepository.save(new GeneratorMessage(
            content,
            generator,
            user
        ));
    }

    /**
     * Save Help message
     * 
     * @param request The message request.
     * @param user The user.
     * @param generator The generator.
     * @return Generator message.
     */
    private GeneratorMessage saveHelpMessage(String content, User user, Generator generator) {
        CompletionResponse completionResponse = generatorGPTService.getHelpMessage(content, 
            generator.getActor().getName(), generator.getTone().getTone());
        return generatorMessageRepository.save(new GeneratorMessage(
            completionResponse.getChoices().get(0).getText(),
            generator,
            null
        ));
    }

    /**
     * Update generator messages
     * 
     * @param generator The generator.
     * @param message The message.
     * @param helpMessage The help message.
     */
    private void updateGeneratorMessages(Generator generator, List<GeneratorMessage> messages) {
        List<GeneratorMessage> generatorMessages = generator.getMessages();
        if (generatorMessages == null) {
            generatorMessages = new ArrayList<>();
        }

        generatorMessages.addAll(messages);
        generator.setMessages(generatorMessages);
        generatorRepository.save(generator);
    }

    /**
     * Transform the generator messages into a complete string
     * 
     * @param messages The generator messages.
     * @return The complete string.
     */
    private static String toSingularMessage(List<GeneratorMessage> messages) {
        StringBuilder builder = new StringBuilder();

        for (GeneratorMessage message : messages) {
            // Skip messages with no user
            if (message.getUser() == null) {
                continue;
            }

            builder.append(message.getContent());
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}

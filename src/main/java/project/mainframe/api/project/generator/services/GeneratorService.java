package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.openAI.dto.completions.CompletionRequest;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.openAI.exceptions.OpenAIException;
import project.mainframe.api.openAI.services.OpenAIService;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.generator.*;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.dto.message.*;
import project.mainframe.api.project.generator.entities.Generator;
import project.mainframe.api.project.generator.entities.GeneratorMessage;
import project.mainframe.api.project.generator.repositories.GeneratorMessageRepository;
import project.mainframe.api.project.generator.repositories.GeneratorRepository;
import project.mainframe.api.project.generator.utils.GeneratorSanitizer;

/**
 * Generator service.
 */
@Service
public class GeneratorService {

    /**
     * Complete command.
     */
    private static final String COMPLETE_COMMAND = "#complete";

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
    private GeneratorCompleterService generatorCompleterService;

    /**
     * Constructor.
     *
     * @param generatorRepository The generator repository.
     * @param messageRepository The message repository.
     * @param openAIService The OpenAI service.
     * @param generatorCompleterService The generator completer service.
     */
    public GeneratorService(
        GeneratorRepository generatorRepository,
        GeneratorMessageRepository generatorMessageRepository,
        GeneratorGPTService generatorGPTService,
        GeneratorCompleterService generatorCompleterService
    ) {
        this.generatorRepository = generatorRepository;
        this.generatorMessageRepository = generatorMessageRepository;
        this.generatorGPTService = generatorGPTService;
        this.generatorCompleterService = generatorCompleterService;
    }

    /**
     * Generate a new project.
     *
     * @param request The generator request.
     * @return Generator response.
     */
    public GeneratorResponse generate(GeneratorRequest request, User user) {
        // Create initial entities.
        Generator generator = new Generator();
        generator.setUser(user);
        generator = generatorRepository.save(generator);
        
        // Create user message.
        GeneratorMessage message = generatorMessageRepository.save(new GeneratorMessage(
            request.getContent(),
            generator,
            user
        ));

        // Create OpenAI completion request.
        CompletionResponse completionResponse = generatorGPTService.getHelpMessage(message.getContent());

        // Create OpenAI message.
        GeneratorMessage openAIMessage = generatorMessageRepository.save(new GeneratorMessage(
            completionResponse.getChoices().get(0).getText(),
            generator,
            null
        ));

        
        // Create messages list.
        List<GeneratorMessage> generatorMessages = new ArrayList<>();
        generatorMessages.add(message);
        generatorMessages.add(openAIMessage);

        // add messages to generator
        generator.setMessages(generatorMessages);
        generatorRepository.save(generator);

        // Create messages list.
        List<MessageResponse> messageResponses = new ArrayList<>();
        //messageResponses.add(new MessageResponse(message, null));
        messageResponses.add(new MessageResponse(openAIMessage, null));
        
        // Return the response.
        return new GeneratorResponse(generator.getId(), messageResponses, generator.isCompleted(), 0L);
    }

    /**
     * Proceed with a chat generation.
     * 
     * @param request The generator request.
     * @return Generator response.
     */
    public GeneratorResponse proceed(MessageRequest request, User user) {
        GeneratorMessage message = generatorMessageRepository.save(new GeneratorMessage(
            request.getContent(),
            generatorRepository.findById(request.getGeneratorId()).get(),
            user
        ));

        Generator generator = generatorRepository.findById(request.getGeneratorId()).get();
        if (generator.isCompleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Generator is already completed.");
        }

        if (generator.getUser().getUsername() != user.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the generator.");
        }

        GPTResponse gptResponse = null;
        GeneratorMessage completionMessage = null;
        long mainChatId = 0L;
        if (isComplete(request.getContent())) {
            List<GeneratorMessage> messages = generator.getMessages();
            gptResponse = generatorGPTService.getProjectJSON(messages);
            mainChatId = generatorCompleterService.complete(generator, gptResponse, user);

            completionMessage = generatorMessageRepository.save(new GeneratorMessage(
                "Project completed!",
                generator,
                null
            ));
        } else {
            // Create help message
            CompletionResponse _completionResponse = generatorGPTService.getHelpMessage(message.getContent());
            completionMessage = generatorMessageRepository.save(new GeneratorMessage(
                _completionResponse.getChoices().get(0).getText(),
                generator,
                null
            ));
        }

        // Create messages list.
        List<GeneratorMessage> generatorMessages = generator.getMessages();
        generatorMessages.add(message);
        generatorMessages.add(completionMessage);

        // update generator messages
        generator.setMessages(generatorMessages);
        generatorRepository.save(generator);

        List<MessageResponse> messageResponses = new ArrayList<>();
        //messageResponses.add(new MessageResponse(message, gptResponse));
        messageResponses.add(new MessageResponse(completionMessage, gptResponse));

        return new GeneratorResponse(generator.getId(), messageResponses, generator.isCompleted(), mainChatId);
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

        return new GeneratorResponse(generator.getId(), messages, generator.isCompleted(), 0L);
    }

    /**
     * Force complete a generator.
     * 
     * @param authorization The authorized user.
     * @param generatorId The generator id.
     * @return Generator response.
     */
    public GeneratorResponse forceComplete(Long generatorId, User user) {
        Generator generator = generatorRepository.findById(generatorId).get();
        if (generator == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Generator not found!");
        }

        if (generator.getUser().getUsername() != user.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the generator.");
        }

        generator.setCompleted(true);
        generatorRepository.save(generator);

        List<MessageResponse> messageResponses = new ArrayList<>();
        messageResponses.add(new MessageResponse(
            generatorMessageRepository.save(new GeneratorMessage(
                "Generator cancelled!",
                generator,
                null
            )),
            null
        ));

        return new GeneratorResponse(generator.getId(), messageResponses, generator.isCompleted(), 0L);
    }

    /**
     * Check if a generator is complete by
     * checking if the last message is the complete command.
     * 
     * @param request The generator request.
     * @return boolean True if complete, false otherwise.
     */
    public boolean isComplete(String messageContent) {
        return GeneratorSanitizer
            .sanitize(messageContent)
            .equals(COMPLETE_COMMAND);
    }
}

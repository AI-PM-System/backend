package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

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
        Generator generator = generatorRepository.save(new Generator());
        
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
        List<MessageResponse> messages = new ArrayList<>();
        messages.add(new MessageResponse(message, null));
        messages.add(new MessageResponse(openAIMessage, null));
        
        // Return the response.
        return new GeneratorResponse(generator.getId(), messages);
    }

    /**
     * Proceed with a chat generation.
     * 
     * @param request The generator request.
     * @return Generator response.
     */
    public List<MessageResponse> proceed(MessageRequest request, User user) {
        GeneratorMessage message = generatorMessageRepository.save(new GeneratorMessage(
            request.getContent(),
            generatorRepository.findById(request.getGeneratorId()).get(),
            user
        ));

        GPTResponse gptResponse = null;
        GeneratorMessage completionMessage = null;
        if (isComplete(request.getContent())) {
            Generator generator = generatorRepository.findById(request.getGeneratorId()).get();
            List<GeneratorMessage> messages = generator.getMessages();
            gptResponse = generatorGPTService.getProjectJSON(messages);
            generatorCompleterService.complete(generator, gptResponse, user);

            completionMessage = generatorMessageRepository.save(new GeneratorMessage(
                "Project completed!",
                generator,
                null
            ));
        }

        List<MessageResponse> messages = new ArrayList<>();
        messages.add(new MessageResponse(message, gptResponse));
        if (completionMessage != null) {
            messages.add(new MessageResponse(completionMessage, null));
        }

        return messages;
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

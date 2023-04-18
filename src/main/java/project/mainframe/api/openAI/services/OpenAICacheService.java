package project.mainframe.api.openAI.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import project.mainframe.api.openAI.dto.completions.ChoiceResponse;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.openAI.entities.Choice;
import project.mainframe.api.openAI.entities.Completion;
import project.mainframe.api.openAI.entities.Usage;
import project.mainframe.api.openAI.repositories.ChoiceRepository;
import project.mainframe.api.openAI.repositories.CompletionRepository;
import project.mainframe.api.openAI.repositories.UsageRepository;

/**
 * The OpenAI Cache Service
 *
 * Used to cache the OpenAI API responses.
 */
@Service
public class OpenAICacheService {

    /**
     * The completion repository
     */
    private CompletionRepository completionRepository;

    /**
     * The choice repository
     */
    private ChoiceRepository choiceRepository;

    /**
     * The usage repository
     */
    private UsageRepository usageRepository;

    /**
     * Constructor
     *
     * @param completionRepository the completion repository
     * @param choiceRepository the choice repository
     * @param usageRepository the usage repository
     */
    public OpenAICacheService(
        CompletionRepository completionRepository,
        ChoiceRepository choiceRepository,
        UsageRepository usageRepository
    ) {
        this.completionRepository = completionRepository;
        this.choiceRepository = choiceRepository;
        this.usageRepository = usageRepository;
    }

    /** 
     * Cache an OpenAI completion response 
     * 
     * @param completion the completion
     * @return void
     */
    public void cacheCompletion(CompletionResponse completionResponse) {
        Completion completion = completionResponse.toCompletion();

        // Check if the completion already exists
        if (completionRepository.existsById(completion.getId())) {
            return;
        }
        
        // Save usage and choices first
        Usage usage = usageRepository.save(completionResponse.getUsage().toUsage());
        List<Choice> choices = choiceRepository.saveAll(completionResponse.getChoices().stream().map(ChoiceResponse::toChoice).toList());

        // Set the usage and choices again
        completion.setUsage(usage);
        completion.setChoices(choices);

        // Save the completion
        completionRepository.save(completion);
    }

    /**
     * Get the number of completions between two LocalDateTime objects
     * 
     * @param start the start
     * @param end the end
     * @return int
     */
    public int getCompletionsBetween(LocalDateTime start, LocalDateTime end) {
        return completionRepository.countByCreatedAtBetween(start, end);
    }
}

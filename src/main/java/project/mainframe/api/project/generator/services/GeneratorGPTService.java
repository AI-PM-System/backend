package project.mainframe.api.project.generator.services;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.mainframe.api.openAI.dto.completions.CompletionRequest;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.openAI.exceptions.OpenAIException;
import project.mainframe.api.openAI.services.OpenAIService;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.utils.GeneratorGPTSanitizer;

/**
 * Generator GPT service.
 * 
 * This service is responsible for generating messages using OpenAI's completion endpoint.
 */
@Service
public class GeneratorGPTService {
    
    /**
     * Completion prompt.
     * Send to OpenAI's completion endpoint when the generator is completed.
     */
    private static final String COMPLETION_PROMPT = "Answer as %s planning a project, who writes in tone %s, use max 650 tokens to answer, do not include leading zeroes, and based on the following message: %s; Design a project and answer everything in minified JSON formatted exact like the following: {\"project\":{\"name\":\"\",\"description\":\"\"},\"roles\":[{\"name\":\"\",\"description\":\"\"}],\"members\":[{\"roleId\":0,\"isAI\":false}],\"events\":[{\"name\":\"\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"\",\"agenda\":\"\"}],\"boards\":[{\"name\":\"\",\"description\":\"\"}],\"boardLists\":[{\"name\":\"\",\"boardId\":0}],\"tasks\":[{\"name\":\"\",\"description\":\"\",\"boardListId\":0,\"dueDate\":\"2023-04-04T00:00:00\"}]}";
    
    /**
     * Help prompt.
     * Send to OpenAI's completion endpoint after each message, except when completing.
     */
    private static final String HELP_PROMPT = "Answer as %s, use max 50 tokens to answer, write in tone %s, and suggest missing project elements based on the following message: %s";

    /**
     * Welcome message prompt.
     * Send to OpenAI's completion endpoint when the generation is complete 
     * to create a welcome message for the new project.
     */
    private static final String WELCOME_MESSAGE_PROMPT = "Answer as %s who welcomes the user to the project professional, who does not use names, include emojis, and write in tone %s. The project's name is: %s";

    /**
     * The OpenAI service.
     */
    private OpenAIService openAIService;    

    /**
     * The object mapper.
     */
    private ObjectMapper objectMapper = new ObjectMapper();
 
    /**
     * Constructor.
     * 
     * @param openAIService The OpenAI service.
     */
    public GeneratorGPTService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    /**
     * Get a helpfull response for project planning.
     * 
     * @param messages
     * @param openAIService
     * @return
     */
    public CompletionResponse getHelpMessage(String message, String gptActor, String gptTone) {
        String prompt = String.format(HELP_PROMPT, gptActor, gptTone, message);
        return getCompletionResponse(prompt, 50); 

        // Fake response
        /*
        return CompletionResponse.builder()
            .choices(List.of(ChoiceResponse.builder()
                .text("Sounds like a great project. Tell me more about it.")
                .build()))
            .usage(UsageResponse.builder()
                .prompt_tokens(20)
                .build())
            .build(); */
    }

    /**
     * Get a welcome message.
     * @return The welcome message.
     */
    public CompletionResponse getWelcomeMessage(String message, String gptActor, String gptTone) {
        String prompt = String.format(WELCOME_MESSAGE_PROMPT, gptActor, gptTone, message);
        return getCompletionResponse(prompt, 50);

        // Fake response
        /*
        return CompletionResponse.builder()
            .choices(List.of(ChoiceResponse.builder()
                .text("Welcome to the Mobile App Project! I'm excited to help you plan out this project and make sure it runs smoothly. Let's get started!")
                .build()))
            .usage(UsageResponse.builder()
                .prompt_tokens(20)
                .build())
            .build(); */
    }
    
    /**
     * Transform A list of GeneratorMessages
     * into a project.
     * 
     * @param messages The generator messages.
     * @param openAIService The OpenAI service.
     * @return The project.
     */
    public GPTResponse getProjectJSON(String message, String gptActor, String gptTone) {
        GPTResponse gptResponse = null;

        String prompt = String.format(COMPLETION_PROMPT, gptActor, gptTone, message);
        
        try {
            // Fake JSON string
            // String json = "{\"boards\":[{\"name\":\"Main board\",\"description\":\"main board description\"}],\"tasks\":[{\"name\":\"test 1\",\"description\":\"test 2\",\"boardListId\":0,\"dueDate\":\"2023-04-04T00:00:00\"}],\"boardLists\":[{\"name\":\"to-do\",\"boardId\":0}],\"project\":{\"name\":\"Website Development\",\"description\":\"Building a website for a business with a deadline at the end of the month\"},\"roles\":[{\"name\":\"Project Manager\",\"description\":\"Responsible for managing the project\"},{\"name\":\"Designer\",\"description\":\"Responsible for designing the website\"},{\"name\":\"Developer\",\"description\":\"Responsible for developing the website\"}],\"members\":[{\"roleId\":0,\"isAI\":false},{\"roleId\":1,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false}],\"events\":[{\"name\":\"Scrum Planning\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Discuss the project plan and assign tasks to members\"},{\"name\":\"Design Review\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Review the design and make necessary changes\"},{\"name\":\"Development Review\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Review the development progress and make necessary changes\"},{\"name\":\"Project Review\",\"startDateTime\":{\"year\":2020,\"month\":7,\"day\":20,\"hour\":10,\"minute\":0},\"endDateTime\":{\"year\":2020,\"month\":7,\"day\":20,\"hour\":12,\"minute\":0},\"location\":\"Online\",\"agenda\":\"Review the project and make sure it meets the requirements\"}]}";
            // String json = "asda\"!rk90\n\r3%sodsdasd {\"boards\":[{\"name\":\"Main board\",\"description\":\"main board description\"}],\"tasks\":[{\"name\":\"test 1\",\"description\":\"test 2\",\"boardListId\":0,\"dueDate\":\"2023-04-04T00:00:00\"}],\"boardLists\":[{\"name\":\"to-do\",\"boardId\":0}],\"project\":{\"name\":\"Website Development\",\"description\":\"Building a website for a business with a deadline at the end of the month\"},\"roles\":[{\"name\":\"Project Manager\",\"description\":\"Responsible for managing the project\"},{\"name\":\"Designer\",\"description\":\"Responsible for designing the website\"},{\"name\":\"Developer\",\"description\":\"Responsible for developing the website\"}],\"members\":[{\"roleId\":0,\"isAI\":false},{\"roleId\":1,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false}],\"events\":[{\"name\":\"Scrum Planning\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Discuss the project plan and assign tasks to members\"},{\"name\":\"Design Review\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Review the design and make necessary changes\"},{\"name\":\"Development Review\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Review the development progress and make necessary changes\"},{\"name\":\"Project Review\",\"startDateTime\":\"2021-04-30T00:00:00\",\"endDateTime\":\"2021-04-30T00:00:00\",\"location\":\"Online\",\"agenda\":\"Review the project and make sure it meets the requirements\"}]}asdasdasd'\"d893";
            
            CompletionResponse response = getCompletionResponse(prompt, 712);
            String json = response.getChoices().get(0).getText();
            // Parse JSON from response using the object mapper
            gptResponse = objectMapper.readValue(GeneratorGPTSanitizer.sanitize(json), GPTResponse.class);

        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gptResponse;
    }

    /**
     * Returns a completion response.
     * 
     * @param message The message.
     * @return CompletionResponse The completion response.
     */
    private CompletionResponse getCompletionResponse(String message, int maxTokens) {
        CompletionResponse completionResponse = null;

        try {
            completionResponse = openAIService.getCompletions(CompletionRequest.builder()
                .prompt(message)
                .model("text-davinci-003")
                .maxTokens(maxTokens)
                .temperature(0.7)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .build());
        } catch (OpenAIException e) {
            e.printStackTrace();
        }

        return completionResponse;
    }
}

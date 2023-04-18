package project.mainframe.api.project.generator.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.openAI.dto.completions.ChoiceResponse;
import project.mainframe.api.openAI.dto.completions.CompletionRequest;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.openAI.dto.completions.UsageResponse;
import project.mainframe.api.openAI.exceptions.OpenAIException;
import project.mainframe.api.openAI.services.OpenAIService;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.entities.GeneratorMessage;

/**
 * Generator serializer.
 */
@Service
public class GeneratorGPTService {
    
    /**
     * Completion prompt.
     */
    private static final String COMPLETION_PROMPT = "Act as a project manager planning a project based on the following message: >%s<, answer everything in JSON formatted exact like the following (Make the JSON minified) (Max 4 events) (Max 512 tokens answer): {\"project\":{\"name\":\"\",\"description\":\"\"},\"roles\":[{\"name\":\"\",\"description\":\"\"}],\"members\":[{\"roleId\":0,\"isAI\":false}],\"events\":[{\"name\":\"\",\"startDateTime\":{\"year\":0,\"month\":0,\"day\":0,\"hour\":0,\"minute\":0},\"endDateTime\":{\"year\":0,\"month\":0,\"day\":0,\"hour\":0,\"minute\":0},\"location\":\"\",\"agenda\":\"\"}]}";
    
    /**
     * Help prompt.
     */
    private static final String HELP_PROMPT = "Act as a project planner assistant who knows everything about SCRUM and answer the following message (You got max 50 tokens): >%s<";

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
    public CompletionResponse getHelpMessage(String message) {
        String prompt = String.format(HELP_PROMPT, message);
        //return getCompletionResponse(prompt, 50); comment out real call, to test

        // Fake response
        return CompletionResponse.builder()
            .choices(List.of(ChoiceResponse.builder()
                .text("You can ask me questions like: What is a sprint? What is a project? What is a role? What is a member? What is a event? What is a project manager? What is a project planner? What is a")
                .build()))
            .usage(UsageResponse.builder()
                .prompt_tokens(20)
                .build())
            .build();

    }
    
    /**
     * Transform A list of GeneratorMessages
     * into a project.
     * 
     * @param messages The generator messages.
     * @param openAIService The OpenAI service.
     * @return The project.
     */
    public GPTResponse getProjectJSON(List<GeneratorMessage> messages) {
        GPTResponse gptResponse = null;

        String message = toSingularMessage(messages);
        String prompt = String.format(COMPLETION_PROMPT, message);
        
        try {
            CompletionResponse response = getCompletionResponse(prompt, 512);
            // Get JSON string
            //String json = "{\"project\":{\"name\":\"Website Development\",\"description\":\"Building a website for a business with a deadline at the end of the month\"},\"roles\":[{\"name\":\"Project Manager\",\"description\":\"Responsible for managing the project\"},{\"name\":\"Designer\",\"description\":\"Responsible for designing the website\"},{\"name\":\"Developer\",\"description\":\"Responsible for developing the website\"}],\"members\":[{\"roleId\":0,\"isAI\":false},{\"roleId\":1,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false},{\"roleId\":2,\"isAI\":false}],\"events\":[{\"name\":\"Scrum Planning\",\"startDateTime\":{\"year\":2020,\"month\":7,\"day\":1,\"hour\":10,\"minute\":0},\"endDateTime\":{\"year\":2020,\"month\":7,\"day\":1,\"hour\":12,\"minute\":0},\"location\":\"Online\",\"agenda\":\"Discuss the project plan and assign tasks to members\"},{\"name\":\"Design Review\",\"startDateTime\":{\"year\":2020,\"month\":7,\"day\":3,\"hour\":10,\"minute\":0},\"endDateTime\":{\"year\":2020,\"month\":7,\"day\":3,\"hour\":12,\"minute\":0},\"location\":\"Online\",\"agenda\":\"Review the design and make necessary changes\"},{\"name\":\"Development Review\",\"startDateTime\":{\"year\":2020,\"month\":7,\"day\":10,\"hour\":10,\"minute\":0},\"endDateTime\":{\"year\":2020,\"month\":7,\"day\":10,\"hour\":12,\"minute\":0},\"location\":\"Online\",\"agenda\":\"Review the development progress and make necessary changes\"},{\"name\":\"Project Review\",\"startDateTime\":{\"year\":2020,\"month\":7,\"day\":20,\"hour\":10,\"minute\":0},\"endDateTime\":{\"year\":2020,\"month\":7,\"day\":20,\"hour\":12,\"minute\":0},\"location\":\"Online\",\"agenda\":\"Review the project and make sure it meets the requirements\"}]}";
            String json = response.getChoices().get(0).getText();
            // Parse JSON from response using the object mapper
            gptResponse = objectMapper.readValue(json, GPTResponse.class);


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
     * Transform the generator messages into a complete string
     * 
     * @param messages The generator messages.
     * @return The complete string.
     */
    private static String toSingularMessage(List<GeneratorMessage> messages) {
        StringBuilder builder = new StringBuilder();

        for (GeneratorMessage message : messages) {
            builder.append(message.getContent());
            builder.append(System.lineSeparator());
        }

        return builder.toString();
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
                .temperature(0.9)
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

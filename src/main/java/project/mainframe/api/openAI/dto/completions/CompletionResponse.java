package project.mainframe.api.openAI.dto.completions;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.openAI.entities.Completion;

/**
 * The Completions response.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompletionResponse {

    /**
     * The id.
     */
    private String id;

    /**
     * The object.
     */
    private String object;

    /**
     * When the object was created.
     */
    private int created;

    /**
     * The model.
     */
    private String model;

    /**
     * The choices.
     */
    private List<ChoiceResponse> choices;

    /**
     * The usage.
     */
    private UsageResponse usage;
    
    /**
     * No-args constructor
     */
    public CompletionResponse() {}

    /**
     * To Completion.
     */
    public Completion toCompletion() {        
        return new Completion(
            id, 
            object, 
            created, 
            model, 
            null,
            null,
            LocalDateTime.now()
        );
    }

    /**
     * to string
     * 
     * @return string
     */
    @Override
    public String toString() {
        return "CompletionResponse [choices=" + choices + ", completion=" + id + ", created=" + created + ", model=" + model + ", object=" + object + ", usage=" + usage + "]";
    }
}
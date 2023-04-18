package project.mainframe.api.openAI.services;

import java.time.LocalDateTime;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.mainframe.api.openAI.dto.completions.CompletionRequest;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;
import project.mainframe.api.openAI.exceptions.OpenAIException;
import project.mainframe.api.openAI.dto.completions.CompletionResponse;


/**
 * The OpenAI service.
 */
@Service
public class OpenAIService {

    /**
     * The API URL
     */
    private static final String API_BASE_URL = "https://api.openai.com/v1/";

    /**
     * The object mapper
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * The web client
     */
    private static final WebClient CLIENT = WebClient.create();
    
    /**
     * The API key for OpenAI's API.
     */
    @Value("${openAI.api-key}")
    private String API_KEY;

    /**
     * The max number of daily requests
     */
    @Value("${openAI.max-daily-requests}")
    private int MAX_DAILY_REQUESTS;

    /** 
     * The OpenAI cache service 
     */
    private OpenAICacheService openAICacheService;

    /**
     * Constructor
     * 
     * @param openAICacheService the OpenAI cache service
     */
    public OpenAIService(OpenAICacheService openAICacheService) {
        this.openAICacheService = openAICacheService;
    }

    /**
     * Get a completion from OpenAI's API.
     * 
     * @param request
     * @return the response
     */
    public CompletionResponse getCompletions(CompletionRequest request) throws OpenAIException {
        if (isDailyRequestsExceeded()) {
            throw new OpenAIException("Daily requests exceeded");
        }

        Map<String, Object> body = toMap(request);
        String json = toJson(body);
        URI uri = URI.create(API_BASE_URL + "completions");

        CompletionResponse response = CLIENT.post()
            .uri(uri)
            .header("Authorization", getAuthorizationHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(json))
            .retrieve()
            .bodyToMono(CompletionResponse.class)
            .block();

        // Cache the response
        openAICacheService.cacheCompletion(response);

        return response;
    }

    /**
     * Transform the request into a map
     * 
     * @param request the request
     * @return the map
     */
    private Map<String, Object> toMap(CompletionRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("model", request.getModel());
        map.put("prompt", request.getPrompt());
        map.put("temperature", request.getTemperature());
        map.put("max_tokens", request.getMaxTokens());
        map.put("top_p", request.getTopP());
        map.put("frequency_penalty", request.getFrequencyPenalty());
        map.put("presence_penalty", request.getPresencePenalty());
        return map;
    }

    /**
     * Transform a map into a JSON string
     * 
     * @param map the map
     * @return the JSON string
     */
    private String toJson(Map<String, Object> map) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Get the authorization header
     * 
     * @return the authorization header
     */
    private String getAuthorizationHeader() {
        return String.format("Bearer %s", API_KEY);
    }

    /**
     * Check if daily requests are exceeded
     * 
     * @return true if exceeded, false otherwise
     */
    public boolean isDailyRequestsExceeded() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        int completionsCount = openAICacheService.getCompletionsBetween(startOfDay, endOfDay);
        return completionsCount >= MAX_DAILY_REQUESTS;
    }
}

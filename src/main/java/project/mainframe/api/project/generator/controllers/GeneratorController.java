package project.mainframe.api.project.generator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.generator.*;
import project.mainframe.api.project.generator.dto.message.MessageRequest;
import project.mainframe.api.project.generator.services.GeneratorService;

/**
 * The Generator controller.
 * Contains the endpoints to generate a new project.
 */
@RestController
@RequestMapping("/v1/user/generate/project")
public class GeneratorController {
    
    /**
     * The Generator service.
     */
    private final GeneratorService generatorService;

    /**
     * Constructor.
     * @param generatorService The generator service.
     */
    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }
    
    /**
     * Creates a new chat generator.
     * 
     * @param request The generator request.
     * @param authorization The authorized user.
     * @return Generator response.
     */
    @PostMapping("/new")
    public GeneratorResponse generate(@RequestBody GeneratorRequest request, @Authorization User user) {
        return this.generatorService.generate(request, user);
    }

    /**
     * Proceed with a chat generation.
     * Note: send #complete to finish the generation.
     * 
     * @param request The generator request.
     * @param authorization The authorized user.
     * @return Generator response.
     */
    @PostMapping("/proceed")
    public GeneratorResponse proceed(@RequestBody MessageRequest request, @Authorization User user) {
        return this.generatorService.proceed(request, user);
    }

    /**
     * cancel a generator.
     * 
     * @param authorization The authorized user.
     * @param generatorId The generator id.
     * @return Generator response.
     */
    @PostMapping("/cancel/{generatorId}")
    public GeneratorResponse cancel(@Authorization User user, @PathVariable Long generatorId) {
        return this.generatorService.cancel(generatorId, user);
    }

    /**
     * Complete a generator.
     * 
     * @param authorization The authorized user.
     * @param generatorId The generator id.
     * @return Generator response.
     */
    @PostMapping("/complete/{generatorId}")
    public GeneratorResponse complete(@Authorization User user, @PathVariable Long generatorId) {
        return this.generatorService.complete(generatorId, user);
    }

    /**
     * Get any generator that is not completed.
     * 
     * @param authorization The authorized header.
     * @return The generator response.
     */
    @GetMapping("/incomplete")
    public GeneratorResponse getIncompleteGenerator(@Authorization User user) {
        return this.generatorService.getIncompleteGenerator(user);
    }
}

package project.mainframe.api.project.generator.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.generator.dto.actor.GeneratorActorResponse;
import project.mainframe.api.project.generator.services.GeneratorActorService;

/**
 * The Generator actor controller.
 */
@RestController
@RequestMapping("/api/v1/user/generate/actors")
public class GeneratorActorController {
    
    /**
     * The Generator actor service.
     */
    private final GeneratorActorService generatorActorService;

    /**
     * Constructor.
     * @param generatorActorService The generator actor service.
     */
    public GeneratorActorController(GeneratorActorService generatorActorService) {
        this.generatorActorService = generatorActorService;
    }

    /**
     * Get all generator actors.
     * 
     * @return the list of generator actors.
     */
    @GetMapping
    public List<GeneratorActorResponse> getAllGeneratorActors() {
        return this.generatorActorService.getAllGeneratorActors();
    }
}

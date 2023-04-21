package project.mainframe.api.project.generator.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.generator.dto.tone.GeneratorToneResponse;
import project.mainframe.api.project.generator.services.GeneratorToneService;

/**
 * The Generator tone controller.
 */
@RestController
@RequestMapping("/api/v1/user/generate/tones")
public class GeneratorToneController {
    
    /**
     * The Generator tone service.
     */
    private final GeneratorToneService generatorToneService;

    /**
     * Constructor.
     * @param generatorToneService The generator tone service.
     */
    public GeneratorToneController(GeneratorToneService generatorToneService) {
        this.generatorToneService = generatorToneService;
    }

    /**
     * Get all generator tones.
     * 
     * @return the list of generator tones.
     */
    @GetMapping
    public List<GeneratorToneResponse> getAllGeneratorTones() {
        return this.generatorToneService.getAllGeneratorTones();
    }
}

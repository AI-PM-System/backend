package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.generator.dto.tone.GeneratorToneResponse;
import project.mainframe.api.project.generator.repositories.GeneratorToneRepository;

@Service
public class GeneratorToneService {
    
    /**
     * The generator tone repository.
     */
    private GeneratorToneRepository generatorToneRepository;

    /**
     * constructor.
     * 
     * @param generatorToneRepository
     */
    public GeneratorToneService(GeneratorToneRepository generatorToneRepository) {
        this.generatorToneRepository = generatorToneRepository;
    }

    /**
     * Get all generator tones.
     * 
     * @return the list of generator tones.
     */
    public List<GeneratorToneResponse> getAllGeneratorTones() {
        List<GeneratorToneResponse> generatorToneResponses = new ArrayList<>();
        generatorToneRepository.findAll().forEach(generatorTone -> {
            generatorToneResponses.add(new GeneratorToneResponse(generatorTone.getId(), generatorTone.getTone()));
        });
        return generatorToneResponses;
    }
}

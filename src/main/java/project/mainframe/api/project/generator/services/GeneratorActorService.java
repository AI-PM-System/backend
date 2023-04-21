package project.mainframe.api.project.generator.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.generator.dto.actor.GeneratorActorResponse;
import project.mainframe.api.project.generator.repositories.GeneratorActorRepository;

@Service
public class GeneratorActorService {
    
    /**
     * The generator actor repository.
     */
    private GeneratorActorRepository generatorActorRepository;

    /**
     * constructor.
     * 
     * @param generatorActorRepository
     */
    public GeneratorActorService(GeneratorActorRepository generatorActorRepository) {
        this.generatorActorRepository = generatorActorRepository;
    }

    /**
     * Get all generator actors.
     * 
     * @return the list of generator actors.
     */
    public List<GeneratorActorResponse> getAllGeneratorActors() {
        List<GeneratorActorResponse> generatorActorResponses = new ArrayList<>();
        generatorActorRepository.findAll().forEach(generatorActor -> {
            generatorActorResponses.add(new GeneratorActorResponse(generatorActor.getId(), generatorActor.getName()));
        });
        return generatorActorResponses;
    }
}

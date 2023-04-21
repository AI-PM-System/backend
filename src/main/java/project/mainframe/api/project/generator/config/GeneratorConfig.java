package project.mainframe.api.project.generator.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import project.mainframe.api.project.generator.entities.GeneratorActor;
import project.mainframe.api.project.generator.entities.GeneratorTone;
import project.mainframe.api.project.generator.repositories.GeneratorActorRepository;
import project.mainframe.api.project.generator.repositories.GeneratorToneRepository;

@Configuration
public class GeneratorConfig implements ApplicationRunner {

    /**
     * The generator actor repository.
     */
    private GeneratorActorRepository generatorActorRepository;

    /**
     * The generator tone repository.
     */
    private GeneratorToneRepository generatorToneRepository;

    /**
     * constructor.
     * 
     * @param generatorActorRepository
     * @param generatorToneRepository
     */
    public GeneratorConfig(GeneratorActorRepository generatorActorRepository, GeneratorToneRepository generatorToneRepository) {
        this.generatorActorRepository = generatorActorRepository;
        this.generatorToneRepository = generatorToneRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createActors();
        createTones();
    }

    private void createActors() {
        // check if there are any generator actors in the database.
        if (generatorActorRepository.count() > 0) {
            return;
        }

        // Create default generator actors.
        List<GeneratorActor> generatorActors = new ArrayList<>();
        generatorActors.add(new GeneratorActor("SCRUM Master"));
        generatorActors.add(new GeneratorActor("Project Manager"));
        generatorActors.add(new GeneratorActor("University Professor"));
        generatorActors.add(new GeneratorActor("Steve Jobs"));
        generatorActors.add(new GeneratorActor("Einstein"));
        generatorActors.add(new GeneratorActor("Batman"));
        generatorActors.add(new GeneratorActor("Spiderman"));
        generatorActors.add(new GeneratorActor("Marketing Director"));
        generatorActors.add(new GeneratorActor("Graphic Designer"));
        generatorActors.add(new GeneratorActor("CEO"));
        generatorActors.add(new GeneratorActor("HR Manager"));
        generatorActors.add(new GeneratorActor("Sales Manager"));
        generatorActors.add(new GeneratorActor("Elon Musk"));
        generatorActors.add(new GeneratorActor("Software Engineer"));
        generatorActors.add(new GeneratorActor("UX Designer"));
        generatorActors.add(new GeneratorActor("Investment Banker"));
        generatorActors.add(new GeneratorActor("Software Developer"));
        generatorActors.add(new GeneratorActor("Journalist"));
        generatorActors.add(new GeneratorActor("Entrepreneur"));
        generatorActors.add(new GeneratorActor("Project Coordinator"));
        generatorActors.add(new GeneratorActor("Event Planner"));
        generatorActors.add(new GeneratorActor("Executive Assistant"));
        generatorActors.add(new GeneratorActor("Real Estate Agent"));
        generatorActors.add(new GeneratorActor("Customer Support Specialist"));
        generatorActors.add(new GeneratorActor("Content Marketer"));
        generatorActors.add(new GeneratorActor("Copywriter"));
        generatorActors.add(new GeneratorActor("Sales Representative"));
        generatorActors.add(new GeneratorActor("Financial Advisor"));
        generatorActors.add(new GeneratorActor("Operations Manager"));
        generatorActors.add(new GeneratorActor("Researcher"));
        generatorActors.add(new GeneratorActor("Social Media Manager"));
        generatorActors.add(new GeneratorActor("Technical Writer"));
        generatorActors.add(new GeneratorActor("Product Manager"));
        generatorActors.add(new GeneratorActor("Digital Marketing Manager"));
        generatorActors.add(new GeneratorActor("PR Manager"));
        generatorActors.add(new GeneratorActor("Data Analyst"));
        generatorActors.add(new GeneratorActor("Web Developer"));
        generatorActors.add(new GeneratorActor("System Administrator"));
        generatorActors.add(new GeneratorActor("IT Manager"));
        generatorActors.add(new GeneratorActor("Graphic Artist"));
        generatorActors.add(new GeneratorActor("Artist"));
        generatorActors.add(new GeneratorActor("Educator"));
        generatorActors.add(new GeneratorActor("Counselor"));
        generatorActors.add(new GeneratorActor("Project Analyst"));
        generatorActors.add(new GeneratorActor("Marketing Coordinator"));
        generatorActors.add(new GeneratorActor("Brand Manager"));
        generatorActors.add(new GeneratorActor("Freelancer"));
        generatorActors.add(new GeneratorActor("Executive Director"));
        generatorActors.add(new GeneratorActor("Accountant"));
        generatorActors.add(new GeneratorActor("HR Director"));
        generatorActors.add(new GeneratorActor("Recruiter"));
        generatorActors.add(new GeneratorActor("Famous Actor"));
        generatorActors.add(new GeneratorActor("Famous Musician"));
        generatorActors.add(new GeneratorActor("Famous Athlete"));
        generatorActors.add(new GeneratorActor("Famous Writer"));
        generatorActors.add(new GeneratorActor("Famous Scientist"));
        generatorActors.add(new GeneratorActor("Evil Villian"));
        generatorActorRepository.saveAll(generatorActors);
    }

    private void createTones() {
        // Check if there are any generator tones in the database.
        if (generatorToneRepository.count() > 0) {
            return;
        }

        // Create default generator tones.
        List<GeneratorTone> generatorTones = new ArrayList<>();
        generatorTones.add(new GeneratorTone("Assertive"));
        generatorTones.add(new GeneratorTone("Confident"));
        generatorTones.add(new GeneratorTone("Cooperative"));
        generatorTones.add(new GeneratorTone("Curious"));
        generatorTones.add(new GeneratorTone("Diplomatic"));
        generatorTones.add(new GeneratorTone("Direct"));
        generatorTones.add(new GeneratorTone("Energetic"));
        generatorTones.add(new GeneratorTone("Enthusiastic"));
        generatorTones.add(new GeneratorTone("Flexible"));
        generatorTones.add(new GeneratorTone("Friendly"));
        generatorTones.add(new GeneratorTone("Helpful"));
        generatorTones.add(new GeneratorTone("Humble"));
        generatorTones.add(new GeneratorTone("Inspiring"));
        generatorTones.add(new GeneratorTone("Logical"));
        generatorTones.add(new GeneratorTone("Objective"));
        generatorTones.add(new GeneratorTone("Optimistic"));
        generatorTones.add(new GeneratorTone("Patient"));
        generatorTones.add(new GeneratorTone("Persistent"));
        generatorTones.add(new GeneratorTone("Reflective"));
        generatorTones.add(new GeneratorTone("Respectful"));
        generatorToneRepository.saveAll(generatorTones);
    }
}

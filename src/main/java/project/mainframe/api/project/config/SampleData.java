package project.mainframe.api.project.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * Sample data.
 */
@Configuration
public class SampleData implements ApplicationRunner {

    /**
     * The project repository.
     */
    private ProjectRepository projectRepository;

    /**
     * Constructor.
     * @param projectRepository
     */
    public SampleData(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    /**
     * Runs the application.
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*projectRepository.saveAll(
            List.of(
                Project.builder().name("name").description("description").build(),
                Project.builder().name("name2").description("description2").build()
            )
        );*/
    }
}

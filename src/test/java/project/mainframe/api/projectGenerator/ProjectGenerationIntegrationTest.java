package project.mainframe.api.projectGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import project.mainframe.api.project.generator.controllers.GeneratorController;
import project.mainframe.api.project.generator.dto.generator.GeneratorRequest;
import project.mainframe.api.project.generator.dto.generator.GeneratorResponse;
import project.mainframe.api.project.generator.dto.message.MessageRequest;
import project.mainframe.api.project.generator.dto.message.MessageResponse;
import project.mainframe.api.project.generator.repositories.GeneratorMessageRepository;
import project.mainframe.api.project.generator.repositories.GeneratorRepository;
import project.mainframe.api.project.generator.services.GeneratorService;

/**
 * Project generation integration test.
 */
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
public class ProjectGenerationIntegrationTest {

    /**
     * The Generator controller.
     */
    private GeneratorController generatorController;

    /**
     * The Generator service.
     */
    private GeneratorService generatorService;

    /**
     * Generator repository.
     */
    @Autowired
    private GeneratorRepository generatorRepository;

    /**
     * Message repository.
     */
    @Autowired
    private GeneratorMessageRepository generatorMessageRepository;

    /**
     * Setup the test.
     */
    @BeforeAll
    public void setup() {
        generatorService = new GeneratorService(generatorRepository, generatorMessageRepository);
        generatorController = new GeneratorController(generatorService);
    }

    @Test
    public void testNew() {
        /// STEP 1: Send a new message to create a new generator.
        String generatorContent = "new";
        
        // This is the first message sent to the generator.
        GeneratorRequest generatorRequest = new GeneratorRequest();
        generatorRequest.setContent(generatorContent);
        
        // This is the response from the controller.
        GeneratorResponse generatorResponse = generatorController.generate(generatorRequest);

        // Assert that the response got a generator id that is not 0.
        assertNotEquals(0, generatorResponse.getId());

        // Assert that the response contains a message with the correct content.
        assertEquals(1, generatorResponse.getMessages().size());
        assertEquals(generatorContent, generatorResponse.getMessages().get(0).getContent());

        // save the generator id for later use.
        long generatorId = generatorResponse.getId();

        /// STEP 2: Send another message to add information to the generator.
        String proceedContent = "proceed";

        // This is the second message sent to the generator.
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent(proceedContent);
        messageRequest.setGeneratorId(generatorId);

        // This is the response from the controller.
        MessageResponse messageResponse = generatorController.proceed(messageRequest);

        // Assert that the response got a generator id that is not 0.
        assertNotEquals(0, messageResponse.getId());

        // Assert that the generator id is the same as the one we sent.
        assertEquals(generatorId, messageResponse.getGeneratorId());

        // Assert that the response contains a message with the correct content.
        assertEquals(proceedContent, messageResponse.getContent());

        /// STEP 3: Send a complete message to finish the generator.
        String completeContent = "#complete";

        // This is the third message sent to the generator.
        messageRequest = new MessageRequest();
        messageRequest.setContent(completeContent);
        messageRequest.setGeneratorId(generatorId);

        // This is the response from the controller.
        messageResponse = generatorController.proceed(messageRequest);

        // Assert that the response got a generator id that is not 0.
        assertNotEquals(0, messageResponse.getId());

        // Assert that the generator id is the same as the one we sent.
        assertEquals(generatorId, messageResponse.getGeneratorId());

        // Assert that the response contains a message with the correct content.
        assertEquals(completeContent, messageResponse.getContent());

        // Assert the project is not null.
        assertNotEquals(null, messageResponse.getProject());
    }
}

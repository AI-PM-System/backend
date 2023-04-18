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
public class ProjectGenerationIntegrationTest {

}

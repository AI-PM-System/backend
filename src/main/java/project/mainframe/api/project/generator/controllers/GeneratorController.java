package project.mainframe.api.project.generator.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.generator.dto.generator.*;
import project.mainframe.api.project.generator.dto.message.*;
import project.mainframe.api.project.generator.services.GeneratorService;
import project.mainframe.api.project.services.UserIdentityService;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * The Generator controller.
 * Contains the endpoints to generate a new project.
 */
@RestController
@RequestMapping("/api/v1/user/generate/project")
public class GeneratorController {
    
    /**
     * The Generator service.
     */
    private final GeneratorService generatorService;

    /**
     * The User identity service
     */
    private final UserIdentityService userIdentityService;

    /**
     * Constructor.
     * @param generatorService The generator service.
     * @param userIdentityService The user identity service.
     */
    public GeneratorController(GeneratorService generatorService, UserIdentityService userIdentityService) {
        this.generatorService = generatorService;
        this.userIdentityService = userIdentityService;
    }
    
    /**
     * Creates a new chat generator.
     * 
     * @param request The generator request.
     * @return Generator response.
     */
    @PostMapping("/new")
    public GeneratorResponse generate(@RequestBody GeneratorRequest request, @RequestHeader("Authorization") String authorization) {
        User user = this.userIdentityService.getAuthenticatableIdentity(authorization);
        return this.generatorService.generate(request, user);
    }

    /**
     * Proceed with a chat generation.
     * Note: send #complete to finish the generation.
     * 
     * @param request The generator request.
     * @return Generator response.
     */
    @PostMapping("/proceed")
    public List<MessageResponse> proceed(@RequestBody MessageRequest request, @RequestHeader("Authorization") String authorization) {
        User user = this.userIdentityService.getAuthenticatableIdentity(authorization);
        return this.generatorService.proceed(request, user);
    }
}

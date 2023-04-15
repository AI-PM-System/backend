package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.project.ProjectRequest;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Project;

/**
 * Project controller.
 */
@RestController
@RequestMapping("/api/v1/public/projects")
public class ProjectController extends BaseCrudController<ProjectRequest, ProjectResponse, Project, Long> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public ProjectController(BaseCrudService<ProjectRequest, ProjectResponse, Project, Long> baseCrudService) {
        super(baseCrudService);
    }
}

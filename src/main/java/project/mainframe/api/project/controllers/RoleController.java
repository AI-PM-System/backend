package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.role.RoleRequest;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Role;

@RequestMapping("/api/roles")
public class RoleController extends BaseCrudController<RoleRequest, RoleResponse, Role, Long> {

    public RoleController(BaseCrudService<RoleRequest, RoleResponse, Role, Long> baseCrudService) {
        super(baseCrudService);
    }
}


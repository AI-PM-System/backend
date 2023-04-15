package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.user.UserRequest;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.User;

/**
 * User controller.
 */
@RestController
@RequestMapping("/api/v1/user/users")
public class UserController extends BaseCrudController<UserRequest, UserResponse, User, String> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public UserController(BaseCrudService<UserRequest, UserResponse, User, String> baseCrudService) {
        super(baseCrudService);
    }
}

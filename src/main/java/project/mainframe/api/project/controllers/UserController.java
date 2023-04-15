package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.user.UserRequest;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.User;

@RequestMapping("/api/users")
public class UserController extends BaseCrudController<UserRequest, UserResponse, User, String> {

    public UserController(BaseCrudService<UserRequest, UserResponse, User, String> baseCrudService) {
        super(baseCrudService);
    }
}

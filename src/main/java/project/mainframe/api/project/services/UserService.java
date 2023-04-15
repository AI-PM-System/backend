package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.user.UserRequest;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.User;

/**
 * User service.
 */
@Service
public class UserService extends BaseCrudService<UserRequest, UserResponse, User, String> {
    
    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     */
    public UserService(JpaRepository<User, String> jpaRepository) {
        super(jpaRepository);
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return EventResponse response
     */
    @Override
    protected UserResponse mapToResponse(User entity) {
        return new UserResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Event entity
     */
    @Override
    protected User mapToEntity(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }
}

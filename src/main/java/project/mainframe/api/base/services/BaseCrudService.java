package project.mainframe.api.base.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Base CRUD service.
 * 
 * @param <Request> Request
 * @param <Response> Response
 * @param <E> E
 * @param <ID> ID
 */
public abstract class BaseCrudService<Request, Response, E, ID> {

    /**
     * The repository to use for CRUD operations.
     */
    protected JpaRepository<E, ID> jpaRepository;

    /**
     * Constructor.
     * 
     * @param jpaRepository<Request, Response, E, ID> jpaRepository
     */
    public BaseCrudService(JpaRepository<E, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Finds all entities.
     * 
     * @param request
     * @return List<Response>
     * @throws ResponseStatusException
     */
    public List<Response> findAll() throws ResponseStatusException {
        List<E> entities = jpaRepository.findAll();
        
        return entities
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList()); 
    }

    /**
     * Finds an entity by id.
     * 
     * @param id
     * @return Response
     * @throws ResponseStatusException
     */
    public Response findById(ID id) throws ResponseStatusException {
        E entity = jpaRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }

        return mapToResponse(entity);
    }

    /**
     * Creates an entity.
     * 
     * @param request
     * @return Response
     * @throws ResponseStatusException
     */
    public Response create(Request request) throws ResponseStatusException {
        E entity = mapToEntity(request);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity could not be created");
        }

        E createdEntity = jpaRepository.save(entity);

        if (createdEntity == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity could not be created");
        }

        return mapToResponse(createdEntity);
    }

    /**
     * Updates an entity.
     * 
     * @param id
     * @param request
     * @return Response
     * @throws ResponseStatusException
     */
    public Response update(ID id, Request request) throws ResponseStatusException {
        E entity = jpaRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }

        E updatedEntity = jpaRepository.save(mapToEntity(request));

        if (updatedEntity == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity could not be updated");
        }

        return mapToResponse(updatedEntity);
    }

    /**
     * Deletes an entity.
     * 
     * @param id
     * @return Response
     * @throws ResponseStatusException
     */
    public boolean delete(ID id) throws ResponseStatusException {
        E entity = jpaRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }

        jpaRepository.delete(entity);

        return true;
    }

    /**
     * Gets the an associated entity by id.
     * 
     * @param Object primaryKey
     * @return E
     */
    protected <T, PK> T getAssociatedEntity(JpaRepository<T, PK> repository, PK primaryKey) {
        return repository
            .findById(primaryKey)
            .orElse(null);
    }

    /**
     * Gets associated entities by id.
     * 
     * @param JpaRepository repository
     * @param List primaryKeys
     * @return List
     */
    protected <T, PK> List<T> getAssociatedEntities(JpaRepository<T, PK> repository, List<PK> primaryKeys) {
        return repository.findAllById(primaryKeys);
    }

    /**
     * Maps an entity to a response.
     * 
     * @param E entity
     */
    protected abstract Response mapToResponse(E entity);

    /**
     * Maps a request to an entity.
     * 
     * @param Request request
     */
    protected abstract E mapToEntity(Request request);
}

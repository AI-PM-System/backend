package project.mainframe.api.base.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import project.mainframe.api.base.services.BaseCrudService;

public class BaseCrudController<Request, Response, E, ID> {
    
    /*
     * The generic service.
     */
    protected BaseCrudService<Request, Response, E, ID> baseCrudService;

    /*
     * Constructor.
     */
    public BaseCrudController(BaseCrudService<Request, Response, E, ID> baseCrudService) {
        this.baseCrudService = baseCrudService;
    }

    /*
     * Finds all entities.
     * 
     * @return List<Response>
     */
    @GetMapping
    public List<Response> findAll() {
        return baseCrudService.findAll();
    }

    /*
     * Finds an entity by id.
     * 
     * @param id
     * @return Response
     */
    @GetMapping("/{id}")
    public Response findById(@PathVariable ID id) {
        return baseCrudService.findById(id);
    }

    /*
     * Creates an entity.
     * 
     * @param request
     * @return Response
     */
    @PostMapping
    public Response create(@RequestBody Request request) {
        return baseCrudService.create(request);
    }

    /*
     * Updates an entity.
     * 
     * @param request
     * @return Response
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable ID id, @RequestBody Request request) {
        return baseCrudService.update(id, request);
    }

    /*
     * Deletes an entity.
     * 
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        baseCrudService.delete(id);
    }
}

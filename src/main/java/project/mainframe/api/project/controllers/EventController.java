package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.event.EventRequest;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.entities.Event;

/**
 * Event controller.
 */
@RestController
@RequestMapping("/api/v1/user/events")
public class EventController extends BaseCrudController<EventRequest, EventResponse, Event, Long> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public EventController(BaseCrudService<EventRequest, EventResponse, Event, Long> baseCrudService) {
        super(baseCrudService);
    }
}

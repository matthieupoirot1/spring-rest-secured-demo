package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Event;
import com.comvous.demo.data.repositories.EventRepository;
import com.comvous.demo.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController extends BaseSecuredCrudController<EventService, EventRepository, Event, Event> {

    @Autowired
    public EventController(EventService eventService, ModelMapper modelMapper) {
        super(eventService, modelMapper, Event.class);
    }
}

package com.comvous.demo.services;

import com.comvous.demo.data.models.Event;
import com.comvous.demo.data.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService extends BaseCrudService<EventRepository, Event> {

    @Autowired
    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }

    public Event getEventByName(String name){
        return this.linkedRepository.findByName(name);
    }

    public Event getEventByNameAndDate(String name, String date){
        return this.linkedRepository.findByNameContainsIgnoreCaseAndDateContaining(name, date);
    }
}

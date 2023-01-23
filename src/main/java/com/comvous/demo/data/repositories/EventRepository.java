package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findByName(String name);
    public Event findByNameContainsIgnoreCaseAndDateContaining(String name, String date);
}

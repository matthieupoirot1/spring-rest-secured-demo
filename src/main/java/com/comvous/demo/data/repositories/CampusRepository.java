package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Campus findCampusByNameContainingIgnoreCase(String name);

}

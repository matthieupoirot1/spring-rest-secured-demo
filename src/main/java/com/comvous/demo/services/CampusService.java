package com.comvous.demo.services;

import com.comvous.demo.data.models.Campus;
import com.comvous.demo.data.repositories.CampusRepository;
import org.springframework.stereotype.Service;

@Service
public class CampusService extends BaseCrudService<CampusRepository, Campus> {

    public CampusService(CampusRepository repository) {
        super(repository);
    }

    public Campus findByName(String name) {
        return super.linkedRepository.findCampusByNameContainingIgnoreCase(name);
    }
}

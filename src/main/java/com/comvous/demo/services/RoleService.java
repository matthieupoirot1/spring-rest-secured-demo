package com.comvous.demo.services;

import com.comvous.demo.data.models.Role;
import com.comvous.demo.data.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseCrudService<RoleRepository, Role> {

    public RoleService(RoleRepository repository) {
        super(repository);
    }

    public Role findByName(String name) {
        return linkedRepository.findByName(name);
    }
}

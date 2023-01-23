package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Role;
import com.comvous.demo.data.repositories.RoleRepository;
import com.comvous.demo.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseSecuredCrudController<RoleService, RoleRepository, Role, Role> {

    public RoleController(RoleService service, ModelMapper modelMapper) {
        super(service, modelMapper, Role.class);
    }

    @GetMapping("/{name}")
    public Role getByName(@PathVariable String name) {
        return linkedService.findByName(name);
    }
}

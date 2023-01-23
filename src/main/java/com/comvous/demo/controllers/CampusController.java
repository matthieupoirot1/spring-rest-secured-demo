package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Campus;
import com.comvous.demo.data.models.projections.CampusDTO;
import com.comvous.demo.data.repositories.CampusRepository;
import com.comvous.demo.services.CampusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campus")
public class CampusController extends BaseSecuredCrudController<CampusService, CampusRepository, Campus, CampusDTO> {

    @Autowired
    public CampusController(CampusService campusService, ModelMapper modelMapper) {
        super(campusService, modelMapper, CampusDTO.class);
    }

    @GetMapping("/name/{name}")
    public Campus getCampusByName(@PathVariable String name) {
        return this.linkedService.findByName(name);
    }
}

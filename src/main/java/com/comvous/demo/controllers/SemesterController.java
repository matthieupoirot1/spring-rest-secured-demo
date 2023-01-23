package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Semester;
import com.comvous.demo.data.models.projections.SemesterDTO;
import com.comvous.demo.data.models.projections.SemesterWithStudentsDTO;
import com.comvous.demo.data.models.projections.UserDTO;
import com.comvous.demo.data.repositories.SemesterRepository;
import com.comvous.demo.services.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController extends BaseSecuredCrudController<SemesterService, SemesterRepository, Semester, SemesterDTO> {

    public SemesterController(SemesterService semesterService, ModelMapper modelMapper) {
        super(semesterService, modelMapper, SemesterDTO.class);
    }

    @GetMapping("/name/{name}")
    public Semester getSemesterByName(@PathVariable String name) {
        return this.linkedService.findByName(name);
    }

    @PostMapping("/name/{name}/addStudents")
    public Semester addStudents(@PathVariable String name, @RequestBody List<Long> studentsId){
        return this.linkedService.updateStudents(name, studentsId);
    }

    @GetMapping("/{id}/students")
    public List<UserDTO> getStudents(@PathVariable Long id){
        return this.linkedService.getStudents(id).stream().map(u -> modelMapper.map(u, UserDTO.class)).toList();
    }

    @GetMapping("/allWithStudents")
    public List<SemesterWithStudentsDTO> getAllWithStudents(){
        return this.linkedService.getAll().stream().map(s -> modelMapper.map(s, SemesterWithStudentsDTO.class)).toList();
    }
}

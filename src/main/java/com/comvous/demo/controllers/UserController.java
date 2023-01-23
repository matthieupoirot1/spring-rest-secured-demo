package com.comvous.demo.controllers;

import com.comvous.demo.data.models.User;
import com.comvous.demo.data.models.projections.UserDTO;
import com.comvous.demo.data.models.projections.UserWithReportsDTO;
import com.comvous.demo.data.repositories.UserRepository;
import com.comvous.demo.security.facade.AuthenticationFacade;
import com.comvous.demo.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController extends BaseSecuredCrudController<UserService, UserRepository, User, UserDTO> {

    //logger
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        super(userService, modelMapper, UserDTO.class);
    }

    @PostMapping("/generate")
    public UserDTO generateUser(@RequestBody User user) {
        logger.info("Generating user");
        logger.info(user);
        return modelMapper.map(linkedService.generateUser(user), UserDTO.class);
    }

    @GetMapping("/allTeachers")
    public List<UserDTO> getAllTeachers() {
        return linkedService.getAllTeachers().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @GetMapping("/semester/{name}")
    public List<UserDTO> getBySemesterName(@PathVariable String name){
        return linkedService.getUsersBySemesterName(name).stream().map((user)-> modelMapper.map(user, UserDTO.class)).toList();
    }

    @GetMapping("/getUsersWithReportsByProjectId/{id}")
    public List<UserWithReportsDTO> getUsersWithReportsByProjectId(@PathVariable Long id){
        return linkedService.getUsersWithReportsByProjectId(id).stream().map((user)-> modelMapper.map(user, UserWithReportsDTO.class)).toList();
    }

}

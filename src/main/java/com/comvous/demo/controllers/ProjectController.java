package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Project;
import com.comvous.demo.data.models.projections.*;
import com.comvous.demo.data.repositories.ProjectRepository;
import com.comvous.demo.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseSecuredCrudController<ProjectService, ProjectRepository, Project, ProjectDTO> {

    ProjectService projectService;
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    public ProjectController(ProjectService projectService, ModelMapper modelMapper){
        super(projectService, modelMapper, ProjectDTO.class);
        this.projectService = projectService;
    }

    @Operation(summary = "Add a member to a project", description = "Add a member to a project by providing the project id and the member id", security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/project/{projectId}/addMember/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Project addMember(@PathVariable Long projectId, @PathVariable Long memberId){
        return projectService.addMember(projectId, memberId);
    }

    @Operation(summary = "Delete a member from a project", description = "Delete a member from a project by providing the project id and the member id", security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping("/project/{projectId}/deleteMember/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Project deleteMember(@PathVariable Long projectId, @PathVariable Long memberId){
        return projectService.deleteMember(projectId, memberId);
    }

    @PostMapping("/createWithSemesterIdAndMembersId")
    @PreAuthorize("hasRole('ADMIN')")
    public Project createWithSemesterAndMembers(@RequestBody ObjectNode json){
        ObjectMapper mapper = new ObjectMapper();
        Project project = mapper.convertValue(json.get("project"), Project.class);
        Long semesterId = json.get("semesterId").asLong();
        List<Long> members = null;
        try {
            members = mapper.readValue(json.get("membersId").toString(), new TypeReference<List<Long>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Project: " + project);
        logger.info("Semester: " + semesterId);
        logger.info("Members: " + members);
        logger.debug("Creating project with semester id and members id");
        return projectService.createWithSemesterAndMembers(project, semesterId, members);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectWithStakeHoldersDTO getByName(@PathVariable String name){
        Project project = projectService.getByName(name);
        return modelMapper.map(project, ProjectWithStakeHoldersDTO.class);
    }

    @PostMapping("/{projectId}/uploadPoster")
    public Project uploadPoster(@PathVariable Long projectId, @RequestParam("file") MultipartFile file){
        //todo verify upload path
        return projectService.uploadPoster(projectId, file);
    }

    @GetMapping("/name/{name}/withSemesterAndStakeHolders")
    public ProjectWithSemesterAndStakeHoldersDTO getProjectByNameWithStudents(@PathVariable String name) {
        return modelMapper.map(projectService.findByNameWithSemesterAndStakeHolders(name), ProjectWithSemesterAndStakeHoldersDTO.class);
    }

    @GetMapping("/name/{name}/setProjectManager/{cdpId}")
    public ProjectDTO setCDP(@PathVariable String name, @PathVariable Long cdpId) {
        return modelMapper.map(projectService.setProjectManager(name, cdpId), ProjectDTO.class);
    }

    @GetMapping("/name/{name}/setSupervisor/{supervisorId}")
    public ProjectDTO setSupervisor(@PathVariable String name, @PathVariable Long supervisorId) {
        return modelMapper.map(projectService.setSupervisor(name, supervisorId), ProjectDTO.class);
    }

    @PostMapping("/name/{name}/setSlogan")
    public ProjectDTO setSlogan(@PathVariable String name, @RequestBody String slogan) {
        return modelMapper.map(projectService.setSlogan(name, slogan), ProjectDTO.class);
    }

    @PostMapping("/name/{name}/setPitch")
    public ProjectDTO setPitch(@PathVariable String name, @RequestBody String pitch) {
        return modelMapper.map(projectService.setPitch(name, pitch), ProjectDTO.class);
    }

    @GetMapping("/getSupervisedProjects/{supervisorId}")
    public List<ProjectWithMembersAndWithReportsAndLinkedUserDTO> getSupervisedProjects(@PathVariable Long supervisorId) {
        return projectService.getSupervisedProjectsWithMembersAndReportsWithLinkedUser(supervisorId).stream().map((p) -> modelMapper.map(p, ProjectWithMembersAndWithReportsAndLinkedUserDTO.class)).toList();
    }
}

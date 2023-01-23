package com.comvous.demo.services;

import com.comvous.demo.data.models.Project;
import com.comvous.demo.data.models.Semester;
import com.comvous.demo.data.models.User;
import com.comvous.demo.data.repositories.ProjectRepository;
import com.comvous.demo.data.repositories.SemesterRepository;
import com.comvous.demo.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectService extends BaseCrudService<ProjectRepository, Project> {

    UserRepository userRepository;

    SemesterRepository semesterRepository;

    StorageService storageService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, SemesterRepository semesterRepository, StorageService storageService) {
        super(projectRepository);
        this.userRepository=userRepository;
        this.semesterRepository=semesterRepository;
        this.storageService=storageService;
    }

    public Project addMember(long projectId, long memberId){
        Project p = this.linkedRepository.findById(projectId).orElseThrow(
                () -> new NoSuchElementException("Can't find user with given ID : " + projectId)
        );
        User u = this.userRepository.findById(memberId).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given ID " + memberId)
        );
        p.getMembers().add(u);
        return this.linkedRepository.save(p);
    }

    public Project deleteMember(Long projectId, Long memberId) {
        Project p = this.linkedRepository.findById(projectId).orElseThrow(
                () -> new NoSuchElementException("Can't find user with given ID : " + projectId)
        );
        User u = this.userRepository.findById(memberId).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given ID " + memberId)
        );
        p.getMembers().remove(u);
        return this.linkedRepository.save(p);
    }

    public Project createWithSemesterAndMembers(Project project, Long semesterId, List<Long> membersId) {
        if(linkedRepository.findByNameLike(project.getName()) != null){
            throw new IllegalArgumentException("Project with given name already exists");
        }
        Semester s = this.semesterRepository.findById(semesterId).orElseThrow(
                () -> new NoSuchElementException("Can't find semester with given ID : " + semesterId)
        );
        for (Long id : membersId) {
            User u = this.userRepository.findById(id).orElseThrow(
                    () -> new NoSuchElementException("Can't find user with given ID : " + id)
            );
            project.addMember(u);
        }
        project.addSemester(s);
        return this.linkedRepository.save(project);
    }

    public Project getByName(String name) {
        return this.linkedRepository.findFirstByName(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
    }

    public Project uploadPoster(Long projectId, MultipartFile file) {
        Project project = this.linkedRepository.findById(projectId).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given ID : " + projectId)
        );
        try {
            storageService.store(file);

            project.setPosterPath(file.getOriginalFilename());
            project = this.linkedRepository.save(project);

            return project;
        } catch (Exception e) {
            throw new RuntimeException("Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
        }
    }

    public Project findByNameWithSemesterAndStakeHolders(String name) {
        return this.linkedRepository.findByNameWithSemesterAndStakeholders(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
    }

    public Project setProjectManager(String name, Long cdpId) {
        Project p = this.linkedRepository.findByNameWithSemesterAndStakeholders(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
        User cdp = this.userRepository.findById(cdpId).orElseThrow(
                () -> new NoSuchElementException("Can't find user with given ID : " + cdpId)
        );
        p.setProjectManager(cdp);
        return this.linkedRepository.save(p);
    }

    public Project setSupervisor(String name, Long supervisorId) {
        Project p = this.linkedRepository.findByNameWithSemesterAndStakeholders(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
        User supervisor = this.userRepository.findById(supervisorId).orElseThrow(
                () -> new NoSuchElementException("Can't find user with given ID : " + supervisorId)
        );
        p.setSupervisor(supervisor);
        return this.linkedRepository.save(p);
    }

    public Project setSlogan(String name, String slogan) {
        Project p = this.linkedRepository.findByNameWithSemesterAndStakeholders(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
        p.setSlogan(slogan);
        return this.linkedRepository.save(p);
    }

    public Project setPitch(String name, String pitch) {
        Project p = this.linkedRepository.findByNameWithSemesterAndStakeholders(name).orElseThrow(
                () -> new NoSuchElementException("Can't find project with given name : " + name)
        );
        p.setPitch(pitch);
        return this.linkedRepository.save(p);
    }

    public List<Project> getSupervisedProjectsWithMembersAndReportsWithLinkedUser(Long supervisorId) {
        return this.linkedRepository.getSupervisedProjectsWithMembersAndReportsWithLinkedUser(supervisorId);
    }

}

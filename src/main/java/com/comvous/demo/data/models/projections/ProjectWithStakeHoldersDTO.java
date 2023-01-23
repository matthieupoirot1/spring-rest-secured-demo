package com.comvous.demo.data.models.projections;

import java.sql.Date;
import java.util.List;

public class ProjectWithStakeHoldersDTO extends ProjectDTO {

    private List<UserDTO> members;
    private UserDTO supervisor;
    private UserDTO projectManager;

    public ProjectWithStakeHoldersDTO() {
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }

    public UserDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UserDTO supervisor) {
        this.supervisor = supervisor;
    }

    public UserDTO getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserDTO projectManager) {
        this.projectManager = projectManager;
    }
}

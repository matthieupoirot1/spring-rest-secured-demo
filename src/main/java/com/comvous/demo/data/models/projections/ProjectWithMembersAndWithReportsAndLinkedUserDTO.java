package com.comvous.demo.data.models.projections;

import java.util.List;

public class ProjectWithMembersAndWithReportsAndLinkedUserDTO extends ProjectDTO{
    private List<ReportWithUserDTO> reports;
    private List<UserDTO> members;

    public ProjectWithMembersAndWithReportsAndLinkedUserDTO() {
    }

    public List<ReportWithUserDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportWithUserDTO> reports) {
        this.reports = reports;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}

package com.comvous.demo.data.models.projections;

public class ReportWithUserDTO extends ReportDTO{

    private UserDTO user;

    public ReportWithUserDTO() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

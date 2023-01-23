package com.comvous.demo.data.models.projections;

import java.util.List;

public class UserWithReportsDTO extends UserDTO {
    private List<ReportDTO> reports;

    public UserWithReportsDTO() {
    }

    public List<ReportDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportDTO> reports) {
        this.reports = reports;
    }
}

package com.comvous.demo.data.models.projections;

import java.time.LocalDate;

public class ReportDTO {

    private long id;
    private String text;
    private String remarks;
    private Boolean isTeamReport;
    private LocalDate dueDate;
    private LocalDate completionDate;
    private boolean validated = false;

    public ReportDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getTeamReport() {
        return isTeamReport;
    }

    public void setTeamReport(Boolean teamReport) {
        isTeamReport = teamReport;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}

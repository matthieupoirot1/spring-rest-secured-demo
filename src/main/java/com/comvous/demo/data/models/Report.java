package com.comvous.demo.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reports")
public class Report {
    private long id;
    private String text;
    private String remarks;
    private Boolean isTeamReport;
    private LocalDate dueDate;
    private LocalDate completionDate;
    private boolean validated = false;
    private User user;
    private Project project;

    public Report() {
    }

    public Report(String text, Boolean isTeamReport, User user, Project project) {
        this.text = text;
        this.isTeamReport = isTeamReport;
        this.user = user;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name="remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name="is_team_report")
    public Boolean getIsTeamReport() {
        return isTeamReport;
    }

    public void setIsTeamReport(Boolean isTeamReport) {
        this.isTeamReport = isTeamReport;
    }

    @Column(name="date", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate date) {
        this.dueDate = date;
    }

    @Column(name="completion_date", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    @Column(name="validated")
    public boolean getValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIdentityReference(alwaysAsId = true)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

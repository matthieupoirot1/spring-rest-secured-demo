package com.comvous.demo.data.models.projections;

public class ProjectWithSemesterDTO extends ProjectDTO {

    private SemesterDTO semester;

    public ProjectWithSemesterDTO() {
    }

    public SemesterDTO getSemester() {
        return semester;
    }

    public void setSemester(SemesterDTO semester) {
        this.semester = semester;
    }
}

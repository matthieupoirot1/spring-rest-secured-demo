package com.comvous.demo.data.models.projections;

public class ProjectWithSemesterAndStakeHoldersDTO extends ProjectWithStakeHoldersDTO{

    private SemesterDTO semester;

    public ProjectWithSemesterAndStakeHoldersDTO() {
    }

    public SemesterDTO getSemester() {
        return semester;
    }

    public void setSemester(SemesterDTO semester) {
        this.semester = semester;
    }
}

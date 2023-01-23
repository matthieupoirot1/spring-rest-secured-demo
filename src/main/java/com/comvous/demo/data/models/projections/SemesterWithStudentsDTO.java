package com.comvous.demo.data.models.projections;

import java.sql.Date;
import java.util.List;

public class SemesterWithStudentsDTO extends SemesterDTO {

    private List<UserDTO> students;

    public SemesterWithStudentsDTO() {
    }

    public SemesterWithStudentsDTO(Long id, String name, String date, List<UserDTO> students) {
        super(id, name, date);
        this.students = students;
    }

    public List<UserDTO> getStudents() {
        return students;
    }

    public void setStudents(List<UserDTO> students) {
        this.students = students;
    }
}

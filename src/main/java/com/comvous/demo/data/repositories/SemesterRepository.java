package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Semester;
import com.comvous.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    public Semester findByNameContainsIgnoreCase(String name);

//    @Query("UPDATE Semester S set S.students=:userList WHERE S.name = :name  )
//    public void updateStudents(String name, List<User> userList);
}

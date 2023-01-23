package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.User;
import com.comvous.demo.data.models.projections.UserWithReportsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.semesters s WHERE s.name LIKE %:name%")
    public List<User> findAllBySemesterName(String name);

    public List<User> findAllByTeacher(boolean teacher);

}

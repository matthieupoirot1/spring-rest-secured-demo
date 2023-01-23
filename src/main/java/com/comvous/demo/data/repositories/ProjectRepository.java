package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Project;
import com.comvous.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.Optional;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN FETCH p.members WHERE p.name = ?1 ")
    public Optional<Project> findFirstByName(String name);

    @Query("SELECT p FROM Project p JOIN p.members m WHERE m.lastName LIKE :name OR m.firstName LIKE :name")
    public Streamable<Project> findProjectsByMembersContains(String name);

    @Query("SELECT p FROM Project p JOIN FETCH p.semester s JOIN FETCH p.members WHERE p.name = :name")
    Optional<Project> findByNameWithSemesterAndStakeholders(String name);

    Project findByNameLike(String name);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.members m LEFT JOIN FETCH p.reports r LEFT JOIN FETCH r.user WHERE p.supervisor.id=:id")
    List<Project> getSupervisedProjectsWithMembersAndReportsWithLinkedUser(Long id);
}

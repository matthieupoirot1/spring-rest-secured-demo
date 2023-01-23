package com.comvous.demo.data.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "email")
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isTeacher;
    private Collection<Role> roles = new ArrayList<>();
    private Set<Project> projects = new HashSet<>();
    private Set<Report> reports = new HashSet<>();
    private Set<Project> managedProjects = new HashSet<>();
    private Set<Project> supervisedProjects = new HashSet<>();
    private Set<Semester> semesters = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, boolean isTeacher, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isTeacher = isTeacher;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    @NotBlank(message = "Le prénom est obligatoire.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    @NotBlank(message = "Le nom est obligatoire.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", unique = true)
    @NotBlank(message = "L'email est obligatoire.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="is_teacher")
    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    @Column(name = "password")
    @NotBlank(message = "Le mot de passe est obligatoire.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "appuser_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    @OneToMany(mappedBy = "projectManager", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    public Set<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(Set<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    public Set<Project> getSupervisedProjects() {
        return supervisedProjects;
    }

    public void setSupervisedProjects(Set<Project> supervisedProjects) {
        this.supervisedProjects = supervisedProjects;
    }

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'';
    }
}

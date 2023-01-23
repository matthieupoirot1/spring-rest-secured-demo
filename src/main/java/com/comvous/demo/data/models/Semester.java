package com.comvous.demo.data.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Semester")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Semester {

    private long id;
    private String name;
    private String date;
    private Set<User> students = new HashSet<>();
    private Promo promo;
    private Set<Event> events = new HashSet<>();
    private List<Project> semesterProjects = new ArrayList<>();

    public Semester() {
    }

    public Semester(String name, String date) {
        this.name = name;
        this.date = date;
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_semester",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> getStudents() {
        return students;
    }

    public void addStudent(User student) {
        this.getStudents().add(student);
        student.getSemesters().add(this);
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "promo_id")
    @JsonIdentityReference(alwaysAsId = true)
    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    @OneToMany(mappedBy = "semester", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @OneToMany(mappedBy = "semester", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    public List<Project> getSemesterProjects() {
        return semesterProjects;
    }

    public void setSemesterProjects(List<Project> semesterProjects) {
        this.semesterProjects = semesterProjects;
    }
}

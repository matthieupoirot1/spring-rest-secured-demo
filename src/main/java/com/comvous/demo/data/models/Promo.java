package com.comvous.demo.data.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promo")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Promo {

    private long id;
    private String name;
    private Campus campus;
    private Set<Semester> semesters = new HashSet<>();

    public Promo() {
    }

    public Promo(String name) {
        this.name = name;
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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    @JsonIdentityReference(alwaysAsId = true)
    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    @OneToMany(mappedBy = "promo", fetch = FetchType.LAZY)
    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }


}

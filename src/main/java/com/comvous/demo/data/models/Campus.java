package com.comvous.demo.data.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="campus")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Campus {
    private long id;
    private String name;
    private String prefix;
    private Set<Promo> promos = new HashSet<>();

    public Campus() {
    }

    public Campus(String name) {
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

    @Column(name = "prefix")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @OneToMany(mappedBy = "campus", fetch = FetchType.LAZY)
    public Set<Promo> getPromos() {
        return promos;
    }

    public void setPromos(Set<Promo> promos) {
        this.promos = promos;
    }
}

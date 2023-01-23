package com.comvous.demo.data.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    public static final String DEFAULT_ROLE = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
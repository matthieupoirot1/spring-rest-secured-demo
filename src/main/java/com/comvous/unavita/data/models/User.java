package com.comvous.unavita.data.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private String zip;
    private String phone;

    private Collection<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String address, String city, String zip, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="first_name")
    @NotBlank(message = "Le prénom est obligatoire.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name")
    @NotBlank(message = "Le nom est obligatoire.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="email", unique = true)
    @NotBlank(message = "L'email est obligatoire.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="password")
    @NotBlank(message = "Le mot de passe est obligatoire.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="address")
    @NotBlank(message = "L'adresse est obligatoire.")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="city")
    @NotBlank(message = "La ville est obligatoire.")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="zip")
    @NotBlank(message = "Le code postal est obligatoire.")
    @Pattern(regexp = "^\\d{5}$", message = "Veuillez entrer un code postal valide.")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name="phone")
    @NotBlank(message = "Le numéro de téléphone est obligatoire.")
    @Pattern(regexp = "^(06|07)\\d{8}$", message = "Veuillez entrer un numéro de téléphone valide.")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "appuser_roles",
            joinColumns = @JoinColumn(name = "appuser_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}

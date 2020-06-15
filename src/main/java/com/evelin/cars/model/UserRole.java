package com.evelin.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {

    private Long id;
    private Role role;


    public UserRole(){}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

package com.evelin.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role() {
    }

    public Role(String role) {
        this.name = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long roleId) {
        this.id = roleId;
    }


    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }
}

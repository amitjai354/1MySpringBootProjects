package com.example.BirthCertificate.model;

import jakarta.persistence.*;

@Entity
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String roleName;

    public RoleModel() {
    }

    public RoleModel(int id) {
        this.id = id;
    }

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }

    public RoleModel(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

package org.example.models;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int i;

    public User(){}
    public User(String name, String pass, String role) {
        this.name = name;
        this.pass = pass;
        this.role = role;
    }
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.role = "ROLE_USER";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column
    String name;
    @Column
    String pass;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column
    String role;
}

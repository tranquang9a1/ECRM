package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Entity(name = "tblUser")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45)
    private String name;
    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 45)
    private String password;


    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

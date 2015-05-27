package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Entity(name = "tblUser")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="RoleId", nullable = false)
    private
    Role role;
    @Basic
    @Column(name = "Username", nullable = false, insertable = true, updatable = true, length = 45)
    private String username;
    @Basic
    @Column(name = "Password", nullable = false, insertable = true, updatable = true, length = 45)
    private String password;
    @Basic
    @Column(name = "Fullname", nullable = false, insertable = true, updatable = true, length = 45)
    private String fullname;
    @Basic
    @Column(name = "Phone", nullable = false, insertable = true, updatable = true, length = 45)
    private String phone;
    @Basic
    @Column(name = "Status", nullable = false, insertable = true, updatable = true)
    private boolean status;



    public User() {
    }

    public User(String username, String password, String fullname, String phone, Role role, boolean status) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 5/25/2015.
 */
@Entity(name = "tblRole")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;


    @Basic
    @Column(name = "Name", nullable = true, insertable = false, updatable = true, length = 45)
    private String name;

    public Role(){

    }

    public Role(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

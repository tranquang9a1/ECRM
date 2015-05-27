package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 5/27/2015.
 */
@Entity(name = "tblEquipmentCategory")
public class EquipmentCategory {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @Basic
    @Column(name = "Name", nullable = false, insertable = false, updatable = true, length = 45)
    private String name;

    public EquipmentCategory() {
    }

    public EquipmentCategory(String name) {
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

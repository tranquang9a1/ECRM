package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by user on 8/19/2015.
 */
@Entity
@Table(name = "tblsystemconfiguration")
public class TblSystemConfiguration {
    private String key;
    private String value;
    private String description;

    public TblSystemConfiguration() {}

    public TblSystemConfiguration(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Id
    @Column(name = "ConfigKey")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

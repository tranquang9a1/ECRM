package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblRole", schema = "dbo", catalog = "ecrm")
public class TblRoleEntity {
    private int id;
    private String name;
    private Collection<TblUserEntity> tblUsersById;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblRoleEntity that = (TblRoleEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblRoleByRoleId")
    public Collection<TblUserEntity> getTblUsersById() {
        return tblUsersById;
    }

    public void setTblUsersById(Collection<TblUserEntity> tblUsersById) {
        this.tblUsersById = tblUsersById;
    }
}

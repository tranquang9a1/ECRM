package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblEquipmentCategory")
public class TblEquipmentCategoryEntity {
    private int id;
    private String name;
    private Integer usingTime;
    private Boolean isManaged;
    private String imageUrl;
    private Boolean isDelete;
    private Collection<TblEquipmentEntity> tblEquipmentsById;
    private Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityById;


    @Basic
    @Column(name = "IsDelete")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "IsManaged")
    public Boolean getIsManaged() {
        return isManaged;
    }

    public void setIsManaged(Boolean isManaged) {
        this.isManaged = isManaged;
    }



    @Basic
    @Column(name = "ImageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }




    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "UsingTime")
    public Integer getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Integer usingTime) {
        this.usingTime = usingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblEquipmentCategoryEntity that = (TblEquipmentCategoryEntity) o;

        if (id != that.id) return false;
        if (usingTime != that.usingTime) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (usingTime != null ? usingTime.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblEquipmentCategoryByCategoryId")
    public Collection<TblEquipmentEntity> getTblEquipmentsById() {
        return tblEquipmentsById;
    }

    public void setTblEquipmentsById(Collection<TblEquipmentEntity> tblEquipmentsById) {
        this.tblEquipmentsById = tblEquipmentsById;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblEquipmentCategoryEntityByEquipmentCategoryId")
    public Collection<TblEquipmentQuantityEntity> getTblEquipmentQuantityById() {
        return tblEquipmentQuantityById;
    }

    public void setTblEquipmentQuantityById(Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityById) {
        this.tblEquipmentQuantityById = tblEquipmentQuantityById;
    }

    public TblEquipmentCategoryEntity() {
    }

    public TblEquipmentCategoryEntity(String name, Integer usingTime, Boolean isManaged, String imageUrl, Boolean isDelete) {
        this.name = name;
        this.usingTime = usingTime;
        this.isManaged = isManaged;
        this.imageUrl = imageUrl;
        this.isDelete = isDelete;
    }
}

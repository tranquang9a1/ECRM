package com.ecrm.DTO;

import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/13/2015.
 */
public class DamagedRoomDTO {
    private int roomId;
    private String roomName;
    private String reporters;
    private Date reportDate;
    private int damagedLevel;
    private String evaluate;

    private List<String> suggestRooms;
    private TblRoomTypeEntity roomtype;
    private List<EquipmentDamaged> equipmentCategory;
    private List<TblEquipmentEntity> equipments;

    public DamagedRoomDTO(TblClassroomEntity room, TblReportEntity report, List<TblEquipmentEntity> equipments) {
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.reportDate = report.getCreateTime();
        this.evaluate = report.getEvaluate();
        this.equipments = equipments;

        this.equipmentCategory = new ArrayList<EquipmentDamaged>();
        int position = -1;
        String[] className = {"","projector","tivi","air-condition","fan","speaker","bulb","table-icon","chair"};

        for (TblEquipmentEntity item: equipments) {
            position = checkContain(item.getCategoryId());
            if (position != -1) {
                this.equipmentCategory.get(position).addSize();
            } else {
                this.equipmentCategory.add(new EquipmentDamaged(item.getCategoryId(), item.getTblEquipmentCategoryByCategoryId().getName(), className[item.getCategoryId()]));
            }
        }
     }

    private int checkContain(int cate) {

        for (int i = 0; i < this.equipmentCategory.size(); i++){
            if(this.equipmentCategory.get(i).getId() == cate) {
                return i;
            }
        }

        return -1;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getReporters() {
        return reporters;
    }

    public void setReporters(String reporters) {
        this.reporters = reporters;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(int damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public List<String> getSuggestRooms() {
        return suggestRooms;
    }

    public void setSuggestRooms(List<String> suggestRooms) {
        this.suggestRooms = suggestRooms;
    }

    public TblRoomTypeEntity getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(TblRoomTypeEntity roomtype) {
        this.roomtype = roomtype;
    }

    public List<TblEquipmentEntity> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<TblEquipmentEntity> equipments) {
        this.equipments = equipments;
    }

    public List<EquipmentDamaged> getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(List<EquipmentDamaged> equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }

    public class EquipmentDamaged {
        private int id;
        private int size;
        private String name;
        private String className;

        public EquipmentDamaged(int id, String name, String className){
            this.id = id;
            this.name = name;
            this.size = 1;
            this.className = className;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void addSize() {
            this.size++;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public String toString() {
            return " " + this.name;
        }
    }
}

package com.ecrm.DTO;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentCategoryDAOImpl;
import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.ReportDetailDAOImpl;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblReportDetailEntity;
import com.ecrm.Entity.TblReportEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */
public class ReportResponseObject {
    private int reportId;
    private String room;
    private String listEquipment = "";
    private String createDate;
    private int status;
    private int damagedLevel;

    public ReportResponseObject(TblReportEntity report, List<TblReportDetailEntity> reportDetails){
        this.reportId = report.getId();
        this.room = report.getTblClassroomByClassRoomId().getName();
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyy");
        this.createDate = formatter.format(report.getCreateTime().getTime());
        this.status = report.getStatus();
        this.damagedLevel = 0;

        List<Integer> equipmentName = new ArrayList<Integer>();
        boolean flag = false;
        for(int i = 0; i < reportDetails.size(); i++) {
            TblEquipmentCategoryEntity category = reportDetails.get(i).getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId();

            if(!equipmentName.contains(category.getId())){
                this.listEquipment += category.getName() + ", ";
                equipmentName.add(category.getId());
            }
        }
        this.listEquipment = this.listEquipment.substring(0, listEquipment.length()-2);
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoomId(String room) {
        this.room = room;
    }

    public String getListEquipment() {
        return listEquipment;
    }

    public void setListEquipment(String listEquipment) {
        this.listEquipment = listEquipment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(int damagedLevel) {
        this.damagedLevel = damagedLevel;
    }
}

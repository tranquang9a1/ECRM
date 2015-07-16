package com.fu.group10.capstone.apps.teachermobileapp.model;

import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class Equipment {
    private String equipmentName;
    private String timeRemain;
    private String company;
    private boolean isDamaged;
    private String damageLevel;
    private String evaluate;
    private String position;
    private boolean isReport;

    public Equipment(String equipmentName, String timeRemain, String company, boolean damaged) {
        this.equipmentName = equipmentName;
        this.timeRemain = timeRemain;
        this.company = company;
        this.isDamaged = damaged;
        this.damageLevel= "1";
        this.evaluate= "";
        this.position = "";
        this.isReport = false;
    }

    public Equipment(String equipmentName, String damageLevel, String evaluate, String position) {
        this.equipmentName = equipmentName;
        this.damageLevel = damageLevel;
        this.evaluate = evaluate;
        this.position = position;
        this.isDamaged = false;
        this.isReport = true;

    }

    public Equipment() {

    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(String timeRemain) {
        this.timeRemain = timeRemain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public String getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(String damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public boolean isReport() {
        return isReport;
    }

    public void setIsReport(boolean isReport) {
        this.isReport = isReport;
    }
}

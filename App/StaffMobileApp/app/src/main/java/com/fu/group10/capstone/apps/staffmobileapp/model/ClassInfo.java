package com.fu.group10.capstone.apps.staffmobileapp.model;

/**
 * Created by QuangTV on 8/4/2015.
 */
public class ClassInfo {
    private String name;
    private int damageLevel;

    public ClassInfo(String name, int damageLevel) {
        this.name = name;
        this.damageLevel = damageLevel;
    }

    public ClassInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }
}

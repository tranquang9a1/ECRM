package com.fu.group10.capstone.apps.staffmobileapp.model;

/**
 * Created by QuangTV on 8/4/2015.
 */
public class ClassInfo {
    private int id;
    private String name;
    private int damageLevel;

    public ClassInfo(int id, String name, int damageLevel) {
        this.id = id;
        this.name = name;
        this.damageLevel = damageLevel;
    }

    public ClassInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

package com.ecrm.DTO;

/**
 * Created by Htang on 8/11/2015.
 */
public class PriorityDTO {
    private Integer mark;
    private String name;

    public PriorityDTO() {
    }

    public PriorityDTO(int mark, String name) {
        this.mark = mark;
        this.name = name;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

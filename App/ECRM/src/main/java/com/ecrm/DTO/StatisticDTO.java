package com.ecrm.DTO;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/31/2015.
 */
public class StatisticDTO {
    private List<Integer> listNumber;
    private List<String> listMonth;

    public StatisticDTO() {
        this.listNumber = new ArrayList<Integer>();
        this.listMonth = new ArrayList<String>();
    }

    public StatisticDTO(List<Integer> listNumber, List<String> listMonth) {
        this.listNumber = listNumber;
        this.listMonth = listMonth;
    }

    public List<Integer> getListNumber() {
        return listNumber;
    }

    public void setListNumber(List<Integer> listNumber) {
        this.listNumber = listNumber;
    }

    public List<String> getListMonth() {
        return listMonth;
    }

    public void setListMonth(List<String> listMonth) {
        this.listMonth = listMonth;
    }


    public class StatisticRow {
        private long num;
        private String month;

        public StatisticRow() {}

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}

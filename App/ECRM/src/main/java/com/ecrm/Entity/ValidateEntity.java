package com.ecrm.Entity;

/**
 * Created by Htang on 7/11/2015.
 */
public class ValidateEntity {
    private String alert;
    private boolean status;

    public ValidateEntity() {
    }

    public ValidateEntity(String alert, boolean status) {
        this.alert = alert;
        this.status = status;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

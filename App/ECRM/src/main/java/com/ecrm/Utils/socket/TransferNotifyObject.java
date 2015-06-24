package com.ecrm.Utils.socket;

/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
public class TransferNotifyObject {

    private String message;

    public TransferNotifyObject() {
    }

    public TransferNotifyObject(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

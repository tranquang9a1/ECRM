package com.ecrm.Utils.socket;

/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
public class TransferNotifyObject {

    private String message;
    private String redirectLink;

    public TransferNotifyObject() {
    }

    public TransferNotifyObject(String message, String redirectLink) {
        super();
        this.message = message;
        this.redirectLink = redirectLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }
}

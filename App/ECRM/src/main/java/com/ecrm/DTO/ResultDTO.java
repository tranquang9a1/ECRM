package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/11/2015.
 */
public class ResultDTO {

    private int error_code;
    private String error;

    public ResultDTO(int error_code, String error) {
        this.error_code = error_code;
        this.error = error;
    }

    public ResultDTO() {
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

package com.fu.group10.capstone.apps.teachermobileapp.model;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class Result {
    private int error_code;
    private String error;

    public Result(int error_code, String error) {
        this.error_code = error_code;
        this.error = error;
    }

    public Result(){

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

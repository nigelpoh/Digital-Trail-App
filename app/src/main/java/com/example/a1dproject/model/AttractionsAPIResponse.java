package com.example.a1dproject.model;

public class AttractionsAPIResponse {
    private Info info;
    private Integer status;
    private String error_message;

    public void setInfo(Info info) {
        this.info = info;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setErrorMessage(String error_message) {
        this.error_message = error_message;
    }

    public Info getInfo() {
        return info;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return error_message;
    }
}

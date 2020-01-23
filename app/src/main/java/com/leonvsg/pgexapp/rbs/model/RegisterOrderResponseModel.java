package com.leonvsg.pgexapp.rbs.model;

public class RegisterOrderResponseModel {

    private String errorCode;
    private String errorMessage;
    private String orderId;
    private String formUrl;

    public RegisterOrderResponseModel(String errorCode, String errorMessage, String orderId, String formUrl) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.orderId = orderId;
        this.formUrl = formUrl;
    }

    public RegisterOrderResponseModel(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getFormUrl() {
        return formUrl;
    }

    @Override
    public String toString() {
        return "RegisterOrderResponseModel{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", orderId='" + orderId + '\'' +
                ", formUrl='" + formUrl + '\'' +
                '}';
    }
}

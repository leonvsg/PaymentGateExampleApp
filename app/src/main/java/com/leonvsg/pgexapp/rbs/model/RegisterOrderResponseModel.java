package com.leonvsg.pgexapp.rbs.model;

/*
Models

{
    "orderId": "348e786d-38cd-7018-be0b-27be00006aec",
    "formUrl": "https://web.rbsuat.com/ab/merchants/typical/payment_ru.html?mdOrder=348e786d-38cd-7018-be0b-27be00006aec"
}
{
    "errorCode": "4",
    "errorMessage": "URL возврата не может быть пуст"
}
 */

public class RegisterOrderResponseModel {

    private Integer errorCode;
    private String errorMessage;
    private String orderId;
    private String formUrl;

    public RegisterOrderResponseModel() { }

    public RegisterOrderResponseModel(String orderId, String formUrl) {
        this.orderId = orderId;
        this.formUrl = formUrl;
    }

    public RegisterOrderResponseModel(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
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

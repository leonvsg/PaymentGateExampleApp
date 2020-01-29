package com.leonvsg.pgexapp.rbs.model;

/*
Models

{
    "error":"Некорректный номер карты.",
    "errorCode":1,
    "errorMessage":"Некорректный номер карты."
}
{
    "info":"Ваш платёж обработан, происходит переадресация...",
    "errorCode":0,
    "acsUrl":"https://web.rbsuat.com/acs/auth/start.do",
    "paReq":"eJxVUttSgzAQ/RWG95IQoUJnG4eKt3GgreA49Y2G1DIjAUOwl6836cX",
    "termUrl":"https://web.rbsuat.com/ab/rest/finish3ds.do?lang=ru"
}
{
    "redirect":"https://google.com?orderId=348e786d-38cd-7018-be0b-27be00006aec&lang=ru",
    "info":"Ваш платёж обработан, происходит переадресация...",
    "errorCode":0
}
 */


public class PaymentOrderResponseModel {

    private Integer errorCode;
    private String error;
    private String errorMessage;
    private String info;
    private String redirect;
    private String termUrl;
    private String acsUrl;
    private String paReq;

    public PaymentOrderResponseModel() { }

    public PaymentOrderResponseModel(Integer errorCode, String error, String errorMessage, String info, String redirect,
                                     String termUrl, String acsUrl, String paReq) {
        this.errorCode = errorCode;
        this.error = error;
        this.errorMessage = errorMessage;
        this.info = info;
        this.redirect = redirect;
        this.termUrl = termUrl;
        this.acsUrl = acsUrl;
        this.paReq = paReq;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getTermUrl() {
        return termUrl;
    }

    public void setTermUrl(String termUrl) {
        this.termUrl = termUrl;
    }

    public String getAcsUrl() {
        return acsUrl;
    }

    public void setAcsUrl(String acsUrl) {
        this.acsUrl = acsUrl;
    }

    public String getPaReq() {
        return paReq;
    }

    public void setPaReq(String paReq) {
        this.paReq = paReq;
    }

    @Override
    public String toString() {
        return "PaymentOrderResponseModel{" +
                "errorCode='" + errorCode + '\'' +
                ", error='" + error + '\'' +
                ", info='" + info + '\'' +
                ", redirect='" + redirect + '\'' +
                ", termUrl='" + termUrl + '\'' +
                ", acsUrl='" + acsUrl + '\'' +
                ", paReq='" + paReq + '\'' +
                '}';
    }
}

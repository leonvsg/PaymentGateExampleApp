package com.leonvsg.pgexapp.rbs.model;

public class PaymentOrderResponseModel {

    private String errorCode;
    private String error;
    private String info;
    private String redirect;
    private String termUrl;
    private String acsUrl;
    private String paReq;

    public PaymentOrderResponseModel(String errorCode, String error, String info, String redirect,
                                     String termUrl, String acsUrl, String paReq) {
        this.errorCode = errorCode;
        this.error = error;
        this.info = info;
        this.redirect = redirect;
        this.termUrl = termUrl;
        this.acsUrl = acsUrl;
        this.paReq = paReq;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }

    public String getInfo() {
        return info;
    }

    public String getRedirect() {
        return redirect;
    }

    public String getTermUrl() {
        return termUrl;
    }

    public String getAcsUrl() {
        return acsUrl;
    }

    public String getPaReq() {
        return paReq;
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

package com.leonvsg.pgexapp.rbs.model;

public class PaymentOrderRequestModel {

    private String userName;
    private String password;
    private String MDORDER;
    private String seToken;
    private String TEXT;

    public PaymentOrderRequestModel(String userName, String password, String MDORDER, String seToken, String TEXT) {
        this.userName = userName;
        this.password = password;
        this.MDORDER = MDORDER;
        this.seToken = seToken;
        this.TEXT = TEXT;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMDORDER() {
        return MDORDER;
    }

    public String getSeToken() {
        return seToken;
    }

    public String getTEXT() {
        return TEXT;
    }

    @Override
    public String toString() {
        return "PaymentOrderRequestModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", MDORDER='" + MDORDER + '\'' +
                ", seToken='" + seToken + '\'' +
                ", TEXT='" + TEXT + '\'' +
                '}';
    }
}

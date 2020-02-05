package com.leonvsg.pgexapp.rbs.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class PaymentOrderRequestModel {

    @Getter private String userName;
    @Getter private String password;
    @Getter private String MDORDER;
    @Getter private String seToken;
    @Getter private String TEXT;

    public PaymentOrderRequestModel(String userName, String password, String MDORDER, String seToken, String TEXT) {
        this.userName = userName;
        this.password = password;
        this.MDORDER = MDORDER;
        this.seToken = seToken;
        this.TEXT = TEXT;
    }
}

package com.leonvsg.pgexapp.rbs.model;

import lombok.Data;

public @Data class PaymentOrderResponseModel {

    private Integer errorCode;
    private String error;
    private String errorMessage;
    private String info;
    private String redirect;
    private String termUrl;
    private String acsUrl;
    private String paReq;
}

package com.leonvsg.pgexapp.rbs.model;

import lombok.Data;

public @Data class RegisterOrderResponseModel {
    private Integer errorCode;
    private String errorMessage;
    private String orderId;
    private String formUrl;
}

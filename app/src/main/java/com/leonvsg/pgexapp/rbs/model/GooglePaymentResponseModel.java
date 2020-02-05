package com.leonvsg.pgexapp.rbs.model;

import lombok.Data;

public @Data
class GooglePaymentResponseModel {

    private boolean success;
    private Data data;
    private Error error;

    @lombok.Data
    public class Data {

        private String orderId;
        private String acsUrl;
        private String paReq;
        private String termUrl;
    }

    @lombok.Data
    public class Error {

        private Integer code;
        private String description;
        private String message;
    }
}

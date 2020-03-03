package com.leonvsg.pgexapp.rbs.model;

import lombok.Data;

public @Data
class GooglePaymentResponseModel {

    private boolean success;
    private Data data;
    private Error error;

    @Override
    public String toString() {
        return "GooglePaymentResponseModel{" +
                "success=" + success +
                ", data=" + data +
                ", error=" + error +
                '}';
    }

    @lombok.Data
    public class Data {

        private String orderId;
        private String acsUrl;
        private String paReq;
        private String termUrl;

        @Override
        public String toString() {
            return "Data{" +
                    "orderId='" + orderId + '\'' +
                    ", acsUrl='" + acsUrl + '\'' +
                    ", paReq='" + paReq + '\'' +
                    ", termUrl='" + termUrl + '\'' +
                    '}';
        }
    }

    @lombok.Data
    public class Error {

        private Integer code;
        private String description;
        private String message;

        @Override
        public String toString() {
            return "Error{" +
                    "code=" + code +
                    ", description='" + description + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}

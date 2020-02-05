package com.leonvsg.pgexapp.rbs.model;

import java.util.List;

import lombok.Data;

public @Data class GetOrderStatusExtendedResponseModel {

    private Integer errorCode;
    private String errorMessage;
    private String orderNumber;
    private Integer orderStatus;
    private Integer actionCode;
    private String actionCodeDescription;
    private Integer amount;
    private String currency;
    private Long date;
    private Long depositedDate;
    private String orderDescription;
    private String ip;
    private List<Param> merchantOrderParams;
    private List<Param> attributes;
    private CardAuthInfo cardAuthInfo;
    private BindingInfo bindingInfo;
    private Long authDateTime;
    private String terminalId;
    private String authRefNum;
    private PaymentAmountInfo paymentAmountInfo;
    private BankInfo bankInfo;
    private PayerData payerData;
    private boolean chargeback;
    private String paymentWay;
    private Long refundedDate;

    public @Data class Param {

        private String name;
        private String value;
    }

    public @Data class CardAuthInfo {

        private String maskedPan;
        private String expiration;
        private String cardholderName;
        private String approvalCode;
        private String paymentSystem;
        private String product;
        private SecureAuthInfo secureAuthInfo;
        private String pan;

        public @Data class SecureAuthInfo {

            private Integer eci;
            private ThreeDSInfo threeDSInfo;

            public @Data class ThreeDSInfo {

                private String xid;
                private String cavv;
            }
        }
    }

    public @Data class BindingInfo {

        private String clientId;
        private String bindingId;
    }

    public @Data class PaymentAmountInfo{

        private String paymentState;
        private Integer approvedAmount;
        private Integer depositedAmount;
        private Integer refundedAmount;
        private Integer feeAmount;
    }

    public @Data class BankInfo {

        private String bankName;
        private String bankCountryCode;
        private String bankCountryName;
    }

    public @Data class PayerData {

        private String phone;
        private String email;
    }
}

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

    @Override
    public String toString() {
        return "GetOrderStatusExtendedResponseModel{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus=" + orderStatus +
                ", actionCode=" + actionCode +
                ", actionCodeDescription='" + actionCodeDescription + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", date=" + date +
                ", depositedDate=" + depositedDate +
                ", orderDescription='" + orderDescription + '\'' +
                ", ip='" + ip + '\'' +
                ", merchantOrderParams=" + merchantOrderParams +
                ", attributes=" + attributes +
                ", cardAuthInfo=" + cardAuthInfo +
                ", bindingInfo=" + bindingInfo +
                ", authDateTime=" + authDateTime +
                ", terminalId='" + terminalId + '\'' +
                ", authRefNum='" + authRefNum + '\'' +
                ", paymentAmountInfo=" + paymentAmountInfo +
                ", bankInfo=" + bankInfo +
                ", payerData=" + payerData +
                ", chargeback=" + chargeback +
                ", paymentWay='" + paymentWay + '\'' +
                ", refundedDate=" + refundedDate +
                '}';
    }

    public @Data class Param {

        private String name;
        private String value;

        @Override
        public String toString() {
            return "Param{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
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

        @Override
        public String toString() {
            return "CardAuthInfo{" +
                    "maskedPan='" + maskedPan + '\'' +
                    ", expiration='" + expiration + '\'' +
                    ", cardholderName='" + cardholderName + '\'' +
                    ", approvalCode='" + approvalCode + '\'' +
                    ", paymentSystem='" + paymentSystem + '\'' +
                    ", product='" + product + '\'' +
                    ", secureAuthInfo=" + secureAuthInfo +
                    ", pan='" + pan + '\'' +
                    '}';
        }

        public @Data class SecureAuthInfo {

            private Integer eci;
            private ThreeDSInfo threeDSInfo;

            @Override
            public String toString() {
                return "SecureAuthInfo{" +
                        "eci=" + eci +
                        ", threeDSInfo=" + threeDSInfo +
                        '}';
            }

            public @Data class ThreeDSInfo {

                private String xid;
                private String cavv;

                @Override
                public String toString() {
                    return "ThreeDSInfo{" +
                            "xid='" + xid + '\'' +
                            ", cavv='" + cavv + '\'' +
                            '}';
                }
            }
        }
    }

    public @Data class BindingInfo {

        private String clientId;
        private String bindingId;

        @Override
        public String toString() {
            return "BindingInfo{" +
                    "clientId='" + clientId + '\'' +
                    ", bindingId='" + bindingId + '\'' +
                    '}';
        }
    }

    public @Data class PaymentAmountInfo{

        private String paymentState;
        private Integer approvedAmount;
        private Integer depositedAmount;
        private Integer refundedAmount;
        private Integer feeAmount;

        @Override
        public String toString() {
            return "PaymentAmountInfo{" +
                    "paymentState='" + paymentState + '\'' +
                    ", approvedAmount=" + approvedAmount +
                    ", depositedAmount=" + depositedAmount +
                    ", refundedAmount=" + refundedAmount +
                    ", feeAmount=" + feeAmount +
                    '}';
        }
    }

    public @Data class BankInfo {

        private String bankName;
        private String bankCountryCode;
        private String bankCountryName;

        @Override
        public String toString() {
            return "BankInfo{" +
                    "bankName='" + bankName + '\'' +
                    ", bankCountryCode='" + bankCountryCode + '\'' +
                    ", bankCountryName='" + bankCountryName + '\'' +
                    '}';
        }
    }

    public @Data class PayerData {

        private String phone;
        private String email;

        @Override
        public String toString() {
            return "PayerData{" +
                    "phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}

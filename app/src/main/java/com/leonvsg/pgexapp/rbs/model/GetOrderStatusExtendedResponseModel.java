package com.leonvsg.pgexapp.rbs.model;

import java.util.List;

/*
Models
{
    "errorCode": "1",
    "errorMessage": "Ожидается [orderId] или [orderNumber]",
    "merchantOrderParams": [],
    "attributes": []
}
{
    "errorCode": "0",
    "errorMessage": "Успешно",
    "orderNumber": "1580818980",
    "orderStatus": 2,
    "actionCode": 0,
    "actionCodeDescription": "",
    "amount": 1000,
    "currency": "810",
    "date": 1580818979846,
    "depositedDate": 1580819041664,
    "orderDescription": "",
    "ip": "81.18.144.51",
    "merchantOrderParams": [
        {
            "name": "interestRate",
            "value": "100"
        },
        {
            "name": "mdOrder",
            "value": "4970958d-d05e-794d-aaa2-ebdf00006aec"
        }
    ],
    "attributes": [
        {
            "name": "mdOrder",
            "value": "4970958d-d05e-794d-aaa2-ebdf00006aec"
        }
    ],
    "cardAuthInfo": {
        "maskedPan": "524846**7756",
        "expiration": "202112",
        "cardholderName": "fdasgbdf sdgfsd",
        "approvalCode": "123456",
        "paymentSystem": "MASTERCARD",
        "product": "DEBIT",
        "secureAuthInfo": {
            "eci": 5,
            "threeDSInfo": {
                "xid": "Njk1MjQ4MjE1ODA4OTI5MzIyODM=",
                "cavv": "AAABACEmBRWAiSkyYFAAAAAAAAA="
            }
        },
        "pan": "524846**7756"
    },
    "bindingInfo": {
        "clientId": "jgtiv",
        "bindingId": "7d3c4b9a-3610-74d9-808d-422b00006aec"
    },
    "authDateTime": 1580819041633,
    "terminalId": "456123",
    "authRefNum": "039153527525",
    "paymentAmountInfo": {
        "paymentState": "DEPOSITED",
        "approvedAmount": 1000,
        "depositedAmount": 1000,
        "refundedAmount": 0,
        "feeAmount": 0
    },
    "bankInfo": {
        "bankName": "BANK ZENIT",
        "bankCountryCode": "RU",
        "bankCountryName": "Россия"
    },
    "payerData": {
        "phone": "9876543210",
        "email": "afasdf@afasd.tr"
    },
    "chargeback": false,
    "paymentWay": "CARD",
    "refundedDate": 1580819041633
}
 */
public class GetOrderStatusExtendedResponseModel {

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

    public GetOrderStatusExtendedResponseModel() { }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionCodeDescription() {
        return actionCodeDescription;
    }

    public void setActionCodeDescription(String actionCodeDescription) {
        this.actionCodeDescription = actionCodeDescription;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDepositedDate() {
        return depositedDate;
    }

    public void setDepositedDate(Long depositedDate) {
        this.depositedDate = depositedDate;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Param> getMerchantOrderParams() {
        return merchantOrderParams;
    }

    public void setMerchantOrderParams(List<Param> merchantOrderParams) {
        this.merchantOrderParams = merchantOrderParams;
    }

    public List<Param> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Param> attributes) {
        this.attributes = attributes;
    }

    public CardAuthInfo getCardAuthInfo() {
        return cardAuthInfo;
    }

    public void setCardAuthInfo(CardAuthInfo cardAuthInfo) {
        this.cardAuthInfo = cardAuthInfo;
    }

    public BindingInfo getBindingInfo() {
        return bindingInfo;
    }

    public void setBindingInfo(BindingInfo bindingInfo) {
        this.bindingInfo = bindingInfo;
    }

    public Long getAuthDateTime() {
        return authDateTime;
    }

    public void setAuthDateTime(Long authDateTime) {
        this.authDateTime = authDateTime;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAuthRefNum() {
        return authRefNum;
    }

    public void setAuthRefNum(String authRefNum) {
        this.authRefNum = authRefNum;
    }

    public PaymentAmountInfo getPaymentAmountInfo() {
        return paymentAmountInfo;
    }

    public void setPaymentAmountInfo(PaymentAmountInfo paymentAmountInfo) {
        this.paymentAmountInfo = paymentAmountInfo;
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }

    public PayerData getPayerData() {
        return payerData;
    }

    public void setPayerData(PayerData payerData) {
        this.payerData = payerData;
    }

    public boolean isChargeback() {
        return chargeback;
    }

    public void setChargeback(boolean chargeback) {
        this.chargeback = chargeback;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Long getRefundedDate() {
        return refundedDate;
    }

    public void setRefundedDate(Long refundedDate) {
        this.refundedDate = refundedDate;
    }

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

    public class Param {
        /*
        Model
        {
            "name": "interestRate",
            "value": "100"
        }
        */
        private String name;
        private String value;

        public Param() { }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public class CardAuthInfo {
        /*
        Model
        {
            "maskedPan": "524846**7756",
            "expiration": "202112",
            "cardholderName": "fdasgbdf sdgfsd",
            "approvalCode": "123456",
            "paymentSystem": "MASTERCARD",
            "product": "DEBIT",
            "secureAuthInfo": {
                "eci": 5,
                "threeDSInfo": {
                    "xid": "Njk1MjQ4MjE1ODA4OTI5MzIyODM=",
                    "cavv": "AAABACEmBRWAiSkyYFAAAAAAAAA="
                }
            },
            "pan": "524846**7756"
        }
        */

        private String maskedPan;
        private String expiration;
        private String cardholderName;
        private String approvalCode;
        private String paymentSystem;
        private String product;
        private SecureAuthInfo secureAuthInfo;
        private String pan;

        public CardAuthInfo() { }

        public String getMaskedPan() {
            return maskedPan;
        }

        public void setMaskedPan(String maskedPan) {
            this.maskedPan = maskedPan;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getCardholderName() {
            return cardholderName;
        }

        public void setCardholderName(String cardholderName) {
            this.cardholderName = cardholderName;
        }

        public String getApprovalCode() {
            return approvalCode;
        }

        public void setApprovalCode(String approvalCode) {
            this.approvalCode = approvalCode;
        }

        public String getPaymentSystem() {
            return paymentSystem;
        }

        public void setPaymentSystem(String paymentSystem) {
            this.paymentSystem = paymentSystem;
        }

        public String getPan() {
            return pan;
        }

        public void setPan(String pan) {
            this.pan = pan;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public SecureAuthInfo getSecureAuthInfo() {
            return secureAuthInfo;
        }

        public void setSecureAuthInfo(SecureAuthInfo secureAuthInfo) {
            this.secureAuthInfo = secureAuthInfo;
        }

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

        public class SecureAuthInfo {
            /*
            Model
            {
                "eci": 5,
                "threeDSInfo": {
                    "xid": "Njk1MjQ4MjE1ODA4OTI5MzIyODM=",
                    "cavv": "AAABACEmBRWAiSkyYFAAAAAAAAA="
                }
            },
            */

            private Integer eci;
            private ThreeDSInfo threeDSInfo;

            public SecureAuthInfo() { }

            public Integer getEci() {
                return eci;
            }

            public void setEci(Integer eci) {
                this.eci = eci;
            }

            public ThreeDSInfo getThreeDSInfo() {
                return threeDSInfo;
            }

            public void setThreeDSInfo(ThreeDSInfo threeDSInfo) {
                this.threeDSInfo = threeDSInfo;
            }

            @Override
            public String toString() {
                return "SecureAuthInfo{" +
                        "eci=" + eci +
                        ", threeDSInfo=" + threeDSInfo +
                        '}';
            }

            public class ThreeDSInfo {
                /*
                Model
                {
                    "xid": "Njk1MjQ4MjE1ODA4OTI5MzIyODM=",
                    "cavv": "AAABACEmBRWAiSkyYFAAAAAAAAA="
                }
                 */

                private String xid;
                private String cavv;

                public ThreeDSInfo() { }

                public String getXid() {
                    return xid;
                }

                public void setXid(String xid) {
                    this.xid = xid;
                }

                public String getCavv() {
                    return cavv;
                }

                public void setCavv(String cavv) {
                    this.cavv = cavv;
                }

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

    private class BindingInfo {
        /*
        Model
        {
            "clientId": "jgtiv",
            "bindingId": "7d3c4b9a-3610-74d9-808d-422b00006aec"
        }
         */
        private String clientId;
        private String bindingId;

        public BindingInfo() { }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getBindingId() {
            return bindingId;
        }

        public void setBindingId(String bindingId) {
            this.bindingId = bindingId;
        }

        @Override
        public String toString() {
            return "BindingInfo{" +
                    "clientId='" + clientId + '\'' +
                    ", bindingId='" + bindingId + '\'' +
                    '}';
        }
    }

    private class PaymentAmountInfo{
        /*
        Model
        {
            "paymentState": "DEPOSITED",
            "approvedAmount": 1000,
            "depositedAmount": 1000,
            "refundedAmount": 0,
            "feeAmount": 0
        }
        */

        private String paymentState;
        private Integer approvedAmount;
        private Integer depositedAmount;
        private Integer refundedAmount;
        private Integer feeAmount;

        public PaymentAmountInfo() { }

        public String getPaymentState() {
            return paymentState;
        }

        public void setPaymentState(String paymentState) {
            this.paymentState = paymentState;
        }

        public Integer getApprovedAmount() {
            return approvedAmount;
        }

        public void setApprovedAmount(Integer approvedAmount) {
            this.approvedAmount = approvedAmount;
        }

        public Integer getDepositedAmount() {
            return depositedAmount;
        }

        public void setDepositedAmount(Integer depositedAmount) {
            this.depositedAmount = depositedAmount;
        }

        public Integer getRefundedAmount() {
            return refundedAmount;
        }

        public void setRefundedAmount(Integer refundedAmount) {
            this.refundedAmount = refundedAmount;
        }

        public Integer getFeeAmount() {
            return feeAmount;
        }

        public void setFeeAmount(Integer feeAmount) {
            this.feeAmount = feeAmount;
        }

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

    public class BankInfo {
        /*
        Model
        {
            "bankName": "BANK ZENIT",
            "bankCountryCode": "RU",
            "bankCountryName": "Россия"
        }
         */

        private String bankName;
        private String bankCountryCode;
        private String bankCountryName;

        public BankInfo() { }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCountryCode() {
            return bankCountryCode;
        }

        public void setBankCountryCode(String bankCountryCode) {
            this.bankCountryCode = bankCountryCode;
        }

        public String getBankCountryName() {
            return bankCountryName;
        }

        public void setBankCountryName(String bankCountryName) {
            this.bankCountryName = bankCountryName;
        }

        @Override
        public String toString() {
            return "BankInfo{" +
                    "bankName='" + bankName + '\'' +
                    ", bankCountryCode='" + bankCountryCode + '\'' +
                    ", bankCountryName='" + bankCountryName + '\'' +
                    '}';
        }
    }

    public class PayerData {
        /*
        Model
        {
            "phone": "9876543210",
            "email": "afasdf@afasd.tr"
        }
         */

        private String phone;
        private String email;

        public PayerData() { }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "PayerData{" +
                    "phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}

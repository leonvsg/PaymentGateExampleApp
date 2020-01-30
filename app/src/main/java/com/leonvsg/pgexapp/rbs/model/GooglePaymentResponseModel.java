package com.leonvsg.pgexapp.rbs.model;

/*
Models

{
    "success": true,
    "data": {
        "orderId": "99c685ec-2cfe-7444-a36c-a00c00006aec"
    }
}
{
    "success": true,
    "data": {
        "orderId": "2ea81b51-176a-7557-a4f1-26dc00006aec",
        "acsUrl": "https://web.rbsuat.com/acs/auth/start.do",
        "paReq": "eJxVUttym0AM/RWGd7wXCBePvBknTtu0Y",
        "termUrl": "https://web.rbsuat.com/ab/rest/finish3ds.do"
    }
}
{
    "success": false,
    "data": {},
    "error": {
        "code": "10",
        "description": "Некорректное значение параметра [orderNumber]",
        "message": "Некорректное значение параметра [orderNumber]"
    }
}
 */

public class GooglePaymentResponseModel {

    private boolean success;
    private Data data;
    private Error error;

    public GooglePaymentResponseModel() { }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "GooglePaymentResponseModel{" +
                "success=" + success +
                ", data=" + data +
                ", error=" + error +
                '}';
    }

    public class Data {

        private String orderId;
        private String acsUrl;
        private String paReq;
        private String termUrl;

        public Data() { }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAcsUrl() {
            return acsUrl;
        }

        public void setAcsUrl(String acsUrl) {
            this.acsUrl = acsUrl;
        }

        public String getPaReq() {
            return paReq;
        }

        public void setPaReq(String paReq) {
            this.paReq = paReq;
        }

        public String getTermUrl() {
            return termUrl;
        }

        public void setTermUrl(String termUrl) {
            this.termUrl = termUrl;
        }

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

    public class Error {

        private Integer code;
        private String description;
        private String message;

        public Error() { }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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

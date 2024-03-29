package com.leonvsg.pgexapp.rbs;

public class Constants {

    public final static String REGISTER_ORDER_URL_END = "/rest/register.do";
    public final static String CARD_PAYMENT_URL_END = "/rest/paymentorder.do";
    public final static String GOOGLE_PAY_PAYMENT_URL_END = "/google/payment.do";
    public final static String GET_ORDER_STATUS_URL_END = "/rest/getOrderStatusExtended.do";
    public final static String SE_PUBLIC_KEY_URL_END = "/se/keys.do";
    public final static String ACS_REDIRECT_URL_END = "/acsRedirect.do";
    public final static String RETURN_URL = "http://localhost";
    public final static Integer CURRENCY_CODE = 643;
    public final static String DEFAULT_CARDHOLDER_NAME = "Card Holder";

    public enum PaymentGates {

        ALFABANK("https://web.rbsuat.com/ab", "UAT AB", "alfabank"),
        OTHER("", "Другое", "rbs");

        private String uri;
        private String name;
        private String gPayGatewayId;
        PaymentGates(String uri, String name, String gPayGatewayId){
            this.uri = uri;
            this.name = name;
            this.gPayGatewayId = gPayGatewayId;
        }
        public String getURI(){ return uri;}
        public String getName(){ return name;}
        public String getGPayGatewayId() { return gPayGatewayId; }
    }

    private Constants() {}
}

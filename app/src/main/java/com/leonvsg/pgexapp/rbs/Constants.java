package com.leonvsg.pgexapp.rbs;

public class Constants {

    public final static String REGISTER_ORDER_URL_END = "/rest/register.do";
    public final static String CARD__URL_END = "/rest/paymentorder.do";
    public final static String GOOGLE_PAY_PAYMENT_URL_END = "/google/payment.do";
    public final static String GET_ORDER_STATUS_URL_END = "/rest/getOrderStatusExtended.do";
    public final static String SE_PUBLIC_KEY_URL_END = "/se/keys.do";
    public final static String ACS_REDIRECT_URL_END = "/acsRedirect.do";
    public final static String RETURN_URL = "http://localhost";

    public enum PaymentGateURI{

        ALFABANK("https://web.rbsuat.com/ab", "UAT AB"),
        SBERBANK("https://3dsec.sberbank.ru/payment", "UAT SBRF");

        private String uri;
        private String name;
        PaymentGateURI(String uri, String name){
            this.uri = uri;
            this.name = name;
        }
        public String getURI(){ return uri;}
        public String getName(){ return name;}
    }

    private Constants() {}
}

package com.leonvsg.pgexapp.rbs;

public class Constants {

    public final static String REGISTER_ORDER_URL_END = "/rest/register.do";
    public final static String CARD_PAYMENT_URL_END = "/rest/paymentorder.do";
    public final static String GOOGLE_PAY_PAYMENT_URL_END = "/google/payment.do";
    public final static String GET_ORDER_STATUS_URL_END = "/rest/getOrderStatusExtended.do";
    public final static String SE_PUBLIC_KEY_URL_END = "/se/keys.do";
    public final static String ACS_REDIRECT_URL_END = "/acsRedirect.do";
    public final static String RETURN_URL = "http://localhost";
    public final static Integer DEFAULT_CURRENCY_CODE = 643;
    public final static String DEFAULT_CARDHOLDER_NAME = "Card Holder";
    public final static String DEFAULT_GOOGLE_PAY_GATEWAY_ID = "rbs";

    public enum Currencies {

        RUB(643),
        RUR(810),
        BYN(933),
        USD(840),
        EUR(978),
        KZT(398),
        UAH(980),
        GBP(826);

        private int code;
        Currencies(int code){
            this.code=code;
        }
        public int getCode(){ return code; }
    }

    public enum PaymentGates {
        ALFABANK("https://web.rbsuat.com/ab", "UAT AB", "alfabank"),
        SBERBANK("https://3dsec.sberbank.ru/payment", "UAT SBRF", "sberbank"),
        OTHER("", "Другое", DEFAULT_GOOGLE_PAY_GATEWAY_ID);

        private String uri;
        private String name;
        private String googlePayGatewayId;
        PaymentGates(String uri, String name, String googlePayGatewayId){
            this.uri = uri;
            this.name = name;
            this.googlePayGatewayId = googlePayGatewayId;
        }
        public String getURI(){ return uri;}
        public String getName(){ return name;}
        public String getGooglePayGatewayId() { return googlePayGatewayId; }
    }

    private Constants() {}
}

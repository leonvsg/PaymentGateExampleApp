package com.leonvsg.pgexapp.google.model;

import lombok.Data;

@Data
public class PaymentData {
    private Integer apiVersionMinor;
    private Integer apiVersion;
    private PaymentMethodData paymentMethodData;

    @Data
    public class PaymentMethodData {
        private String description;
        private TokenizationData tokenizationData;
        private String type;
        private Info info;

        @Data
        public class TokenizationData {
            private String type;
            private String token;
        }

        @Data
        public class Info {
            private String cardNetwork;
            private String cardDetails;
        }
    }

}

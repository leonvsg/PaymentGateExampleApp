package com.leonvsg.pgexapp.google.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

public @Data class PaymentToken {

    private String signature;
    private String protocolVersion;
    private String signedMessage;

    public String toJson(){
        return JSON.toJSONString(this);
    }
}

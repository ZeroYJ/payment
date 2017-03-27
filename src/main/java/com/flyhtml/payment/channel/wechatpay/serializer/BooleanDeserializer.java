package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 布尔反序列化器
 * Author: haolin
 * Email: haolin.h0@gmail.com
 * Date: 27/11/15
 * @since 1.0.0
 */
public class BooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String r = jp.getValueAsString();
        return "Y".equals(r);
    }
}

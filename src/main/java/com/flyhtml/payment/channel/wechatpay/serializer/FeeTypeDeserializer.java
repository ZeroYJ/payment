package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.flyhtml.payment.channel.wechatpay.model.enums.FeeType;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:18
 * @describe 交易类型反序列化器
 */
public class FeeTypeDeserializer extends JsonDeserializer<FeeType> {

    @Override
    public FeeType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return FeeType.from(jp.getValueAsString());
    }
}

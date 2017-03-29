package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flyhtml.payment.channel.wechatpay.model.enums.TradeState;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:18
 * @describe 交易状态反序列化器
 */
public class TradeStateDeserializer extends JsonDeserializer<TradeState> {

    @Override
    public TradeState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                                                                              JsonProcessingException {
        return TradeState.from(jp.getValueAsString());
    }
}

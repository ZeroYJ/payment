package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flyhtml.payment.channel.wechatpay.model.enums.TradeType;

/**
 * 交易类型反序列化器
 *
 * @since 1.0.0
 */
public class TradeTypeDeserializer extends JsonDeserializer<TradeType> {

  @Override
  public TradeType deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    return TradeType.from(jp.getValueAsString());
  }
}

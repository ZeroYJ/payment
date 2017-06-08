package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:18
 * @describe 布尔反序列化器
 */
public class BooleanDeserializer extends JsonDeserializer<Boolean> {

  @Override
  public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String r = jp.getValueAsString();
    return "Y".equals(r);
  }
}

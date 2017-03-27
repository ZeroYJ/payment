package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import me.hao0.common.date.Dates;

/**
 * 日期反序列化器
 */
public class DateDeserializer extends JsonDeserializer<Date> {
 
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
        return Dates.toDate(parser.getValueAsString(), "yyyyMMddHHmmss");
    }
}
package com.flyhtml.payment.channel.wechatpay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.flyhtml.payment.channel.wechatpay.model.enums.RefundChannel;
import me.hao0.common.util.Strings;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:18
 * @describe 退款渠道反序列化器
 */
public class RefundChannelDeserializer extends JsonDeserializer<RefundChannel> {

    @Override
    public RefundChannel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String val = jp.getValueAsString();
        if (Strings.isNullOrEmpty(val)) {
            return null;
        }
        return RefundChannel.from(val);
    }
}

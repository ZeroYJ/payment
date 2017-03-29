package com.flyhtml.payment.channel.wechatpay.util;

import com.flyhtml.payment.channel.wechatpay.WechatPayConfig;
import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import me.hao0.common.date.Dates;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:02
 * @describe 支付宝工具类
 */
public class WechatUtil {

    public static void main(String[] args) {
        Wepay wepay = WepayBuilder.newBuilder(WechatPayConfig.appid, WechatPayConfig.appKey,
                                              WechatPayConfig.mch_id).build();
        JsPayRequest jsPayRequest = new JsPayRequest();
        jsPayRequest.setOpenId("o0iNcxLAfNPc5rz-2u2-u1D9BauA");
        jsPayRequest.setOutTradeNo("OSJAKWKNFO123");
        jsPayRequest.setTotalFee(100);
        jsPayRequest.setBody("辅料易-辅料");
        jsPayRequest.setAttach("支付测试");
        jsPayRequest.setClientId("127.0.0.1");
        jsPayRequest.setNotifyUrl("http://fuliaoyi.com/flyhtml");
        jsPayRequest.setTimeStart(Dates.now("yyyyMMddHHmmss"));
        JsPayResponse jsPayResponse = wepay.pay().jsPay(jsPayRequest);
        System.out.println(jsPayResponse);
    }
}

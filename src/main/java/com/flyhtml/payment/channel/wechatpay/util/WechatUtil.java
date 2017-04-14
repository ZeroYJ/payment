package com.flyhtml.payment.channel.wechatpay.util;

import java.util.*;

import com.alibaba.druid.util.HttpClientUtils;
import com.flyhtml.payment.channel.wechatpay.WechatPayConfig;
import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.common.util.RandomStrs;

import me.hao0.common.date.Dates;
import me.hao0.common.http.Http;
import me.hao0.common.security.MD5;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:02
 * @describe 支付宝工具类
 */
public class WechatUtil {

    public static void main(String[] args) {
        Wepay wepay = WepayBuilder.newBuilder(WechatPayConfig.appid, WechatPayConfig.appTestKey,
                                              WechatPayConfig.mch_id).build();
        JsPayRequest jsPayRequest = new JsPayRequest();
        jsPayRequest.setOpenId("o0iNcxLAfNPc5rz-2u2-u1D9BauA");
        jsPayRequest.setOutTradeNo("OSJAKWKNFO123");
        jsPayRequest.setTotalFee(101);
        jsPayRequest.setBody("辅料易-辅料");
        jsPayRequest.setAttach("支付测试");
        jsPayRequest.setClientId("127.0.0.1");
        jsPayRequest.setNotifyUrl("http://fuliaoyi.com/flyhtml");
        jsPayRequest.setTimeStart(Dates.now("yyyyMMddHHmmss"));
        JsPayResponse jsPayResponse = wepay.pay().jsPay(jsPayRequest);
        System.out.println(jsPayResponse);

        // Map<String, String> params = new HashMap<>();
        // params.put("mch_id", WechatPayConfig.mch_id);
        // params.put("nonce_str", RandomStrs.generate(20));
        // List<String> keys = new ArrayList<>(params.keySet());
        // Collections.sort(keys);
        // StringBuilder payString = new StringBuilder();
        // for (int i = 0; i < keys.size(); i++) {
        // String key = keys.get(i);
        // String value = params.get(key);
        // if (i == keys.size() - 1) {
        // // 拼接时，不包括最后一个&字符
        // payString.append(key).append("=").append(value);
        // } else {
        // payString.append(key).append("=").append(value).append("&");
        // }
        // }
        // String sign = MD5.generate(payString.toString() + "&key=" + WechatPayConfig.appKey, false);
        // params.put("sign", sign);
        // System.out.println(payString);
        // String request =
        // Http.post("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey").ssl().body(Maps.toXml(params)).request();
        // System.out.println(request);
    }

}

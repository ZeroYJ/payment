package com.flyhtml.payment.channel.wechatpay;

import javax.annotation.PostConstruct;

import com.flyhtml.payment.common.enums.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;

import me.hao0.common.date.Dates;

import java.util.Map;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:02
 * @describe 微信服务类
 */
@Component
public class WechatSupport {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appKey}")
    private String appKey;

    @Value("${wechat.mchId}")
    private String mchId;

    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    private Wepay  wepay;

    @PostConstruct
    private void init() {
        wepay = WepayBuilder.newBuilder(appId, appKey, mchId).build();
    }

    /***
     * @param openId OpenId
     * @param orderId 订单编号
     * @param totalFee 订单金额（分）
     * @param body 商品描述（商家名称-销售商品类目）
     * @param attach 附加数据
     * @param clientIp 终端IP
     * @return
     */
    public JsPayResponse jsPay(String openId, String orderId, Integer totalFee, String body, String attach,
                               String clientIp, String payId) {
        JsPayRequest jsPay = new JsPayRequest();
        jsPay.setOpenId(openId);
        jsPay.setOutTradeNo(orderId);
        jsPay.setTotalFee(totalFee);
        jsPay.setBody(body);
        jsPay.setAttach(attach);
        jsPay.setClientId(clientIp);
        jsPay.setNotifyUrl(notifyUrl + "/" + payId);
        jsPay.setTimeStart(Dates.now("yyyyMMddHHmmss"));
        JsPayResponse payResponse = wepay.pay().jsPay(jsPay);
        return payResponse;
    }

    /***
     * 微信签名效验
     * 
     * @param paramMap
     * @return
     */
    public Boolean verifySign(Map<String, Object> paramMap) {
        return wepay.notifies().verifySign(paramMap);
    }

    /***
     * 返回微信失败信息
     * 
     * @param validate 效验枚举
     * @return
     */
    public String notOk(Validate validate) {
        return wepay.notifies().notOk(validate.getName());
    }

    /***
     * 返回微信成功信息
     * 
     * @return
     */
    public String ok() {
        return wepay.notifies().ok();
    }
}

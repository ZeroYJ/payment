package com.flyhtml.payment.channel.wechatpay.model.pay;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:14
 * @describe JS支付请求对象
 */
public class JsPayRequest extends PayRequest {

    /**
     * 用户标识
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "JsPayRequest{" + "openId='" + openId + '\'' + "} " + super.toString();
    }
}

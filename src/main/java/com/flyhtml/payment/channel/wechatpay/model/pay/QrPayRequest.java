package com.flyhtml.payment.channel.wechatpay.model.pay;

import com.flyhtml.payment.channel.wechatpay.annotation.Optional;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:16
 * @describe 二维码支付请求对象
 */
public class QrPayRequest extends PayRequest {

    /**
     * 商品ID
     */
    @Optional(any = false)
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "QrPayRequest{" + "productId='" + productId + '\'' + "} " + super.toString();
    }
}

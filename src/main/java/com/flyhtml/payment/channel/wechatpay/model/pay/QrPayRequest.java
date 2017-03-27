package com.flyhtml.payment.channel.wechatpay.model.pay;

import com.flyhtml.payment.channel.wechatpay.annotation.Optional;

/**
 * 二维码支付请求对象
 * 
 * @since 1.0.0
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

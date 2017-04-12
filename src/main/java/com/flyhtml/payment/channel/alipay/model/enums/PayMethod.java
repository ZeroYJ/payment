package com.flyhtml.payment.channel.alipay.model.enums;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:36
 * @describe 默认支付方式
 */

public enum PayMethod {

                       /**
                        * 信用支付
                        */
                       CREDIT_PAY("creditPay"),

                       /**
                        * 余额支付
                        */
                       DIRECT_PAY("directPay");

    private String value;

    private PayMethod(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

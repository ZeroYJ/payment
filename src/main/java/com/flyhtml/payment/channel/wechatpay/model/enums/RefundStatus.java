package com.flyhtml.payment.channel.wechatpay.model.enums;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:11
 * @describe 退款类型
 */
public enum RefundStatus {

                          /**
                           * 退款成功
                           */
                          SUCCESS("SUCCESS"),

                          /**
                           * 退款失败
                           */
                          FAIL("FAIL"),

                          /**
                           * 退款处理中
                           */
                          PROCESSING("PROCESSING"),

                          /**
                           * 未确定，需要商户原退款单号重新发起
                           */
                          NOTSURE("NOTSURE"),

                          /**
                           * 转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
                           */
                          CHANGE("CHANGE");

    private String value;

    RefundStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static RefundStatus from(String s) {
        for (RefundStatus rs : RefundStatus.values()) {
            if (rs.value().equals(s)) {
                return rs;
            }
        }
        throw new IllegalArgumentException("unknown refund status: " + s);
    }
}

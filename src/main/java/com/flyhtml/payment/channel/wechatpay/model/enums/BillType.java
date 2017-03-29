package com.flyhtml.payment.channel.wechatpay.model.enums;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:10
 * @describe 账单类型
 */
public enum BillType {

                      /**
                       * 所有订单信息
                       */
                      ALL("ALL"),

                      /**
                       * 成功支付的订单
                       */
                      SUCCESS("SUCCESS"),

                      /**
                       * 退款订单
                       */
                      REFUND("REFUND");

    private String type;

    private BillType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static BillType from(String t) {
        for (BillType bt : BillType.values()) {
            if (bt.type().equals(t)) {
                return bt;
            }
        }
        throw new IllegalArgumentException("unknown bill type: " + t);
    }

}

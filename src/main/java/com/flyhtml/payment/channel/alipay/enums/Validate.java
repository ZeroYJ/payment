package com.flyhtml.payment.channel.alipay.enums;

/**
 * @author xiaowei
 * @time 17-4-13 下午1:55
 * @describe 验证结果枚举类
 */
public enum Validate {
                      SUCCESS("success", "验证通过"), INVALID_SIGNATURE("invalid_signature", "无效签名"),
                      INVALID_OUT_TRADE_NO("invalid_out_trade_no", "无效订单号"),
                      INACCURATE_AMOUNT("inaccurate_amount", "金额不准确"), INACCURATE_APPID("inaccurate_appid", "APPID不准确"),
                      INACCURATE_TRADE_STATUS("inaccurate_trade_status", "交易状态不准确"),
                      INACCURATE_SELLER_ID("inaccurate_seller_id", "seller_id不准确");

    private String name;
    private String desc;

    Validate(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

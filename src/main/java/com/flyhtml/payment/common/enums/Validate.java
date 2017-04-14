package com.flyhtml.payment.common.enums;

/**
 * @author xiaowei
 * @time 17-4-13 下午1:55
 * @describe 验证结果枚举类
 */
public enum Validate {
                      success("success", "验证通过"), invalid_body("invalid_body", "无效body"),
                      invalid_signature("invalid_signature", "无效签名"),
                      invalid_out_trade_no("invalid_out_trade_no", "无效订单号"),
                      inaccurate_amount("inaccurate_amount", "金额不准确"), inaccurate_appid("inaccurate_appid", "APPID不准确"),
                      inaccurate_trade_status("inaccurate_trade_status", "交易状态不准确"),
                      inaccurate_seller_id("inaccurate_seller_id", "seller_id不准确"), notify_repeat("success", "通知重复");

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

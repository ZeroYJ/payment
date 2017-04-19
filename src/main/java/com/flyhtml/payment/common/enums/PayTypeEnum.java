package com.flyhtml.payment.common.enums;

/**
 * @author xiaowei
 * @time 17-4-10 上午11:59
 * @describe 支付方式枚举类
 */
public enum PayTypeEnum {
                         wx_pub(0, "微信公众号支付", "wx_pub"), alipay_wap(1, "支付宝手机网站支付", "alipay_wap"),
                         wx_qr(2, "微信扫码支付", "wx_qr"), alipay_web(3, "支付宝网站支付", "alipay_web");

    private int    value;
    private String desc;
    private String name;

    private PayTypeEnum(int value, String desc, String name) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public static PayTypeEnum getByName(String name) {
        for (PayTypeEnum enum1 : values()) {
            if (name.equals(enum1.getName())) return enum1;
        }
        return null;
    }

    public static PayTypeEnum getByValue(int value) {
        for (PayTypeEnum enum1 : values()) {
            if (value == enum1.getValue()) return enum1;
        }
        return null;
    }
}

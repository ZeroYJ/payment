package com.flyhtml.payment.channel;

/**
 * @author xiaowei
 * @time 17-4-1 下午2:39
 * @describe 通用配置
 */
public class BaseConfig {

    // 域名
    public static String URL               = "http://helloxw.viphk.ngrok.org";
    // 支付宝通知地址
    public static String ALIPAY_NOTIFY_URL = URL + "/notify/alipay";
    // 微信通知地址
    public static String WECHAT_NOTIFY_URL = URL + "/notify/wechat";
}

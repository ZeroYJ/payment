package com.flyhtml.payment.channel.wechatpay;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:19
 * @describe 微信支付配置类
 * @remarks 微信参考地址 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 */
public class WechatPayConfig {

    // APPID
    public static String appid      = "wxc995ba40913d605d";
    // APPSECRET
    public static String appsecret  = "add3136cebac319f2ffa4b0e2a95e42b";
    // APPKEY
    public static String appKey     = "b81fc761fe654f619f150558c490ea49";
    // 商户号
    public static String mch_id     = "1366385702";
    // URL
    public static String url        = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 通知地址
    public static String notify_url = "";
    // 交易类型JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参
    public static String trade_type = "JSAPI";
}

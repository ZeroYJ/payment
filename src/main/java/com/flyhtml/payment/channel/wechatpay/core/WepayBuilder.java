package com.flyhtml.payment.channel.wechatpay.core;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:07
 * @describe Wepay构造器
 */
public final class WepayBuilder {

  private Wepay wepay;

  private WepayBuilder() {}

  public static WepayBuilder newBuilder(String appId, String appKey, String mchId) {
    WepayBuilder builder = new WepayBuilder();
    builder.wepay = new Wepay(appId, appKey, mchId);
    return builder;
  }

  /**
   * 设置证书密码
   *
   * @param certPasswd 证书密码
   * @return this
   */
  public WepayBuilder certPasswd(String certPasswd) {
    wepay.certPasswd = certPasswd;
    return this;
  }

  /**
   * 设置证书数据(p12文件)
   *
   * @param certs 二进制数据
   * @return this
   */
  public WepayBuilder certs(byte[] certs) {
    wepay.certs = certs;
    return this;
  }

  /**
   * 设置解析微信XML时, 使用的编码类型
   *
   * @param encode 编码类型
   * @return this
   */
  public WepayBuilder respEncode(String encode) {
    wepay.respEncode = encode;
    return this;
  }

  public Wepay build() {
    return wepay.init();
  }
}

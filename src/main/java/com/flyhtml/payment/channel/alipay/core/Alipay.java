package com.flyhtml.payment.channel.alipay.core;

import java.util.HashMap;
import java.util.Map;

import com.flyhtml.payment.channel.alipay.model.enums.AlipayField;
import com.flyhtml.payment.channel.alipay.model.enums.PayMethod;
import com.flyhtml.payment.channel.alipay.model.enums.PaymentType;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:26
 * @describe 支付宝支付
 */
public final class Alipay {

  /** 支付宝网关 */
  static final String GATEWAY = "https://mapi.alipay.com/gateway.do?";

  /** 签约的支付宝账号对应的支付宝唯一用户号，以2088开头的16位纯数字组成。 */
  String merchantId;

  /** 商户密钥 */
  String secret;

  /** 商户邮箱帐号 */
  String email;

  /** 商户网站使用的编码格式，如utf-8、gbk、gb2312等 */
  String inputCharset = "UTF-8";

  /** 支付类型 */
  String paymentType = PaymentType.BUY.value();

  /** 默认支付方式 */
  String payMethod = PayMethod.DIRECT_PAY.value();

  /** 支付超时时间 */
  String expired = "48h";

  /** APP RSA私钥 */
  String appPriKey;

  /** APP RSA公钥 */
  String appPubKey;

  /** 二维码 RSA私钥 */
  String qrPriKey;

  /** 二维码 RSA公钥 */
  String qrPubKey;

  /** 支付配置参数，不需每次请求都生成 */
  Map<String, String> payConfig;

  /** 退款配置参数，不需每次请求都生成 */
  Map<String, String> refundConfig;

  Map<String, Component> components = new HashMap<>();

  Alipay(String merchantId, String secret) {
    this.merchantId = merchantId;
    this.secret = secret;
  }

  Alipay start() {
    initConfig();
    initComponents();
    return this;
  }

  private void initConfig() {
    payConfig = new HashMap<>();
    payConfig.put(AlipayField.PARTNER.field(), merchantId);
    payConfig.put(AlipayField.SELLER_ID.field(), merchantId);
    payConfig.put(AlipayField.PAYMENT_TYPE.field(), paymentType);
    payConfig.put(AlipayField.IT_B_PAY.field(), expired);
    payConfig.put(AlipayField.INPUT_CHARSET.field(), inputCharset);

    refundConfig = new HashMap<>();
    refundConfig.put(AlipayField.PARTNER.field(), merchantId);
    refundConfig.put(AlipayField.INPUT_CHARSET.field(), inputCharset);
    refundConfig.put(AlipayField.SELLER_USER_ID.field(), merchantId);
  }

  private void initComponents() {
    components.put(Pays.class.getSimpleName(), new Pays(this));
    components.put(Refunds.class.getSimpleName(), new Refunds(this));
    components.put(Verifies.class.getSimpleName(), new Verifies(this));
  }

  public Pays pay() {
    return (Pays) components.get(Pays.class.getSimpleName());
  }

  public Refunds refund() {
    return (Refunds) components.get(Refunds.class.getSimpleName());
  }

  public Verifies verify() {
    return (Verifies) components.get(Verifies.class.getSimpleName());
  }

  @Override
  public String toString() {
    return "AlipayUtil{"
        + "merchantId='"
        + merchantId
        + '\''
        + ", secret='"
        + secret
        + '\''
        + ", inputCharset='"
        + inputCharset
        + '\''
        + ", paymentType='"
        + paymentType
        + '\''
        + ", payMethod='"
        + payMethod
        + '\''
        + ", expired='"
        + expired
        + '\''
        + ", appPriKey='"
        + appPriKey
        + '\''
        + ", appPubKey='"
        + appPubKey
        + '\''
        + ", qrPriKey='"
        + qrPriKey
        + '\''
        + ", qrPubKey='"
        + qrPubKey
        + '\''
        + '}';
  }
}

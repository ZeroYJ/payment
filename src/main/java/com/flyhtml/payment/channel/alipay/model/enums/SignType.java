package com.flyhtml.payment.channel.alipay.model.enums;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:37
 * @describe 签名方式
 */
public enum SignType {
  MD5("MD5"),

  DSA("DSA"),

  RSA("RSA");

  private String value;

  private SignType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}

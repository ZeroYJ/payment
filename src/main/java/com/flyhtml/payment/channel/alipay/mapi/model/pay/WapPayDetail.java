package com.flyhtml.payment.channel.alipay.mapi.model.pay;

import com.flyhtml.payment.channel.alipay.mapi.annotation.Optional;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:40
 * @describe 支付宝WAP支付明细
 */
public class WapPayDetail extends PayDetail {

  private static final long serialVersionUID = -4046847553452516114L;

  /** 商品展示网址 */
  @Optional private String showUrl;

  /** 手机支付宝token */
  @Optional private String externToken;

  /** 航旅订单其它费用 */
  @Optional private String otherFee;

  /** 航旅订单金额 */
  @Optional private String airTicket;

  /** 是否发起实名校验 */
  @Optional private String rnCheck;

  /** 买家证件号码 */
  @Optional private String buyerCertNo;

  /** 买家真实姓名 */
  @Optional private String buyerRealName;

  /** 收单场景 */
  @Optional private String scene;

  public WapPayDetail(String outTradeNo, String orderName, String totalFee, String body) {
    super(outTradeNo, orderName, totalFee, body);
  }

  public String getShowUrl() {
    return showUrl;
  }

  public void setShowUrl(String showUrl) {
    this.showUrl = showUrl;
  }

  public String getExternToken() {
    return externToken;
  }

  public void setExternToken(String externToken) {
    this.externToken = externToken;
  }

  public String getOtherFee() {
    return otherFee;
  }

  public void setOtherFee(String otherFee) {
    this.otherFee = otherFee;
  }

  public String getAirTicket() {
    return airTicket;
  }

  public void setAirTicket(String airTicket) {
    this.airTicket = airTicket;
  }

  public String getRnCheck() {
    return rnCheck;
  }

  public void setRnCheck(String rnCheck) {
    this.rnCheck = rnCheck;
  }

  public String getBuyerCertNo() {
    return buyerCertNo;
  }

  public void setBuyerCertNo(String buyerCertNo) {
    this.buyerCertNo = buyerCertNo;
  }

  public String getBuyerRealName() {
    return buyerRealName;
  }

  public void setBuyerRealName(String buyerRealName) {
    this.buyerRealName = buyerRealName;
  }

  public String getScene() {
    return scene;
  }

  public void setScene(String scene) {
    this.scene = scene;
  }

  @Override
  public String toString() {
    return "WapPayFields{"
        + "showUrl='"
        + showUrl
        + '\''
        + ", externToken='"
        + externToken
        + '\''
        + ", otherFee='"
        + otherFee
        + '\''
        + ", airTicket='"
        + airTicket
        + '\''
        + ", rnCheck='"
        + rnCheck
        + '\''
        + ", buyerCertNo='"
        + buyerCertNo
        + '\''
        + ", buyerRealName='"
        + buyerRealName
        + '\''
        + ", scene='"
        + scene
        + '\''
        + "} "
        + super.toString();
  }
}

package com.flyhtml.payment.channel.wechatpay.model.notify;

import java.io.Serializable;

/**
 * @author xiaowei
 * @time 17-4-14 下午4:05
 * @describe 微信支付成功通知对象
 */
public class WechatNotify implements Serializable {

  private static final long serialVersionUID = 5380675398077433915L;
  /** 微信分配的公众账号ID */
  private String appid;
  /** 微信支付分配的商户号 */
  private String mchId;
  /** 微信支付分配的终端设备号 */
  private String deviceInfo;
  /** 随机字符串，不长于32位 */
  private String nonceStr;
  /** 签名 */
  private String sign;
  /** 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5 */
  private String signType;
  /** SUCCESS/FAIL */
  private String resultCode;
  /** 错误返回的信息描述 */
  private String errCode;
  /** 错误返回的信息描述 */
  private String errCodeDes;
  /** 用户在商户appid下的唯一标识 */
  private String openid;
  /** 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效 */
  private String isSubscribe;
  /** JSAPI、NATIVE、APP */
  private String tradeType;
  /** 银行类型，采用字符串类型的银行标识 */
  private String bankType;
  /** 订单总金额，单位为分 */
  private String totalFee;
  /** 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额 */
  private String settlementTotalFee;
  /** 货币类型 */
  private String feeType;
  /** 现金支付金额订单现金支付金额 */
  private String cashFee;
  /** 货币类型 */
  private String cashFeeType;
  /** 微信支付订单号 */
  private String transactionId;
  /** 商户系统的订单号，与请求一致。 */
  private String outTradeNo;
  /** 商家数据包，原样返回 */
  private String attach;
  /** 支付完成时间 */
  private String timeEnd;

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getDeviceInfo() {
    return deviceInfo;
  }

  public void setDeviceInfo(String deviceInfo) {
    this.deviceInfo = deviceInfo;
  }

  public String getNonceStr() {
    return nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrCodeDes() {
    return errCodeDes;
  }

  public void setErrCodeDes(String errCodeDes) {
    this.errCodeDes = errCodeDes;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getIsSubscribe() {
    return isSubscribe;
  }

  public void setIsSubscribe(String isSubscribe) {
    this.isSubscribe = isSubscribe;
  }

  public String getTradeType() {
    return tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getBankType() {
    return bankType;
  }

  public void setBankType(String bankType) {
    this.bankType = bankType;
  }

  public String getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }

  public String getSettlementTotalFee() {
    return settlementTotalFee;
  }

  public void setSettlementTotalFee(String settlementTotalFee) {
    this.settlementTotalFee = settlementTotalFee;
  }

  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public String getCashFee() {
    return cashFee;
  }

  public void setCashFee(String cashFee) {
    this.cashFee = cashFee;
  }

  public String getCashFeeType() {
    return cashFeeType;
  }

  public void setCashFeeType(String cashFeeType) {
    this.cashFeeType = cashFeeType;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getAttach() {
    return attach;
  }

  public void setAttach(String attach) {
    this.attach = attach;
  }

  public String getTimeEnd() {
    return timeEnd;
  }

  public void setTimeEnd(String timeEnd) {
    this.timeEnd = timeEnd;
  }
}

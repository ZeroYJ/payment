package com.flyhtml.payment.channel.alipay.sdk.model;

import java.io.Serializable;

/**
 * @author xiaowei
 * @time 17-3-31 下午2:50
 * @describe 手机网站支付结果异步通知
 */
public class SdkNotify implements Serializable {

  private static final long serialVersionUID = 5380675398077433915L;
  /** 通知时间 */
  private String notifyTime;
  /** 通知类型 */
  private String notifyType;
  /** 通知效验ID */
  private String notifyId;
  /** 开发者的app_id */
  private String appId;
  /** 编码格式 */
  private String charset;
  /** 接口版本 */
  private String version;
  /** 签名类型 */
  private String signType;
  /** 签名 */
  private String sign;
  /** 支付宝交易号 */
  private String tradeNo;
  /** 商户订单号 */
  private String outTradeNo;
  /** 商户业务号 */
  private String outBizNo;
  /** 买家支付宝用户号 */
  private String buyerId;
  /** 买家支付宝帐号 */
  private String buyerLogonId;
  /** 卖家支付宝用户号 */
  private String sellerId;
  /** 卖家支付宝帐号 */
  private String sellerEmail;
  /** 支付状态 */
  private String tradeStatus;
  /** 订单金额 */
  private String totalAmount;
  /** 实收金额 */
  private String receiptAmount;
  /** 开票金额 */
  private String invoiceAmount;
  /** 付款金额 */
  private String buyerPayAmount;
  /** 集分宝金额 */
  private String pointAmount;
  /** 总退款金额 */
  private String refundFee;
  /** 订单标题 */
  private String subject;
  /** 订单内容 */
  private String body;
  /** 交易创建时间 */
  private String gmtCreate;
  /** 交易付款时间 */
  private String gmtPayment;
  /** 交易退款时间 */
  private String gmtRefund;
  /** 交易结束时间 */
  private String gmtClose;
  /** 支付金额信息 */
  private String fundBillList;
  /** 回传参数 */
  private String passbackParams;
  /** 优惠券信息 */
  private String voucherDetailList;

  public String getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(String notifyTime) {
    this.notifyTime = notifyTime;
  }

  public String getNotifyType() {
    return notifyType;
  }

  public void setNotifyType(String notifyType) {
    this.notifyType = notifyType;
  }

  public String getNotifyId() {
    return notifyId;
  }

  public void setNotifyId(String notifyId) {
    this.notifyId = notifyId;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getOutBizNo() {
    return outBizNo;
  }

  public void setOutBizNo(String outBizNo) {
    this.outBizNo = outBizNo;
  }

  public String getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(String buyerId) {
    this.buyerId = buyerId;
  }

  public String getBuyerLogonId() {
    return buyerLogonId;
  }

  public void setBuyerLogonId(String buyerLogonId) {
    this.buyerLogonId = buyerLogonId;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public String getSellerEmail() {
    return sellerEmail;
  }

  public void setSellerEmail(String sellerEmail) {
    this.sellerEmail = sellerEmail;
  }

  public String getTradeStatus() {
    return tradeStatus;
  }

  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getReceiptAmount() {
    return receiptAmount;
  }

  public void setReceiptAmount(String receiptAmount) {
    this.receiptAmount = receiptAmount;
  }

  public String getInvoiceAmount() {
    return invoiceAmount;
  }

  public void setInvoiceAmount(String invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public String getBuyerPayAmount() {
    return buyerPayAmount;
  }

  public void setBuyerPayAmount(String buyerPayAmount) {
    this.buyerPayAmount = buyerPayAmount;
  }

  public String getPointAmount() {
    return pointAmount;
  }

  public void setPointAmount(String pointAmount) {
    this.pointAmount = pointAmount;
  }

  public String getRefundFee() {
    return refundFee;
  }

  public void setRefundFee(String refundFee) {
    this.refundFee = refundFee;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(String gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public String getGmtPayment() {
    return gmtPayment;
  }

  public void setGmtPayment(String gmtPayment) {
    this.gmtPayment = gmtPayment;
  }

  public String getGmtRefund() {
    return gmtRefund;
  }

  public void setGmtRefund(String gmtRefund) {
    this.gmtRefund = gmtRefund;
  }

  public String getGmtClose() {
    return gmtClose;
  }

  public void setGmtClose(String gmtClose) {
    this.gmtClose = gmtClose;
  }

  public String getFundBillList() {
    return fundBillList;
  }

  public void setFundBillList(String fundBillList) {
    this.fundBillList = fundBillList;
  }

  public String getPassbackParams() {
    return passbackParams;
  }

  public void setPassbackParams(String passbackParams) {
    this.passbackParams = passbackParams;
  }

  public String getVoucherDetailList() {
    return voucherDetailList;
  }

  public void setVoucherDetailList(String voucherDetailList) {
    this.voucherDetailList = voucherDetailList;
  }
}

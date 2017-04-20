package com.flyhtml.payment.channel.alipay.model.notify;

import java.io.Serializable;

/**
 * @author xiaowei
 * @time 17-4-20 下午4:51
 * @describe Mapi支付结果异步通知
 */
public class AlipayNotify implements Serializable{
    private static final long serialVersionUID = 5380675398077433915L;
    private String            notifyTime;                             // 通知时间
    private String            notifyType;                             // 通知类型
    private String            notifyId;                               // 通知效验ID
    private String            signType;                               // 签名类型
    private String            sign;                                   // 签名
    private String            outTradeNo;                             // 商户订单号
    private String            subject;                                // 订单标题
    private String            paymentType;                            // 支付类型
    private String            tradeNo;                                // 支付宝交易号
    private String            tradeStatus;                            // 支付状态
    private String            gmtCreate;                              // 交易创建时间
    private String            gmtPayment;                             // 交易付款时间
    private String            gmtClose;                               // 交易结束时间
    private String            refundStatus;                           // 退款状态
    private String            gmtRefund;                              // 交易退款时间
    private String            sellerEmail;                            // 卖家支付宝帐号
    private String            buyerEmail;                             // 买家支付宝账号
    private String            sellerId;                               // 卖家支付宝用户号
    private String            buyerId;                                // 买家支付宝用户号
    private String            price;                                  // 商品单价
    private String            totalFee;                               // 交易金额
    private String            quantity;                               // 购买数量
    private String            body;                                   // 订单内容
    private String            discount;                               // 折扣
    private String            isTotalFeeAdjust;                       // 该交易是否调整过价格
    private String            useCoupon;                              // 是否使用红包买家
    private String            extraCommonParam;                       // 公用回传参数
    private String            businessScene;                          // 是否扫码支付

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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
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

    public String getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(String gmtClose) {
        this.gmtClose = gmtClose;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(String gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIsTotalFeeAdjust() {
        return isTotalFeeAdjust;
    }

    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
        this.isTotalFeeAdjust = isTotalFeeAdjust;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getExtraCommonParam() {
        return extraCommonParam;
    }

    public void setExtraCommonParam(String extraCommonParam) {
        this.extraCommonParam = extraCommonParam;
    }

    public String getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(String businessScene) {
        this.businessScene = businessScene;
    }
}

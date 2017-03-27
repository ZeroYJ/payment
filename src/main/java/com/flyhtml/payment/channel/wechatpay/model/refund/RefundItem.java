package com.flyhtml.payment.channel.wechatpay.model.refund;

import java.io.Serializable;
import java.util.List;

import com.flyhtml.payment.channel.wechatpay.model.common.Coupon;
import com.flyhtml.payment.channel.wechatpay.model.enums.RefundChannel;
import com.flyhtml.payment.channel.wechatpay.model.enums.RefundStatus;

/**
 * 单笔退款
 * 
 * @since 1.0.0
 */
public class RefundItem implements Serializable {

    private static final long serialVersionUID = -8803509387441693049L;

    /**
     * 商户退款单号
     */
    private String            outRefundNo;

    /**
     * 微信退款单号
     */
    private String            refundId;

    /**
     * 退款渠道
     */
    private RefundChannel     channel;

    /**
     * 申请退款金额, 可以做部分退款
     */
    private Integer           refundFee;

    /**
     * 退款金额, = 申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
     */
    private Integer           settlementRefundFee;

    /**
     * 退款状态
     * 
     * @see RefundStatus
     */
    private RefundStatus      refundStatus;

    /**
     * 取当前退款单的退款入账方 1）退回银行卡： {银行名称}{卡类型}{卡尾号} 2）退回支付用户零钱: 支付用户零钱
     */
    private String            refundRecvAccout;

    /**
     * 代金券或立减优惠退款金额
     */
    private Integer           couponRefundFee;

    /**
     * 代金券或立减优惠退款项
     */
    private List<Coupon>      coupons;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public RefundChannel getChannel() {
        return channel;
    }

    public void setChannel(RefundChannel channel) {
        this.channel = channel;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Integer getSettlementRefundFee() {
        return settlementRefundFee;
    }

    public void setSettlementRefundFee(Integer settlementRefundFee) {
        this.settlementRefundFee = settlementRefundFee;
    }

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundRecvAccout() {
        return refundRecvAccout;
    }

    public void setRefundRecvAccout(String refundRecvAccout) {
        this.refundRecvAccout = refundRecvAccout;
    }

    public Integer getCouponRefundFee() {
        return couponRefundFee;
    }

    public void setCouponRefundFee(Integer couponRefundFee) {
        this.couponRefundFee = couponRefundFee;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "RefundItem{" + "outRefundNo='" + outRefundNo + '\'' + ", refundId='" + refundId + '\'' + ", channel="
               + channel + ", refundFee=" + refundFee + ", settlementRefundFee=" + settlementRefundFee
               + ", refundStatus=" + refundStatus + ", refundRecvAccout='" + refundRecvAccout + '\''
               + ", couponRefundFee=" + couponRefundFee + ", coupons=" + coupons + '}';
    }
}

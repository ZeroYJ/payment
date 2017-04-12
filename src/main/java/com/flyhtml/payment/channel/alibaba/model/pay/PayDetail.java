package com.flyhtml.payment.channel.alibaba.model.pay;

import java.io.Serializable;

import com.flyhtml.payment.channel.alibaba.annotation.Optional;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:38
 * @describe 支付宝PC和WAP公共支付明细
 */
public class PayDetail implements Serializable {

    private static final long serialVersionUID = 5892926888312847503L;

    /**
     * 我方唯一订单号
     */
    protected String          outTradeNo;

    /**
     * 商品名称
     */
    protected String          orderName;

    /**
     * 商品金额(元)
     */
    protected String          totalFee;

    /**
     * 支付宝后置通知url，若为空，则使用Alipay类中的notifyUrl
     */
    @Optional
    protected String          notifyUrl;

    /**
     * 支付宝前端跳转url，若为空，则使用Alipay类中的returnUrl
     */
    @Optional
    protected String          returnUrl;

    public PayDetail(String outTradeNo, String orderName, String totalFee) {
        this.outTradeNo = outTradeNo;
        this.orderName = orderName;
        this.totalFee = totalFee;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "PayFields{" + "outTradeNo='" + outTradeNo + '\'' + ", orderName='" + orderName + '\'' + ", totalFee='"
               + totalFee + '\'' + ", notifyUrl='" + notifyUrl + '\'' + ", returnUrl='" + returnUrl + '\'' + '}';
    }
}

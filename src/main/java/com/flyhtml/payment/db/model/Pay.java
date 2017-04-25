package com.flyhtml.payment.db.model;

import com.flyhtml.payment.common.annotation.ProtoType;
import com.flyhtml.payment.db.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.persistence.*;

/**
 * @author xiaowei
 * @time 17-4-13 下午12:52
 * @describe 支付表
 */
public class Pay extends BaseModel {

    /**
     * 主键
     */
    @Id
    private String     id;

    /**
     * 创建时间
     */
    @ProtoType(type = long.class)
    @Column(name = "gmt_create")
    private Date       gmtCreate;

    /**
     * 修改时间
     */
    @ProtoType(type = long.class)
    @Column(name = "gmt_modified")
    private Date       gmtModified;

    /**
     * 是否已付款
     */
    @Column(name = "is_pay")
    private Boolean    isPay;

    /**
     * 是否存在退款信息
     */
    @Column(name = "has_refund")
    private Boolean    hasRefund;

    /**
     * 支付渠道
     */
    private String     channel;

    /**
     * 订单ID
     */
    @Column(name = "order_no")
    private String     orderNo;

    /**
     * 客户端IP
     */
    private String     ip;

    /**
     * 订单金额
     */
    @ProtoType(type = int.class)
    private BigDecimal amount;

    /**
     * 货币代码,目前仅支持cny
     */
    private String     currency;

    /**
     * 商品标题
     */
    private String     subject;

    /**
     * 商品描述信息
     */
    private String     body;

    /**
     * 支付渠道交易号
     */
    @Column(name = "channel_no")
    private String     channelNo;

    /**
     * 支付时间
     */
    @ProtoType(type = long.class)
    @Column(name = "pay_time")
    private Date       payTime;

    /**
     * 失效时间
     */
    @ProtoType(type = long.class)
    @Column(name = "expire_time")
    private Date       expireTime;

    /**
     * 自定义数据
     */
    private String     custom;

    /**
     * 支付凭证,JSON格式
     */
    @ProtoType(type = Map.class)
    private String     credential;

    /**
     * 额外参数,JSON格式
     */
    @ProtoType(type = Map.class)
    private String     extra;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取是否已付款
     *
     * @return is_pay - 是否已付款
     */
    public Boolean getIsPay() {
        return isPay;
    }

    /**
     * 设置是否已付款
     *
     * @param isPay 是否已付款
     */
    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    /**
     * 获取是否存在退款信息
     *
     * @return has_refund - 是否存在退款信息
     */
    public Boolean getHasRefund() {
        return hasRefund;
    }

    /**
     * 设置是否存在退款信息
     *
     * @param hasRefund 是否存在退款信息
     */
    public void setHasRefund(Boolean hasRefund) {
        this.hasRefund = hasRefund;
    }

    /**
     * 获取支付渠道
     *
     * @return channel - 支付渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置支付渠道
     *
     * @param channel 支付渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取订单ID
     *
     * @return order_no - 订单ID
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单ID
     *
     * @param orderNo 订单ID
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取客户端IP
     *
     * @return ip - 客户端IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置客户端IP
     *
     * @param ip 客户端IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取订单金额
     *
     * @return amount - 订单金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置订单金额
     *
     * @param amount 订单金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取货币代码,目前仅支持cny
     *
     * @return currency - 货币代码,目前仅支持cny
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置货币代码,目前仅支持cny
     *
     * @param currency 货币代码,目前仅支持cny
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取商品标题
     *
     * @return subject - 商品标题
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置商品标题
     *
     * @param subject 商品标题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取商品描述信息
     *
     * @return body - 商品描述信息
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置商品描述信息
     *
     * @param body 商品描述信息
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 获取支付渠道交易号
     *
     * @return channel_no - 支付渠道交易号
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * 设置支付渠道交易号
     *
     * @param channelNo 支付渠道交易号
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取失效时间
     *
     * @return expire_time - 失效时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置失效时间
     *
     * @param expireTime 失效时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取描述
     *
     * @return custom - 描述
     */
    public String getCustom() {
        return custom;
    }

    /**
     * 设置描述
     *
     * @param custom 描述
     */
    public void setCustom(String custom) {
        this.custom = custom;
    }

    /**
     * 获取支付凭证,JSON格式
     *
     * @return credential - 支付凭证,JSON格式
     */
    public String getCredential() {
        return credential;
    }

    /**
     * 设置支付凭证,JSON格式
     *
     * @param credential 支付凭证,JSON格式
     */
    public void setCredential(String credential) {
        this.credential = credential;
    }

    /**
     * 获取额外参数,JSON格式
     *
     * @return extra - 额外参数,JSON格式
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 设置额外参数,JSON格式
     *
     * @param extra 额外参数,JSON格式
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Pay(String id, String channel, String orderNo, String ip, BigDecimal amount, String currency, String subject,
               String body, String custom, String credential, String extra) {
        this.id = id;
        this.channel = channel;
        this.orderNo = orderNo;
        this.ip = ip;
        this.amount = amount;
        this.currency = currency;
        this.subject = subject;
        this.body = body;
        this.custom = custom;
        this.credential = credential;
        this.extra = extra;
    }

    public Pay() {
    }

    public Pay(String id, Boolean isPay, String channelNo, Date payTime) {
        this.id = id;
        this.isPay = isPay;
        this.channelNo = channelNo;
        this.payTime = payTime;
    }

    public Pay(String channel, String orderNo) {
        this.channel = channel;
        this.orderNo = orderNo;
    }
}

package com.flyhtml.payment.db.model;

import com.flyhtml.payment.db.base.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Refund extends BaseModel {

  /**
   * 主键
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  /**
   * 创建时间
   */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /**
   * 是否成功
   */
  @Column(name = "is_succeed")
  private Boolean isSucceed;

  /**
   * 0=处理中,1=成功,2=失败
   */
  private Integer status;

  /**
   * 支付对象ID
   */
  @Column(name = "pay_id")
  private String payId;

  /**
   * 订单ID
   */
  @Column(name = "order_no")
  private String orderNo;

  /**
   * 退款金额
   */
  private BigDecimal amount;

  /**
   * 退款原因
   */
  private String reason;

  /**
   * 操作人ID
   */
  @Column(name = "op_user_id")
  private String opUserId;

  /**
   * 退款成功时间
   */
  @Column(name = "succeed_time")
  private Date succeedTime;

  /**
   * 退款错误码
   */
  @Column(name = "error_code")
  private String errorCode;

  /**
   * 退款错误信息
   */
  @Column(name = "error_msg")
  private String errorMsg;

  /**
   * 备注
   */
  private String remarks;

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
   * 获取是否成功
   *
   * @return is_succeed - 是否成功
   */
  public Boolean getIsSucceed() {
    return isSucceed;
  }

  /**
   * 设置是否成功
   *
   * @param isSucceed 是否成功
   */
  public void setIsSucceed(Boolean isSucceed) {
    this.isSucceed = isSucceed;
  }

  /**
   * 获取0=处理中,1=成功,2=失败
   *
   * @return status - 0=处理中,1=成功,2=失败
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * 设置0=处理中,1=成功,2=失败
   *
   * @param status 0=处理中,1=成功,2=失败
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * 获取支付对象ID
   *
   * @return pay_id - 支付对象ID
   */
  public String getPayId() {
    return payId;
  }

  /**
   * 设置支付对象ID
   *
   * @param payId 支付对象ID
   */
  public void setPayId(String payId) {
    this.payId = payId;
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
   * 获取退款金额
   *
   * @return amount - 退款金额
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * 设置退款金额
   *
   * @param amount 退款金额
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * 获取退款原因
   *
   * @return reason - 退款原因
   */
  public String getReason() {
    return reason;
  }

  /**
   * 设置退款原因
   *
   * @param reason 退款原因
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * 获取操作人ID
   *
   * @return op_user_id - 操作人ID
   */
  public String getOpUserId() {
    return opUserId;
  }

  /**
   * 设置操作人ID
   *
   * @param opUserId 操作人ID
   */
  public void setOpUserId(String opUserId) {
    this.opUserId = opUserId;
  }

  /**
   * 获取退款成功时间
   *
   * @return succeed_time - 退款成功时间
   */
  public Date getSucceedTime() {
    return succeedTime;
  }

  /**
   * 设置退款成功时间
   *
   * @param succeedTime 退款成功时间
   */
  public void setSucceedTime(Date succeedTime) {
    this.succeedTime = succeedTime;
  }

  /**
   * 获取退款错误码
   *
   * @return error_code - 退款错误码
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * 设置退款错误码
   *
   * @param errorCode 退款错误码
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * 获取退款错误信息
   *
   * @return error_msg - 退款错误信息
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /**
   * 设置退款错误信息
   *
   * @param errorMsg 退款错误信息
   */
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  /**
   * 获取备注
   *
   * @return remarks - 备注
   */
  public String getRemarks() {
    return remarks;
  }

  /**
   * 设置备注
   *
   * @param remarks 备注
   */
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
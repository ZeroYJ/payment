package com.flyhtml.payment.db.model;

import com.flyhtml.payment.db.base.BaseModel;

import java.util.Date;
import javax.persistence.*;

/**
 * @author xiaowei
 * @time 17-4-13 下午2:25
 * @describe 支付通知表
 */
@Table(name = "pay_notify")
public class PayNotify extends BaseModel {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 通知地址 */
  @Column(name = "notify_url")
  private String notifyUrl;

  /** 响应数据 */
  @Column(name = "response_data")
  private String responseData;

  /** 通知参数 */
  @Column(name = "notify_param")
  private String notifyParam;

  /**
   * 获取主键
   *
   * @return id - 主键
   */
  public Integer getId() {
    return id;
  }

  /**
   * 设置主键
   *
   * @param id 主键
   */
  public void setId(Integer id) {
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
   * 获取通知地址
   *
   * @return notify_url - 通知地址
   */
  public String getNotifyUrl() {
    return notifyUrl;
  }

  /**
   * 设置通知地址
   *
   * @param notifyUrl 通知地址
   */
  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  /**
   * 获取响应数据
   *
   * @return response_data - 响应数据
   */
  public String getResponseData() {
    return responseData;
  }

  /**
   * 设置响应数据
   *
   * @param responseData 响应数据
   */
  public void setResponseData(String responseData) {
    this.responseData = responseData;
  }

  /**
   * 获取通知参数
   *
   * @return notify_param - 通知参数
   */
  public String getNotifyParam() {
    return notifyParam;
  }

  /**
   * 设置通知参数
   *
   * @param notifyParam 通知参数
   */
  public void setNotifyParam(String notifyParam) {
    this.notifyParam = notifyParam;
  }

  public PayNotify(String notifyUrl, String responseData, String notifyParam) {
    this.notifyUrl = notifyUrl;
    this.responseData = responseData;
    this.notifyParam = notifyParam;
  }

  public PayNotify() {}
}

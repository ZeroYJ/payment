package com.flyhtml.payment.db.model;

import com.flyhtml.payment.db.base.BaseModel;

import java.util.Date;
import javax.persistence.*;

/**
 * @author xiaowei
 * @time 17-4-13 下午12:52
 * @describe 支付回调表
 */
@Table(name = "pay_hooks")
public class PayHooks extends BaseModel {

  /** 主键,同pay_id */
  @Id private String id;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /** 回调地址 */
  @Column(name = "hooks_url")
  private String hooksUrl;

  /** 最新回调时间 */
  @Column(name = "hooks_time")
  private Date hooksTime;

  /** 已回调次数 */
  @Column(name = "hooks_count")
  private Integer hooksCount;

  /** 响应数据 */
  @Column(name = "response_data")
  private String responseData;

  /** 回调参数 */
  @Column(name = "hooks_param")
  private String hooksParam;

  /**
   * 获取主键,同pay_id
   *
   * @return id - 主键,同pay_id
   */
  public String getId() {
    return id;
  }

  /**
   * 设置主键,同pay_id
   *
   * @param id 主键,同pay_id
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
   * 获取回调地址
   *
   * @return hooks_url - 回调地址
   */
  public String getHooksUrl() {
    return hooksUrl;
  }

  /**
   * 设置回调地址
   *
   * @param hooksUrl 回调地址
   */
  public void setHooksUrl(String hooksUrl) {
    this.hooksUrl = hooksUrl;
  }

  /**
   * 获取最新回调时间
   *
   * @return hooks_time - 最新回调时间
   */
  public Date getHooksTime() {
    return hooksTime;
  }

  /**
   * 设置最新回调时间
   *
   * @param hooksTime 最新回调时间
   */
  public void setHooksTime(Date hooksTime) {
    this.hooksTime = hooksTime;
  }

  /**
   * 获取已回调次数
   *
   * @return hooks_count - 已回调次数
   */
  public Integer getHooksCount() {
    return hooksCount;
  }

  /**
   * 设置已回调次数
   *
   * @param hooksCount 已回调次数
   */
  public void setHooksCount(Integer hooksCount) {
    this.hooksCount = hooksCount;
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
   * 获取回调参数
   *
   * @return hooks_param - 回调参数
   */
  public String getHooksParam() {
    return hooksParam;
  }

  /**
   * 设置回调参数
   *
   * @param hooksParam 回调参数
   */
  public void setHooksParam(String hooksParam) {
    this.hooksParam = hooksParam;
  }

  public PayHooks(
      String id,
      String hooksUrl,
      Date hooksTime,
      Integer hooksCount,
      String responseData,
      String hooksParam) {
    this.id = id;
    this.hooksUrl = hooksUrl;
    this.hooksTime = hooksTime;
    this.hooksCount = hooksCount;
    this.responseData = responseData;
    this.hooksParam = hooksParam;
  }

  public PayHooks(String id, String hooksUrl, Integer hooksCount, String hooksParam) {
    this.id = id;
    this.hooksUrl = hooksUrl;
    this.hooksCount = hooksCount;
    this.hooksParam = hooksParam;
  }

  public PayHooks() {}
}

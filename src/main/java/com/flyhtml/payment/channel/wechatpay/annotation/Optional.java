package com.flyhtml.payment.channel.wechatpay.annotation;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:04
 * @describe 标记字段是可选的
 */
public @interface Optional {

  /**
   * 是否任何情况下都可选
   *
   * @return optional or not
   */
  boolean any() default true;
}

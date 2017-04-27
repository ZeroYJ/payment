package com.flyhtml.payment.channel.alipay.model.enums;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:37
 * @describe
 */
public enum RefundStatus {

  /**
   * 退款成功: 全额退款情况：trade_status = TRADE_CLOSED，而refund_status=REFUND_SUCCESS； 非全额退款情况：trade_status =
   * TRADE_SUCCESS，而refund_status=REFUND_SUCCESS
   */
  REFUND_SUCCESS("REFUND_SUCCESS"),

  /** 退款关闭 */
  REFUND_CLOSED("REFUND_CLOSED");

  private String value;

  private RefundStatus(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}

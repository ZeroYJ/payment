package com.flyhtml.payment.channel.alipay.sdk.exception;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:07
 * @describe 支付宝支付异常
 */
public class AlipayException extends RuntimeException {

  /**
   * 当微信发生错误时，对应的错误码
   */
  private String errorCode;

  /**
   * 当微信发生错误时，对应的错误消息
   */
  private String errorMsg;

  public AlipayException(Throwable cause) {
    super(cause);
  }

  public AlipayException(String errorCode, String errorMsg) {
    super("[" + errorCode + "]" + errorMsg);
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
}

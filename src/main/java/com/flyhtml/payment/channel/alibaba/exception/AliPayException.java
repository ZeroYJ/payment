package com.flyhtml.payment.channel.alibaba.exception;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:34
 * @describe Alipay Exception
 */

public class AliPayException extends RuntimeException {

    public AliPayException() {
        super();
    }

    public AliPayException(String message) {
        super(message);
    }

    public AliPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliPayException(Throwable cause) {
        super(cause);
    }

    protected AliPayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

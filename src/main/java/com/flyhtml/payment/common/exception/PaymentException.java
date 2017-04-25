package com.flyhtml.payment.common.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

/**
 * @author xiaowei
 * @time 17-4-10 下午1:16
 * @describe
 */
public class PaymentException extends StatusRuntimeException {

    public PaymentException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}

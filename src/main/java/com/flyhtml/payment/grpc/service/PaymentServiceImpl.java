package com.flyhtml.payment.grpc.service;

import io.grpc.payment.Payment;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author xiaowei
 * @time 17-4-5 下午3:34
 * @describe
 */
@GRpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void create(Payment request, StreamObserver<Payment> responseObserver) {
        super.create(request, responseObserver);
    }
}

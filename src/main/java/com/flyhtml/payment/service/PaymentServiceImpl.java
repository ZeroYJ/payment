package com.flyhtml.payment.service;

import com.flyhtml.payment.channel.alipay.util.AlipayUtil;
import io.grpc.payment.*;
import io.grpc.stub.StreamObserver;

/**
 * Created by xiaowei on 17-3-23.
 */
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void aliPayNotify(CallBackParam request, StreamObserver<ReturnParam> responseObserver) {
        super.aliPayNotify(request, responseObserver);
    }

    @Override
    public void createAlipay(AlipayOrderParam request, StreamObserver<Result> responseObserver) {
        System.out.println(request);
        String form = AlipayUtil.createOrder(request.getSubject(), request.getBody(), request.getOutTradeNo(),
                                             request.getTotalAmount());
        Result result = Result.newBuilder().setData(form).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void createWechat(WechatOrderParam request, StreamObserver<Result> responseObserver) {
        super.createWechat(request, responseObserver);
    }
}

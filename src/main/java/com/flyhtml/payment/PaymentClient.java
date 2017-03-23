package com.flyhtml.payment;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.payment.AlipayOrderParam;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Result;

/**
 * Created by xiaowei on 17-3-23.
 */
public class PaymentClient {

    private static final Logger                                 logger = Logger.getLogger(PaymentClient.class.getName());

    private final ManagedChannel                                channel;
    private final PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub;

    public PaymentClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        blockingStub = PaymentServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void createAlipay() {
        logger.info("Will try to createAlipay...");
        AlipayOrderParam param = AlipayOrderParam.newBuilder().setSubject("Iphone").setBody("16G").setOutTradeNo("O1313213").setTotalAmount("6488").build();
        Result result;
        try {
            result = blockingStub.createAlipay(param);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        PaymentClient client = new PaymentClient("localhost", 50051);
        try {
            client.createAlipay();
        } finally {
            client.shutdown();
        }
    }
}

package com.flyhtml.payment;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.payment.Payment;
import io.grpc.payment.PaymentServiceGrpc;

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
    public void create() {
        logger.info("Will try to createAlipay...");
        Payment payment = Payment.newBuilder().setOrderNo("O101135456").setChannel("alipay").setBody("iphone16G").setAmount("111").setClientIp("127.0.0.1").build();
        Payment result;
        try {
            result = blockingStub.create(payment);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        PaymentClient client = new PaymentClient("localhost", 9090);
        try {
            client.create();
        } finally {
            client.shutdown();
        }
    }
}

package com.flyhtml.payment.grpc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Voucher;

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
        Make.Builder make = Make.newBuilder();
        make.setOrderNo("O101135456");
        make.setChannel("alipay_wap");
        make.setSubject("iphone 7 plus");
        make.setBody("256G,蓝色");
        make.setAmount(748800);
        make.setIp("127.0.0.1");
        make.putExtra("openId", "o0iNcxLAfNPc5rz-2u2-u1D9BauA");
        make.putExtra("notifyUrl", "http://fuliaoyi.com:8082/flyhtml/sds");
        make.putExtra("returnUrl", "http://fuliaoyi.com:8082/flyhtml/sds");
        Make payment = make.build();
        Voucher result;
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

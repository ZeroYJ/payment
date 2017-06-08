package com.flyhtml.payment.grpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flyhtml.payment.common.enums.PayTypeEnum;
import com.flyhtml.payment.common.util.RandomStrs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Query;
import io.grpc.payment.Voucher;

/**
 * @author xiaowei
 * @time 17-4-24 下午3:09
 * @describe
 */
public class PaymentClient {

  private static final Logger logger = Logger.getLogger(PaymentClient.class.getName());

  private final ManagedChannel channel;
  private final PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub;

  public PaymentClient(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    blockingStub = PaymentServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** Say hello to server. */
  public Voucher create() {
    logger.info("Will try to createAlipay...");
    Make.Builder make = Make.newBuilder();
    make.setOrderNo("T_O" + RandomStrs.generate(25));
    make.setChannel(PayTypeEnum.alipay_wap.getName());
    make.setSubject("iphone 7 plus");
    make.setBody("256G,蓝色");
    make.setAmount(1);
    make.setIp("127.0.0.1");
    // make.putExtra("openId", "o0iNcxLAfNPc5rz-2u2-u1D9BauA");
    // make.putExtra("productId", "9as12sdfasd");
    make.putExtra("notifyUrl", "http://helloxw.viphk.ngrok.org/payNotify");
    make.putExtra("returnUrl", "http://helloxw.viphk.ngrok.org/pay/success");
    make.setCustom("custom param");
    Make payment = make.build();
    Voucher result;
    try {
      result = blockingStub.create(payment);
      return result;
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
    }
    return null;
  }

  public Voucher query(String id) {
    try {
      Query query = Query.newBuilder().setId(id).build();
      Voucher vo = blockingStub.query(query);
      return vo;
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
    }
    return null;
  }

  public static Voucher query() {
    PaymentClient client = new PaymentClient("localhost", 9090);
    try {
      Voucher voucher = client.query("pa_9kOlKhSezsnLe1S8k12pkF57dk");
      return voucher;
    } finally {
      try {
        client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static Voucher pay() {
    PaymentClient client = new PaymentClient("localhost", 9090);
    try {
      Voucher voucher = client.create();
      return voucher;
    } finally {
      try {
        client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

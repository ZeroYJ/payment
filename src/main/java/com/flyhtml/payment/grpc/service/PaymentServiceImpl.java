package com.flyhtml.payment.grpc.service;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.flyhtml.payment.channel.alipay.sdk.AlipaySdkSupport;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundApplyResponse;
import com.flyhtml.payment.db.model.Refund;
import com.flyhtml.payment.db.service.RefundService;
import io.grpc.payment.ApplyRefund;
import io.grpc.payment.RefundRes;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.flyhtml.payment.channel.alipay.mapi.AlipayMapiSupport;
import com.flyhtml.payment.channel.wechatpay.WechatSupport;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.common.enums.PayTypeEnum;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.service.PayService;
import com.google.gson.Gson;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Query;
import io.grpc.payment.Voucher;
import io.grpc.stub.StreamObserver;

/**
 * @author xiaowei
 * @time 17-4-5 下午3:34
 * @describe 支付服务GRPC服务
 */
@GRpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

  @Autowired
  private PayService paymentService;
  @Autowired
  private RefundService refundService;
  @Autowired
  private WechatSupport wechatPay;
  @Autowired
  private AlipayMapiSupport alipayMapiPay;
  @Autowired
  private AlipaySdkSupport alipaySdkPay;

  /***
   * 生成支付凭据
   * @param request
   * @param responseObserver
   */
  @Override
  public void create(Make request, StreamObserver<Voucher> responseObserver) {
    try {
      // 判断参数
      if (StringUtils.isAnyBlank(
          request.getOrderNo(),
          request.getChannel(),
          request.getSubject(),
          request.getBody(),
          request.getIp())) {
        throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("参数不全"));
      }
      if (request.getAmount() == 0) {
        throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("金额为0"));
      }
      System.out.println(request);
      String channel = request.getChannel();
      PayTypeEnum payType = PayTypeEnum.getByName(channel);
      if (payType == null) {
        throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("支付渠道未找到"));
      }
      // 判断该订单号是否已经存在
      Pay pay = paymentService.selectOne(new Pay(channel, request.getOrderNo()));
      if (pay != null) {
        throw new StatusRuntimeException(Status.ALREADY_EXISTS.withDescription("订单号重复"));
      }
      String id = "pa_" + RandomStrs.generate(24);
      Pay payment = new Pay();
      payment.setId(id);
      payment.setChannel(channel);
      payment.setOrderNo(request.getOrderNo());
      payment.setIp(request.getIp());
      payment.setAmount(new BigDecimal(request.getAmount()).divide(new BigDecimal(100)));
      payment.setSubject(request.getSubject());
      payment.setBody(request.getBody());
      payment.setCustom(request.getCustom());
      payment.setExpireTime(DateUtils.addHours(new Date(), 48));
      payment.setExtra(new Gson().toJson(request.getExtraMap()));
      switch (payType) {
        case wx_pub: {
          String openId = request.getExtraOrDefault("openId", null);
          String notifyUrl = request.getExtraOrDefault("notifyUrl", null);
          if (StringUtils.isAnyBlank(openId, notifyUrl)) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("缺少extra"));
          }
          JsPayResponse payResponse =
              wechatPay.jsPay(
                  openId,
                  request.getOrderNo(),
                  request.getAmount(),
                  request.getBody(),
                  request.getSubject(),
                  request.getCustom(),
                  request.getIp(),
                  payment.getId());

          Map<String, String> credential = new HashMap<>();
          credential.put("credential", new Gson().toJson(payResponse));
          payment.setCredential(new Gson().toJson(credential));
          break;
        }
        case alipay_wap: {
          String returnUrl = request.getExtraOrDefault("returnUrl", null);
          String notifyUrl = request.getExtraOrDefault("notifyUrl", null);
          if (StringUtils.isAnyBlank(returnUrl, notifyUrl)) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("缺少extra"));
          }
          String form =
              alipaySdkPay.mobilePay(
                  payment.getSubject(),
                  payment.getBody(),
                  payment.getCustom(),
                  payment.getOrderNo(),
                  payment.getAmount().toString(),
                  returnUrl,
                  payment.getId());
          Map<String, String> credential = new HashMap<>();
          credential.put("credential", form);
          payment.setCredential(new Gson().toJson(credential));
          break;
        }
        case wx_qr: {
          String productId = request.getExtraOrDefault("productId", null);
          String notifyUrl = request.getExtraOrDefault("notifyUrl", null);
          if (StringUtils.isAnyBlank(productId, notifyUrl)) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("缺少extra"));
          }
          String qrUrl =
              wechatPay.qrPay(
                  productId,
                  request.getOrderNo(),
                  request.getAmount(),
                  request.getBody(),
                  request.getSubject(),
                  request.getCustom(),
                  request.getIp(),
                  payment.getId());
          Map<String, String> credential = new HashMap<>();
          credential.put("credential", qrUrl);
          payment.setCredential(new Gson().toJson(credential));
          break;
        }
        case alipay_web: {
          String returnUrl = request.getExtraOrDefault("returnUrl", null);
          String notifyUrl = request.getExtraOrDefault("notifyUrl", null);
          if (StringUtils.isAnyBlank(returnUrl, notifyUrl)) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("缺少extra"));
          }
          String form =
              alipayMapiPay.webPay(
                  payment.getSubject(),
                  payment.getBody(),
                  payment.getCustom(),
                  payment.getOrderNo(),
                  payment.getAmount().toString(),
                  returnUrl,
                  payment.getId());
          Map<String, String> credential = new HashMap<>();
          credential.put("credential", form);
          payment.setCredential(new Gson().toJson(credential));
          break;
        }
        default:
          break;
      }
      paymentService.insertSelective(payment);
      Voucher voucher = BeanUtils.toProto(payment, Voucher.class);
      responseObserver.onNext(voucher);
      responseObserver.onCompleted();
    } catch (StatusRuntimeException e) {
      responseObserver.onError(e);
    } catch (Exception e) {
      responseObserver.onError(
          new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    }
  }

  /***
   * 根据paymentId获取payment对象
   * @param request
   * @param responseObserver
   */
  @Override
  public void query(Query request, StreamObserver<Voucher> responseObserver) {
    try {
      String id = request.getId();
      if (id == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("id为空"));
      }
      Pay pay = paymentService.selectById(id);
      if (pay == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("对象未找到"));
      }
      responseObserver.onNext(BeanUtils.toProto(pay, Voucher.class));
      responseObserver.onCompleted();
    } catch (StatusRuntimeException e) {
      responseObserver.onError(e);
    } catch (Exception e) {
      responseObserver.onError(
          new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    }
  }

  /***
   * 申请退款
   * @param request
   * @param responseObserver
   */
  @Override
  public void refund(ApplyRefund request, StreamObserver<RefundRes> responseObserver) {
    try {
      //判断参数是否为空
      String payId = request.getId();
      String desc = request.getDesc();
      String opUserId = request.getOpUserId();
      if (payId == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("id为空"));
      }
      if (desc == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("退款详情为空"));
      }
      if (opUserId == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("操作人id为空"));
      }
      //判断订单是否存在
      Pay pay = paymentService.selectById(payId);
      if (pay == null) {
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("对象未找到"));
      }
      //判断退款金额是否合理
      int reAmount = request.getAmount();
      int amount = pay.getAmount().intValue() * 100;
      if (reAmount <= 0) {
        throw new StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription("退款金额必须大于0"));
      }
      if (reAmount > amount) {
        throw new StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription("超出可退款金额"));
      }
      //根据对应渠道进行退款
      String channel = pay.getChannel();
      PayTypeEnum payType = PayTypeEnum.getByName(channel);
      //退款对象
      Refund refund = new Refund();
      refund.setId("re_" + RandomStrs.generate(24));
      refund.setPayId(payId);
      refund.setOrderNo(pay.getOrderNo());
      refund.setAmount(new BigDecimal(reAmount).divide(new BigDecimal(100)));
      refund.setReason(request.getDesc());
      refund.setOpUserId(request.getOpUserId());
      switch (payType) {
        case wx_pub: {
          RefundApplyResponse response = wechatPay
              .applyRefund(refund.getOrderNo(), refund.getId(), amount, reAmount, opUserId);
          break;
        }
        case alipay_wap: {
          AlipayTradeRefundResponse response = alipaySdkPay
              .applyRefund(refund.getOrderNo(), refund.getId(), refund.getAmount(), opUserId, refund.getReason());
          break;
        }
        case wx_qr: {
          RefundApplyResponse response = wechatPay
              .applyRefund(refund.getOrderNo(), refund.getId(), amount, reAmount, opUserId);
          break;
        }
        case alipay_web: {
          AlipayTradeRefundResponse response = alipaySdkPay
              .applyRefund(refund.getOrderNo(), refund.getId(), refund.getAmount(), opUserId, refund.getReason());
          break;
        }
        default: {
          break;
        }
      }
    } catch (StatusRuntimeException e) {
      responseObserver.onError(e);
    } catch (Exception e) {
      responseObserver.onError(
          new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    }
  }

  /***
   * 根据paymentId批量获取payment对象
   * @param responseObserver
   * @return
   */
  @Override
  public StreamObserver<Query> list(StreamObserver<Voucher> responseObserver) {
    return new StreamObserver<Query>() {
      @Override
      public void onNext(Query query) {

      }

      @Override
      public void onError(Throwable throwable) {
        responseObserver.onError(throwable);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}

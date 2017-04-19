package com.flyhtml.payment.grpc.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.flyhtml.payment.channel.alipay.AlipaySupport;
import com.flyhtml.payment.channel.wechatpay.WechatSupport;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.common.enums.PayTypeEnum;
import com.flyhtml.payment.common.exception.PaymentException;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.service.PayService;
import com.google.gson.Gson;

import io.grpc.Status;
import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
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
    private WechatSupport wechatPay;
    @Autowired
    private AlipaySupport alipay;

    @Override
    public void create(Make request, StreamObserver<Voucher> responseObserver) {
        // 判断参数
        if (StringUtils.isAnyBlank(request.getOrderNo(), request.getChannel(), request.getSubject(), request.getBody(),
                request.getIp())) {
            responseObserver.onError(Status.DATA_LOSS.asRuntimeException());
        }
        String channel = request.getChannel();
        PayTypeEnum payType = PayTypeEnum.getByName(channel);
        if (payType == null) {
            responseObserver.onError(new PaymentException("channel not fonud"));
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
        payment.setDescription(request.getDescription());
        payment.setExtra(new Gson().toJson(request.getExtraMap()));
        switch (payType) {
            case wx_pub: {
                String openId = request.getExtraOrThrow("openId");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isAnyBlank(openId, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                JsPayResponse payResponse = wechatPay.jsPay(openId, request.getOrderNo(), request.getAmount(),
                        request.getBody(), null, request.getIp(), payment.getId());

                Map<String, String> credential = new HashMap<>();
                credential.put("credential", new Gson().toJson(payResponse));
                payment.setCredential(new Gson().toJson(credential));
                break;
            }
            case alipay_wap: {
                String returnUrl = request.getExtraOrThrow("returnUrl");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isAnyBlank(returnUrl, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                String form = alipay.mobilePay(payment.getSubject(), payment.getBody(), payment.getOrderNo(),
                        payment.getAmount().toString(), returnUrl, payment.getId());
                Map<String, String> credential = new HashMap<>();
                credential.put("credential", form);
                payment.setCredential(new Gson().toJson(credential));
                break;
            }
            case wx_qr: {
                String productId = request.getExtraOrThrow("productId");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isAnyBlank(productId, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                String qrUrl = wechatPay.qrPay(productId, request.getOrderNo(), request.getAmount(), request.getBody(),
                        null, request.getIp(), payment.getId());
                Map<String, String> credential = new HashMap<>();
                credential.put("credential", qrUrl);
                payment.setCredential(new Gson().toJson(credential));
                break;
            }
            case alipay_web: {
                String returnUrl = request.getExtraOrThrow("returnUrl");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                String errorUrl = request.getExtraOrThrow("errorUrl");
                if (StringUtils.isAnyBlank(returnUrl, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                String form = alipay.webPay(payment.getSubject(), payment.getBody(), payment.getOrderNo(),
                        payment.getAmount().toString(), returnUrl, errorUrl, payment.getId());
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
    }
}

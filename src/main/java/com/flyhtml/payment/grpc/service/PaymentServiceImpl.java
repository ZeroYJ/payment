package com.flyhtml.payment.grpc.service;

import com.flyhtml.payment.channel.BaseConfig;
import com.flyhtml.payment.channel.alipay.util.AlipayUtil;
import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Payment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.grpc.Status;
import org.apache.commons.lang3.StringUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.flyhtml.payment.channel.wechatpay.WechatPayConfig;
import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.common.enums.PayTypeEnum;
import com.flyhtml.payment.common.exception.PaymentException;
import com.flyhtml.payment.db.service.PaymentService;

import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Voucher;
import io.grpc.stub.StreamObserver;
import me.hao0.common.date.Dates;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowei
 * @time 17-4-5 下午3:34
 * @describe 支付服务GRPC服务
 */
@GRpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void create(Make request, StreamObserver<Voucher> responseObserver) {
        System.out.println(request.toString());
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
        String id = "pa_" + RandomStrs.generate(25);
        Payment payment = new Payment();
        payment.setId(id);
        payment.setGmtCreate(new Date());
        payment.setGmtModified(new Date());
        payment.setIsPay(false);
        payment.setHasRefund(false);
        payment.setChannel(channel);
        payment.setOrderNo(request.getOrderNo());
        payment.setIp(request.getIp());
        payment.setCurrency("cny");
        payment.setAmount(new BigDecimal(request.getAmount()).divide(new BigDecimal(100)));
        payment.setSubject(request.getSubject());
        payment.setBody(request.getBody());
        payment.setDescription(request.getDescription());
        payment.setExtra(new Gson().toJson(request.getExtraMap()));
        System.out.println(new Gson().toJson(payment));
        switch (payType) {
            case wx_pub: {
                String openId = request.getExtraOrThrow("openId");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isAnyBlank(openId, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                Wepay wepay = WepayBuilder.newBuilder(WechatPayConfig.appid, WechatPayConfig.appKey,
                                                      WechatPayConfig.mch_id).build();
                JsPayRequest jsPay = new JsPayRequest();
                jsPay.setOpenId(openId);
                jsPay.setOutTradeNo(request.getOrderNo());
                jsPay.setTotalFee(request.getAmount());
                jsPay.setBody(request.getBody());
                jsPay.setAttach("支付测试");
                jsPay.setClientId(request.getIp());
                jsPay.setNotifyUrl(BaseConfig.NOTIFY_URL);
                jsPay.setTimeStart(Dates.now("yyyyMMddHHmmss"));
                JsPayResponse jsPayResponse = wepay.pay().jsPay(jsPay);
                Map<String, String> credential = new HashMap<>();
                credential.put("credential", new Gson().toJson(jsPayResponse));
                payment.setCredential(new Gson().toJson(credential));
                break;
            }
            case alipay_wap: {
                String returnUrl = request.getExtraOrThrow("returnUrl");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isAnyBlank(returnUrl, notifyUrl)) {
                    responseObserver.onError(new RuntimeException("openId is cannot be null"));
                }
                String form = AlipayUtil.createOrder(payment.getSubject(), payment.getBody(), payment.getOrderNo(),
                                                     payment.getAmount().toString(), returnUrl);
                Map<String, String> credential = new HashMap<>();
                credential.put("credential", form);
                payment.setCredential(new Gson().toJson(credential));
                break;
            }
            default:
                break;
        }
        paymentService.insertSelective(payment);
        responseObserver.onNext(toVoucher(payment));
        responseObserver.onCompleted();
    }

    public static Voucher toVoucher(Payment payment) {
        Voucher.Builder builder = Voucher.newBuilder();
        builder.setId(payment.getId());
        builder.setGmtCreate(payment.getGmtCreate().getTime() / 1000);
        builder.setIsPay(payment.getIsPay());
        builder.setHasRefund(payment.getHasRefund());
        builder.setChannel(payment.getChannel());
        builder.setOrderNo(payment.getOrderNo());
        builder.setIp(payment.getIp());
        builder.setAmount(payment.getAmount().intValue() * 100);
        builder.setCurrency(payment.getCurrency());
        builder.setSubject(payment.getSubject());
        builder.setBody(payment.getBody());
        // builder.setPayTime(payment.getPayTime().getTime());
        // builder.setExpireTime(payment.getExpireTime().getTime());
        builder.setDescription(payment.getDescription());
        builder.putAllCredential(new Gson().fromJson(payment.getCredential(), new TypeToken<Map<String, String>>() {
        }.getType()));
        builder.putAllExtra(new Gson().fromJson(payment.getExtra(), new TypeToken<Map<String, String>>() {
        }.getType()));
        Voucher voucher = builder.build();
        return voucher;
    }
}

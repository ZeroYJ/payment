package com.flyhtml.payment.grpc.service;

import com.flyhtml.payment.channel.BaseConfig;
import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Payment;
import com.google.gson.Gson;
import com.google.protobuf.Parser;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.flyhtml.payment.channel.wechatpay.WechatPayConfig;
import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.common.enums.PayTypeEnum;
import com.flyhtml.payment.common.exception.PayTypeException;
import com.flyhtml.payment.db.service.PaymentService;

import io.grpc.payment.Make;
import io.grpc.payment.PaymentServiceGrpc;
import io.grpc.payment.Voucher;
import io.grpc.stub.StreamObserver;
import me.hao0.common.date.Dates;

import java.math.BigDecimal;

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
            responseObserver.onError(new PayTypeException());
        }
        String id = "pa_" + RandomStrs.generate(29);
        Payment payment = new Payment();
        payment.setId(id);
        payment.setChannel(channel);
        payment.setOrderNo(request.getOrderNo());
        payment.setIp(request.getIp());
        payment.setAmount(new BigDecimal(request.getAmount()).divide(new BigDecimal(100)));
        payment.setSubject(request.getSubject());
        payment.setBody(request.getBody());
        payment.setDescription(request.getDescription());
        payment.setExtra(new Gson().toJson(request.getExtraMap()));
        paymentService.insertSelective(payment);
        switch (payType) {
            case wx_pub:
                String openId = request.getExtraOrThrow("openId");
                String notifyUrl = request.getExtraOrThrow("notifyUrl");
                if (StringUtils.isBlank(openId)) {
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
                break;
            case alipay_wap:
                break;
            default:
                break;
        }
        Parser<Voucher> parser = Voucher.parser();
        responseObserver.onCompleted();
    }
}

package com.flyhtml.payment.channel.alipay;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.flyhtml.payment.channel.alipay.core.Alipay;
import com.flyhtml.payment.channel.alipay.core.AlipayBuilder;
import com.flyhtml.payment.channel.alipay.model.pay.WebPayDetail;
import com.flyhtml.payment.common.util.RandomStrs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.channel.alipay.model.notify.AlipayNotify;
import com.flyhtml.payment.db.model.Pay;

/**
 * @author xiaowei
 * @time 17-4-14 下午2:34
 * @describe 支付宝服务类
 */
@Component
public class AlipaySupport {

    /***
     * 以下参数是使用alipayClient所需要
     */
    @Value("${alipay.gateway}")
    private String       gateway;
    @Value("${alipay.appId}")
    private String       appId;
    @Value("${alipay.privateKey}")
    private String       privateKey;
    @Value("${alipay.publicKey}")
    private String       publicKey;
    @Value("${alipay.mchId}")
    private String       mchId;
    @Value("${alipay.notifyUrl}")
    private String       notifyUrl;
    @Value("${alipay.signType}")
    private String       signType;
    @Value("${alipay.charset}")
    private String       charset;
    @Value("${alipay.format}")
    private String       format;
    @Value("${alipay.timeout}")
    private String       timeout;
    /***
     * 以下是使用mapi网关所需要
     */
    @Value("${alipay.mapi.pId}")
    private String       pId;
    @Value("${alipay.mapi.md5}")
    private String       md5;

    private AlipayClient alipayClient;

    @PostConstruct
    private void init() {
        alipayClient = new DefaultAlipayClient(gateway, appId, privateKey, format, charset, publicKey, signType);
    }

    public static void main(String[] args) {
        Alipay alipay = AlipayBuilder.newBuilder("2088421837490657", "zsustkn9wuwrhymh3omn07ohbnudxpl4").build();
        WebPayDetail payDetail = new WebPayDetail("O" + RandomStrs.generate(20), "Samsung galxy S8", "6888.20");
        payDetail.setNotifyUrl("http://localhost:8080");
        payDetail.setReturnUrl("http://localhost:8080");
        payDetail.setExterInvokeIp("127.0.0.1");
        System.out.println(payDetail.toString());
        String form = alipay.pay().webPay(payDetail);
        System.out.println(form);
    }

    /***
     * @param subject 商品标题
     * @param body 商品描述
     * @param returnUrl 订单号
     * @param amount 订单总金额
     * @return
     */
    public String mobilePay(String subject, String body, String orderId, String amount, String returnUrl,
                            String payId) {
        try {
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
            // 在公共参数中设置回跳和通知地址
            alipayRequest.setReturnUrl(returnUrl);
            alipayRequest.setNotifyUrl(notifyUrl + "/" + payId);
            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setSubject(subject);
            model.setBody(body);
            model.setOutTradeNo(orderId);
            model.setTotalAmount(amount);
            model.setTimeoutExpress(timeout);
            model.setProductCode("QUICK_WAP_PAY");
            alipayRequest.setBizModel(model);
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            return form;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 签名检查
     *
     * @param paramMap 参数
     * @return
     */
    public Boolean verifySign(Map<String, String> paramMap) {
        try {
            boolean bool = AlipaySignature.rsaCheckV1(paramMap, publicKey, charset, signType);
            return bool;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    /***
     * 验证订单准确性
     *
     * @param notify 支付宝通知对象
     * @param pay 支付对象
     * @return
     */
    public Validate verifyNotify(AlipayNotify notify, Pay pay) {
        try {
            // 订单号
            if (!pay.getOrderNo().equals(notify.getOutTradeNo())) {
                return Validate.invalid_out_trade_no;
            }
            // 金额
            if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalAmount())) != 0) {
                return Validate.inaccurate_amount;
            }
            // seller_id
            if (!notify.getSellerId().equals(mchId)) {
                return Validate.inaccurate_seller_id;
            }
            // APPID
            if (!notify.getAppId().equals(appId)) {
                return Validate.inaccurate_appid;
            }
            // 判断是否付款成功
            if (!notify.getTradeStatus().equals("TRADE_SUCCESS")) {
                return Validate.inaccurate_trade_status;
            }
            // 通知重复发送
            if (pay.getIsPay()) {
                return Validate.notify_repeat;
            }
            return Validate.success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 返回支付宝失败信息
     * 
     * @param validate
     * @return
     */
    public String notOk(Validate validate) {
        return validate.getName();
    }

}

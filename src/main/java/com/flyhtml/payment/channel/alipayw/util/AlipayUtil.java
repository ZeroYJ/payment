package com.flyhtml.payment.channel.alipayw.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.channel.alipayw.AlipayConfig;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:03
 * @describe 支付宝工具类
 */
public class AlipayUtil {

    private static AlipayClient alipayClient;

    static {
        alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA2_PRIVATE_KEY,
                                               AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                                               AlipayConfig.ALIPAY_RSA2_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
    }

    /***
     * @param subject 商品标题
     * @param body 商品描述
     * @param returnUrl 订单号
     * @param amount 订单总金额
     * @return
     */
    public static String createOrder(String subject, String body, String orderNo, String amount, String returnUrl,
                                     String notifyUrl) {
        try {
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
            // 在公共参数中设置回跳和通知地址
            alipayRequest.setReturnUrl(returnUrl);
            alipayRequest.setNotifyUrl(notifyUrl);
            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setSubject(subject);
            model.setBody(body);
            model.setOutTradeNo(orderNo);
            model.setTotalAmount(amount);
            model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);
            model.setProductCode("QUICK_WAP_PAY");
            alipayRequest.setBizModel(model);
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            return form;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}

package com.flyhtml.payment.channel.alipay.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.channel.alipay.AlipayConfig;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:03
 * @describe 支付宝工具类
 */
public class AlipayUtil {

    private static AlipayClient alipayClient;

    static {
        alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
                                               AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                                               AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
    }

    /***
     * @param subject 商品标题
     * @param body 商品描述
     * @param out_trade_no 订单号
     * @param total_amount 订单总金额
     * @return
     */
    public static String createOrder(String subject, String body, String out_trade_no, String total_amount) {
        try {
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
            // 在公共参数中设置回跳和通知地址
            alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
            alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setSubject(subject);
            model.setBody(body);
            model.setOutTradeNo(out_trade_no);
            model.setTotalAmount(total_amount);
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
        System.out.println(createOrder("Iphone", "16G", "O121212", "6488"));
    }
}

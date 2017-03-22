package com.flyhtml.payment.alipay.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.alipay.config.AlipayConfig;

import static com.flyhtml.payment.alipay.config.AlipayConfig.ALIPAY_PUBLIC_KEY;
import static com.flyhtml.payment.alipay.config.AlipayConfig.APP_PRIVATE_KEY;

/**
 * Created by xiaowei on 17-3-21.
 */
public class AlipayUtil {

    private static AlipayClient alipayClient;

    static {
        alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, APP_PRIVATE_KEY, "json", "UTF-8",
                                               ALIPAY_PUBLIC_KEY, "RSA2");
    }

    public static void main(String[] args) {
        try {
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
            alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
            alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");// 在公共参数中设置回跳和通知地址
            alipayRequest.setBizContent("{" + "\"out_trade_no\":\"20150320010101002\"," + "\"total_amount\":\"88.88\","
                                        + "\"subject\":\"Iphone6 16G\"," + "\"seller_id\":\"2088123456789012\","
                                        + "\"product_code\":\"QUICK_WAP_PAY\"" + "}");// 填充业务参数
            String body = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(body);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //     String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        //     httpResponse.setContentType("text/html;charset=" + AlipayServiceEnvConstants.CHARSET);
        //     httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        //     httpResponse.getWriter().flush();
    }
}

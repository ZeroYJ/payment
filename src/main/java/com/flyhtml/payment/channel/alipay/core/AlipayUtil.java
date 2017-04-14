package com.flyhtml.payment.channel.alipay.core;

import java.math.BigDecimal;
import java.util.*;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.channel.alibaba.model.enums.AlipayField;
import com.flyhtml.payment.channel.alibaba.model.enums.SignType;
import com.flyhtml.payment.channel.alipay.AlipayConfig;

import com.flyhtml.payment.channel.alipay.enums.Validate;
import com.flyhtml.payment.channel.alipay.model.Notify;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.db.model.Pay;
import me.hao0.common.security.MD5;
import me.hao0.common.util.Strings;
import org.apache.el.parser.BooleanNode;

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

    /***
     * 签名检查
     * 
     * @param paramMap 参数
     * @return
     */
    public static Boolean signCheck(Map<String, String> paramMap) {
        try {
            boolean bool = AlipaySignature.rsaCheckV1(paramMap, AlipayConfig.ALIPAY_RSA2_PUBLIC_KEY,
                                                      AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
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
    public static Validate notifyCheck(Notify notify, Pay pay) {
        try {
            // 订单号
            if (!pay.getOrderNo().equals(notify.getOutTradeNo())) {
                return Validate.INVALID_OUT_TRADE_NO;
            }
            // 金额
            if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalAmount())) != 0) {
                return Validate.INACCURATE_AMOUNT;
            }
            // seller_id
            if (!notify.getSellerId().equals(AlipayConfig.merchantId)) {
                return Validate.INACCURATE_SELLER_ID;
            }
            // APPID
            if (!notify.getAppId().equals(AlipayConfig.APPID)) {
                return Validate.INACCURATE_APPID;
            }
            // 判断是否付款成功
            if (!notify.getTradeStatus().equals("TRADE_SUCCESS")) {
                return Validate.INACCURATE_TRADE_STATUS;
            }
            // 通知重复发送
            if (pay.getIsPay()) {
                return Validate.NOTIFY_REPEAT;
            }
            return Validate.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

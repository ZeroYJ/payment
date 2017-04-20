package com.flyhtml.payment.channel.alipay;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.flyhtml.payment.channel.alipay.core.Alipay;
import com.flyhtml.payment.channel.alipay.core.AlipayBuilder;
import com.flyhtml.payment.channel.alipay.model.notify.AlipayNotify;
import com.flyhtml.payment.channel.alipay.model.pay.WebPayDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.channel.alipay.model.notify.Notify;
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

    private Alipay       alipay;

    @PostConstruct
    private void init() {
        alipayClient = new DefaultAlipayClient(gateway, appId, privateKey, format, charset, publicKey, signType);
        alipay = AlipayBuilder.newBuilder(pId, md5).build();
    }

    /***
     * 支付宝即时到帐
     *
     * @param subject 商品标题
     * @param body 商品描述
     * @param orderId 订单编号
     * @param amount 订单金额
     * @param returnUrl 交易成功地址
     * @param errorUrl 交易失败地址
     * @param payId 对应平台ID
     * @return
     */
    public String webPay(String subject, String body,String custom, String orderId, String amount, String returnUrl, String errorUrl,
                         String payId) {
        WebPayDetail payDetail = new WebPayDetail(orderId, subject, amount);
        payDetail.setReturnUrl(returnUrl);
        payDetail.setNotifyUrl(notifyUrl + "/" + payId);
        payDetail.setErrorNotifyUrl(errorUrl);
        payDetail.setExtraCommonParam(custom);
        return alipay.pay().webPay(payDetail);
    }

    /***
     * 支付宝H5支付
     *
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
        boolean bool = alipay.verify().md5(paramMap);
        return bool;
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
            if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalFee())) != 0) {
                return Validate.inaccurate_amount;
            }
            // seller_id
            if (!notify.getSellerId().equals(pId)) {
                return Validate.inaccurate_seller_id;
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

    /***
     * 返回支付宝success信息
     *
     * @return
     */
    public String ok() {
        return "success";
    }

}

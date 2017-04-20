package com.flyhtml.payment.channel.wechatpay;

import javax.annotation.PostConstruct;

import com.flyhtml.payment.channel.wechatpay.model.notify.WechatNotify;
import com.flyhtml.payment.channel.wechatpay.model.pay.QrPayRequest;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.common.util.Maps;
import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Pay;
import me.hao0.common.http.Http;
import me.hao0.common.security.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:02
 * @describe 微信服务类
 */
@Component
public class WechatSupport {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appKey}")
    private String appKey;

    @Value("${wechat.mchId}")
    private String mchId;

    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    private Wepay  wepay;

    @PostConstruct
    private void init() {
        wepay = WepayBuilder.newBuilder(appId, appKey, mchId).build();
    }

    /***
     * 微信公众号支付
     * 
     * @param openId OpenId
     * @param orderId 订单编号
     * @param totalFee 订单金额（分）
     * @param body 商品描述（商家名称-销售商品类目）
     * @param attach 附加数据
     * @param clientIp 终端IP
     * @return
     */
    public JsPayResponse jsPay(String openId, String orderId, Integer totalFee, String body, String attach,
                               String clientIp, String payId) {
        JsPayRequest jsPay = new JsPayRequest();
        jsPay.setOpenId(openId);
        jsPay.setOutTradeNo(orderId);
        jsPay.setTotalFee(totalFee);
        jsPay.setBody(body);
        jsPay.setAttach(attach);
        jsPay.setClientId(clientIp);
        jsPay.setNotifyUrl(notifyUrl + "/" + payId);
        JsPayResponse payResponse = wepay.pay().jsPay(jsPay);
        return payResponse;
    }

    /***
     * 微信二维码支付
     * 
     * @param productId 商品ID
     * @param orderId 订单ID
     * @param totalFee 总金额
     * @param body 商品描述
     * @param attach 附加参数
     * @param clientIp ip
     * @param payId 对应平台支付ID
     * @return
     */
    public String qrPay(String productId, String orderId, Integer totalFee, String body, String attach, String clientIp,
                        String payId) {
        QrPayRequest qrPay = new QrPayRequest();
        qrPay.setProductId(productId);
        qrPay.setOutTradeNo(orderId);
        qrPay.setTotalFee(totalFee);
        qrPay.setBody(body);
        qrPay.setAttach(attach);
        qrPay.setClientId(clientIp);
        qrPay.setNotifyUrl(notifyUrl + "/" + payId);
        String qrUrl = wepay.pay().qrPay(qrPay, false);
        return qrUrl;
    }

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("mch_id", "1366385702");
        param.put("nonce_str", RandomStrs.generate(20));
        param.put("sign", MD5.generate(Maps.toString(param) + "&key=b81fc761fe654f619f150558c490ea49", false));
        String request = Http.post("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey").body(Maps.toXml(param)).request();
        System.out.println(request);
    }

    /***
     * 微信签名效验
     *
     * @param paramMap
     * @return
     */
    public Boolean verifySign(Map<String, Object> paramMap) {
        return wepay.notifies().verifySign(paramMap);
    }

    /***
     * 验证订单准确性
     *
     * @param notify 微信通知对象
     * @param pay 支付对象
     * @return
     */
    public Validate verifyNotify(WechatNotify notify, Pay pay) {
        try {
            // 订单号
            if (!pay.getOrderNo().equals(notify.getOutTradeNo())) {
                return Validate.invalid_out_trade_no;
            }
            // 金额
            if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalFee()).divide(new BigDecimal(100))) != 0) {
                return Validate.inaccurate_amount;
            }
            // seller_id
            if (!notify.getMchId().equals(mchId)) {
                return Validate.inaccurate_seller_id;
            }
            // APPID
            if (!notify.getAppid().equals(appId)) {
                return Validate.inaccurate_appid;
            }
            // 判断是否付款成功
            if (!notify.getResultCode().equals("SUCCESS")) {
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
     * 返回微信失败信息
     *
     * @param validate 效验枚举
     * @return
     */
    public String notOk(Validate validate) {
        return wepay.notifies().notOk(validate.getName());
    }

    /***
     * 返回微信成功信息
     *
     * @return
     */
    public String ok() {
        return wepay.notifies().ok();
    }
}

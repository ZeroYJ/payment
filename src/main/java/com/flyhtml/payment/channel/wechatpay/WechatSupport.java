package com.flyhtml.payment.channel.wechatpay;

import com.flyhtml.payment.channel.wechatpay.core.Wepay;
import com.flyhtml.payment.channel.wechatpay.core.WepayBuilder;
import com.flyhtml.payment.channel.wechatpay.model.notify.WechatNotify;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.flyhtml.payment.channel.wechatpay.model.pay.PayRequest;
import com.flyhtml.payment.channel.wechatpay.model.pay.QrPayRequest;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundApplyRequest;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundApplyResponse;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundQueryResponse;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.db.model.Pay;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

  @Value("${wechat.cert}")
  private String cert;

  private Wepay wepay;

  @PostConstruct
  private void init() {
    try {
      //证书
      Path path = Paths.get(cert);
      byte[] data = Files.readAllBytes(path);
      wepay = WepayBuilder.newBuilder(appId, appKey, mchId).certPasswd(mchId).certs(data).build();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * * 微信公众号支付
   *
   * @param openId OpenId
   * @param orderId 订单编号
   * @param totalFee 订单金额（分）
   * @param body 商品描述（商家名称-销售商品类目）
   * @param attach 附加数据
   * @param clientIp 终端IP
   */
  public JsPayResponse jsPay(
      String openId,
      String orderId,
      Integer totalFee,
      String body,
      String subject,
      String attach,
      String clientIp,
      String payId) {
    JsPayRequest jsPay = new JsPayRequest();
    jsPay.setOpenId(openId);
    buildPayRequest(jsPay, orderId, totalFee, body, subject, attach, clientIp, payId);
    return wepay.pay().jsPay(jsPay);
  }

  /**
   * * 微信二维码支付
   *
   * @param productId 商品ID
   * @param orderId 订单ID
   * @param totalFee 总金额
   * @param body 商品描述
   * @param attach 附加参数
   * @param clientIp ip
   * @param payId 对应平台支付ID
   */
  public String qrPay(
      String productId,
      String orderId,
      Integer totalFee,
      String body,
      String subject,
      String attach,
      String clientIp,
      String payId) {
    QrPayRequest qrPay = new QrPayRequest();
    qrPay.setProductId(productId);
    buildPayRequest(qrPay, orderId, totalFee, body, subject, attach, clientIp, payId);
    return wepay.pay().qrPay(qrPay, false);
  }

  /***
   * 申请退款
   * @param outTradeNo　商户订单号
   * @param refundNo　退款订单号
   * @param totalFee　订单金额
   * @param refundFee　退款金额
   * @return
   */
  public RefundApplyResponse applyRefund(String outTradeNo, String refundNo, Integer totalFee, Integer refundFee,
      String opUserId) {
    RefundApplyRequest request = new RefundApplyRequest();
    request.setOutTradeNo(outTradeNo);
    request.setOutRefundNo(refundNo);
    request.setTotalFee(totalFee);
    request.setRefundFee(refundFee);
    request.setOpUserId(opUserId);
    return wepay.refund().apply(request);
  }

  /***
   * 根据退款id查询退款信息
   * @param refundId
   * @return
   */
  public RefundQueryResponse queryRefund(String refundId) {
    return wepay.refund().queryByOutRefundNo(refundId);
  }

  /**
   * * 设置一些支付公共参数
   *
   * @param request 支付对象
   * @param orderId 订单ID
   * @param totalFee 总金额
   * @param body 商品标题
   * @param subject 商品详情
   * @param attach 附加参数
   * @param clientIp ip
   * @param payId 对应平台支付ID
   */
  private void buildPayRequest(
      PayRequest request,
      String orderId,
      Integer totalFee,
      String body,
      String subject,
      String attach,
      String clientIp,
      String payId) {
    request.setOutTradeNo(orderId);
    request.setTotalFee(totalFee);
    request.setBody(body);
    request.setDetail(subject);
    request.setAttach(attach);
    request.setClientId(clientIp);
    request.setNotifyUrl(notifyUrl + "/" + payId);
  }

  /**
   * 微信签名效验
   */
  public Boolean verifySign(Map<String, Object> paramMap) {
    return wepay.notifies().verifySign(paramMap);
  }

  /**
   * * 验证订单准确性，返回结果
   *
   * @param notify 微信通知对象
   * @param pay 支付对象
   */
  public Validate verifyNotify(WechatNotify notify, Pay pay) {
    try {
      // 是否为空
      if (pay == null) {
        return Validate.INVALID_OUT_TRADE_NO;
      }
      // 是否已经支付
      if (pay.getIsPay()) {
        return Validate.REPEAT;
      }
      // 订单号
      if (!pay.getOrderNo().equals(notify.getOutTradeNo())) {
        return Validate.INVALID_OUT_TRADE_NO;
      }
      // 金额
      if (pay.getAmount()
          .compareTo(new BigDecimal(notify.getTotalFee()).divide(new BigDecimal(100)))
          != 0) {
        return Validate.INACCURATE_AMOUNT;
      }
      // seller_id
      if (!notify.getMchId().equals(mchId)) {
        return Validate.INACCURATE_SELLER_ID;
      }
      // APPID
      if (!notify.getAppid().equals(appId)) {
        return Validate.INACCURATE_APPID;
      }
      // 判断是否付款成功
      if (!notify.getResultCode().equals("SUCCESS")) {
        return Validate.INACCURATE_TRADE_STATUS;
      }
      return Validate.SUCCESS;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * * 返回微信失败信息
   *
   * @param validate 效验枚举
   */
  public String notOk(Validate validate) {
    return wepay.notifies().notOk(validate.getName());
  }

  /**
   * 返回微信成功信息
   */
  public String ok() {
    return wepay.notifies().ok();
  }
}

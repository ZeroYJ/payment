package com.flyhtml.payment.channel.alipay.sdk;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.flyhtml.payment.channel.alipay.mapi.model.notify.MapiNotify;
import com.flyhtml.payment.channel.alipay.sdk.model.SdkNotify;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.db.model.Pay;
import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaowei
 * @time 17-4-14 下午2:34
 * @describe 支付宝SDK服务类
 */
@Component
public class AlipaySdkSupport {

  /** 以下参数是使用alipayClient所需要 */
  @Value("${alipay.sdk.gateway}")
  private String gateway;

  @Value("${alipay.sdk.appId}")
  private String appId;

  @Value("${alipay.sdk.privateKey}")
  private String privateKey;

  @Value("${alipay.sdk.publicKey}")
  private String publicKey;

  @Value("${alipay.sdk.mchId}")
  private String mchId;

  @Value("${alipay.sdk.notifyUrl}")
  private String notifyUrl;

  @Value("${alipay.sdk.signType}")
  private String signType;

  @Value("${alipay.sdk.charset}")
  private String charset;

  @Value("${alipay.sdk.format}")
  private String format;

  @Value("${alipay.sdk.timeout}")
  private String timeout;

  private AlipayClient alipayClient;

  @PostConstruct
  private void init() {
    alipayClient =
        new DefaultAlipayClient(gateway, appId, privateKey, format, charset, publicKey, signType);
  }

  /**
   * * 支付宝H5支付
   *
   * @param subject 商品标题
   * @param body 商品描述
   * @param returnUrl 订单号
   * @param amount 订单总金额
   */
  public String mobilePay(
      String subject, String body, String orderId, String amount, String returnUrl, String payId) {
    try {
      AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest(); // 创建API对应的request
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
      AlipayTradeWapPayResponse alipay = alipayClient.pageExecute(alipayRequest);
      String form = alipay.getBody();
      return form;
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * * 签名检查
   *
   * @param paramMap 参数
   */
  public Boolean verifySign(Map<String, String> paramMap) {
    try {
      boolean checkV1 = AlipaySignature.rsaCheckV1(paramMap, publicKey, charset, signType);
      return checkV1;
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * * 验证订单准确性
   *
   * @param notify 支付宝通知对象
   * @param pay 支付对象
   */
  public Validate verifyNotify(SdkNotify notify, Pay pay) {
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
      if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalAmount())) != 0) {
        return Validate.INACCURATE_AMOUNT;
      }
      // seller_id
      if (!notify.getSellerId().equals(mchId)) {
        return Validate.INACCURATE_SELLER_ID;
      }
      // app_id
      if (!appId.equals(appId)) {
        return Validate.INACCURATE_APPID;
      }
      // 判断是否付款成功
      if (!notify.getTradeStatus().equals("TRADE_SUCCESS")) {
        return Validate.INACCURATE_TRADE_STATUS;
      }
      return Validate.SUCCESS;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /** 返回支付宝失败信息 */
  public String notOk(Validate validate) {
    return validate.getName();
  }

  /** 返回支付宝success信息 */
  public String ok() {
    return "success";
  }
}

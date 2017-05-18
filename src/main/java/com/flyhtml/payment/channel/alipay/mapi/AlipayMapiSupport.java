package com.flyhtml.payment.channel.alipay.mapi;

import com.flyhtml.payment.channel.alipay.mapi.core.Alipay;
import com.flyhtml.payment.channel.alipay.mapi.core.AlipayBuilder;
import com.flyhtml.payment.channel.alipay.mapi.model.notify.MapiNotify;
import com.flyhtml.payment.channel.alipay.mapi.model.pay.WebPayDetail;
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
 * @describe 支付宝Mapi服务类
 */
@Component
public class AlipayMapiSupport {

  @Value("${alipay.mapi.pId}")
  private String pId;

  @Value("${alipay.mapi.md5}")
  private String md5;

  @Value("${alipay.mapi.notifyUrl}")
  private String notifyUrl;

  private Alipay alipay;

  @PostConstruct
  private void init() {
    alipay = AlipayBuilder.newBuilder(pId, md5).expired("48h").build();
  }

  /**
   * * 支付宝即时到帐
   *
   * @param subject 商品标题
   * @param body 商品描述
   * @param custom 商户自定义参数
   * @param orderId 订单编号
   * @param amount 订单金额
   * @param returnUrl 交易成功地址
   * @param payId 对应平台ID
   */
  public String webPay(
      String subject,
      String body,
      String custom,
      String orderId,
      String amount,
      String returnUrl,
      String payId) {
    WebPayDetail payDetail = new WebPayDetail(orderId, subject, amount, body);
    payDetail.setReturnUrl(returnUrl);
    payDetail.setNotifyUrl(notifyUrl + "/" + payId);
    payDetail.setExtraCommonParam(custom);
    return alipay.pay().webPay(payDetail);
  }

  public Map<String, Object> tradeQuery(String orderNo) {
    Map<String, Object> map = alipay.orders().tradeQuery(orderNo);
    return map;
  }

  /**
   * * 签名检查
   *
   * @param paramMap 参数
   */
  public Boolean verifySign(Map<String, String> paramMap) {
    boolean bool = alipay.verify().md5(paramMap);
    return bool;
  }

  /**
   * * 验证订单准确性
   *
   * @param notify 支付宝通知对象
   * @param pay 支付对象
   */
  public Validate verifyNotify(MapiNotify notify, Pay pay) {
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
      if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalFee())) != 0) {
        return Validate.INACCURATE_AMOUNT;
      }
      // seller_id
      if (!notify.getSellerId().equals(pId)) {
        return Validate.INACCURATE_SELLER_ID;
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

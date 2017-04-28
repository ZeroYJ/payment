package com.flyhtml.payment.web;

import com.alipay.api.AlipayApiException;
import com.flyhtml.payment.channel.alipay.model.notify.AlipayNotify;
import com.flyhtml.payment.channel.wechatpay.model.notify.WechatNotify;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.common.util.Maps;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.model.PayNotify;
import com.google.common.base.Throwables;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import me.hao0.common.date.Dates;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaowei
 * @time 17-3-31 上午11:07
 * @describe 回调控制层
 */
@RestController
@RequestMapping("/notify")
public class CallBackController extends BaseController {

  @RequestMapping("/alipay/{id}")
  public String alipay(@PathVariable("id") String id) throws AlipayApiException, IOException {
    // 验签
    Map<String, String[]> parameterMap = request.getParameterMap();
    Map<String, String> paramMap = new HashMap<>();
    for (String key : parameterMap.keySet()) {
      paramMap.put(key, parameterMap.get(key)[0]);
    }
    Boolean signCheck = alipay.verifySign(paramMap);
    if (!signCheck) {
      return alipay.notOk(Validate.INVALID_SIGNATURE);
    }
    // 验证订单准确性
    Pay pay = payService.selectById(id);
    AlipayNotify notify = BeanUtils.toObject(paramMap, AlipayNotify.class, true);
    Validate validate = alipay.verifyNotify(notify, pay);

    logger.info(">>>>>>>>>>>>>>>>>>>>>>Start Business Logic>>>>>>>>>>>>>>>>>>>>>>");

    Date payTime = Dates.toDate(notify.getGmtPayment());
    String channelNo = notify.getTradeNo();
    String res =
        validate.equals(Validate.SUCCESS)
            ? alipay.ok()
            : validate.equals(Validate.REPEAT) ? alipay.ok() : alipay.notOk(validate);
    notifyLogic(validate.equals(Validate.SUCCESS), pay, notify, payTime, channelNo, res);
    return res;
  }

  @RequestMapping("/wechat/{id}")
  public String wechat(@PathVariable("id") String id) throws IOException {
    // 接收参数
    String notifyXml = getPostRequestBody(request);
    if (notifyXml.isEmpty()) {
      return wechatPay.notOk(Validate.INVALID_BODY);
    }
    // 验签
    Map<String, Object> notifyParams = Maps.toMap(notifyXml);
    if (!wechatPay.verifySign(notifyParams)) {
      logger.error("verify sign failed: {}", notifyParams);
      return wechatPay.notOk(Validate.INVALID_SIGNATURE);
    }
    // 验证订单准确性
    Pay pay = payService.selectById(id);
    WechatNotify notify = BeanUtils.toObject(notifyParams, WechatNotify.class, true);
    Validate validate = wechatPay.verifyNotify(notify, pay);

    logger.info(">>>>>>>>>>>>>>>>>>>>>>Start Business Logic>>>>>>>>>>>>>>>>>>>>>>");

    Date payTime = Dates.toDate(notify.getTimeEnd(), "yyyyMMddHHmmss");
    String channelNo = notify.getTransactionId();
    String res =
        validate.equals(Validate.SUCCESS)
            ? wechatPay.ok()
            : validate.equals(Validate.REPEAT) ? wechatPay.ok() : wechatPay.notOk(validate);
    notifyLogic(validate.equals(Validate.SUCCESS), pay, notify, payTime, channelNo, res);

    return res;
  }

  /**
   * * 支付成功后逻辑操作
   *
   * @param pay 支付对象
   * @param notify 通知对象
   * @param payTime 支付时间
   * @param channelNo 支付渠道订单号
   * @param res 返回渠道数据
   */
  private void notifyLogic(
      boolean isSuccess, Pay pay, Object notify, Date payTime, String channelNo, String res) {
    //插入通知对象
    PayNotify payNotify = new PayNotify();
    payNotify.setNotifyUrl(request.getRequestURI());
    payNotify.setResponseData(res);
    payNotify.setNotifyParam(gson.toJson(notify));
    payNotifyService.insertSelective(payNotify);
    if (isSuccess) {
      // 支付成功对象
      pay.setIsPay(true);
      pay.setChannelNo(channelNo);
      pay.setPayTime(payTime);
      // 异步回调通知对象
      String extra = pay.getExtra();
      Map<String, String> extraMap =
          gson.fromJson(extra, new TypeToken<Map<String, String>>() {
          }.getType());
      PayHooks hooks = new PayHooks();
      hooks.setId(pay.getId());
      hooks.setHooksUrl(extraMap.get("notifyUrl"));
      hooks.setHooksCount(0);
      hooks.setHooksParam(bulidHooksParam(pay));
      // 数据库操作
      payHooksService.insertSelective(hooks);
      hooksTask.run(hooks);
      payService.update(pay);
    }
  }

  /**
   * * 获取request xml内容
   */
  public static String getPostRequestBody(HttpServletRequest request) {
    if (request.getMethod().equals("POST")) {
      StringBuilder sb = new StringBuilder();
      try (BufferedReader br = request.getReader()) {
        char[] charBuffer = new char[128];
        int bytesRead;
        while ((bytesRead = br.read(charBuffer)) != -1) {
          sb.append(charBuffer, 0, bytesRead);
        }
      } catch (IOException e) {
        logger.warn("failed to read request body, cause: {}", Throwables.getStackTraceAsString(e));
      }
      return sb.toString();
    }
    return "";
  }
}

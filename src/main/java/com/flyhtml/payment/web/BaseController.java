package com.flyhtml.payment.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyhtml.payment.common.serializer.DateSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.flyhtml.payment.channel.alipay.AlipaySupport;
import com.flyhtml.payment.channel.wechatpay.WechatSupport;
import com.flyhtml.payment.common.serializer.BigDecimalSerializer;
import com.flyhtml.payment.common.task.PayHooksTask;
import com.flyhtml.payment.common.util.Maps;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.service.PayHooksService;
import com.flyhtml.payment.db.service.PayNotifyService;
import com.flyhtml.payment.db.service.PayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import me.hao0.common.security.MD5;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:36
 * @describe 控制层顶级类
 */
public class BaseController {

  // 在Java类中创建 logger 实例
  protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
  // request,response 不可随意使用
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;

  @Autowired protected PayService payService;
  @Autowired protected PayNotifyService payNotifyService;
  @Autowired protected PayHooksService payHooksService;
  @Autowired protected AlipaySupport alipay;
  @Autowired protected WechatSupport wechatPay;
  @Autowired protected PayHooksTask hooksTask;

  @Value("${payment.key}")
  private String paymentKey;

  protected Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(BigDecimal.class, new BigDecimalSerializer())
          .registerTypeAdapter(Date.class, new DateSerializer())
          .create();

  @ModelAttribute
  public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
    this.session = request.getSession();
  }

  /**
   * * 生成回调参数,加上了sign
   *
   * @param pay
   * @return
   */
  protected String bulidHooksParam(Pay pay) {
    String payJson = gson.toJson(pay);
    Map<String, String> payMap =
        gson.fromJson(payJson, new TypeToken<Map<String, String>>() {}.getType());
    String md5 = MD5.generate(Maps.toString(payMap) + "&key=" + paymentKey, false);
    payMap.put("sign", md5);
    return gson.toJson(payMap);
  }
}

package com.flyhtml.payment.web;

import com.flyhtml.payment.channel.alipay.AlipaySupport;
import com.flyhtml.payment.channel.wechatpay.WechatSupport;
import com.flyhtml.payment.common.serializer.BigDecimalSerializer;
import com.flyhtml.payment.common.serializer.DateSerializer;
import com.flyhtml.payment.common.task.PayHooksTask;
import com.flyhtml.payment.db.service.PayHooksService;
import com.flyhtml.payment.db.service.PayNotifyService;
import com.flyhtml.payment.db.service.PayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:36
 * @describe 控制层顶级类
 */
public class BaseController {

    // 在Java类中创建 logger 实例
    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);
    // request,response 不可随意使用
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected HttpSession         session;

    @Autowired
    protected PayService          payService;
    @Autowired
    protected PayNotifyService    payNotifyService;
    @Autowired
    protected PayHooksService     payHooksService;
    @Autowired
    protected AlipaySupport       alipay;
    @Autowired
    protected WechatSupport       wechatPay;
    @Autowired
    protected PayHooksTask        hooksTask;

    protected Gson                gson   = new GsonBuilder().serializeNulls().registerTypeAdapter(BigDecimal.class,
                                                                                                  new BigDecimalSerializer()).registerTypeAdapter(Date.class,
                                                                                                                                                  new DateSerializer()).create();

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }
}

package com.flyhtml.payment.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.flyhtml.payment.channel.alipay.model.notify.AlipayNotify;
import com.flyhtml.payment.channel.wechatpay.model.notify.WechatNotify;
import com.flyhtml.payment.common.task.PayHooksTask;
import com.google.common.base.Throwables;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.flyhtml.payment.common.enums.Validate;
import com.flyhtml.payment.common.util.Maps;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.model.PayNotify;
import com.google.gson.reflect.TypeToken;

import me.hao0.common.date.Dates;

import javax.servlet.http.HttpServletRequest;

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
            return alipay.notOk(Validate.invalid_signature);
        }
        // 验证订单准确性
        Pay pay = payService.selectById(id);
        AlipayNotify notify = BeanUtils.toObject(paramMap, AlipayNotify.class, true);
        Validate validate = alipay.verifyNotify(notify, pay);
        if (!validate.equals(Validate.success)) {
            return alipay.notOk(validate);
        }
        // TODO business logic

        // 插入通知对象
        PayNotify payNotify = new PayNotify(request.getRequestURI(), alipay.ok(), gson.toJson(paramMap));
        payNotifyService.insertSelective(payNotify);
        // 更新支付对象为已支付状态
        Pay upPay = new Pay(pay.getId(), true, notify.getTradeNo(), Dates.toDate(notify.getGmtPayment()));
        payService.update(upPay);
        // 回调
        logger.debug("start payhooks....");
        String extra = pay.getExtra();
        Map<String, String> extraMap = gson.fromJson(extra, new TypeToken<Map<String, String>>() {
        }.getType());
        PayHooks hooks = new PayHooks(pay.getId(), extraMap.get("notifyUrl"), new Date(), 1, null, gson.toJson(pay));
        payHooksService.insertSelective(hooks);
        return validate.getName();
    }

    @RequestMapping("/wechat/{id}")
    public String wechat(@PathVariable("id") String id) throws IOException {
        // 接收参数
        String notifyXml = getPostRequestBody(request);
        if (notifyXml.isEmpty()) {
            return wechatPay.notOk(Validate.invalid_body);
        }
        // 验签
        Map<String, Object> notifyParams = Maps.toMap(notifyXml);
        if (!wechatPay.verifySign(notifyParams)) {
            logger.error("verify sign failed: {}", notifyParams);
            return wechatPay.notOk(Validate.invalid_signature);
        }
        // 验证订单准确性
        Pay pay = payService.selectById(id);
        if (pay == null) {
            return wechatPay.notOk(Validate.invalid_out_trade_no);
        }
        WechatNotify notify = BeanUtils.toObject(notifyParams, WechatNotify.class, true);
        Validate validate = wechatPay.verifyNotify(notify, pay);
        if (!validate.equals(Validate.success)) {
            return wechatPay.notOk(validate);
        }
        // TODO business logic
        logger.info("verify sign success: {}", notifyParams);

        // 插入通知对象
        PayNotify payNotify = new PayNotify(request.getRequestURI(), wechatPay.ok(), gson.toJson(notifyParams));
        payNotifyService.insertSelective(payNotify);
        // 更新支付对象为已支付状态
        Pay upPay = new Pay(pay.getId(), true, notify.getTransactionId(),
                            Dates.toDate(notify.getTimeEnd(), "yyyyMMddHHmmss"));
        payService.update(upPay);
        // 插入回调对象
        String extra = pay.getExtra();
        Map<String, String> extraMap = gson.fromJson(extra, new TypeToken<Map<String, String>>() {
        }.getType());
        PayHooks hooks = new PayHooks(pay.getId(), extraMap.get("notifyUrl"), 0, gson.toJson(pay));
        payHooksService.insertSelective(hooks);
        // 回调
        logger.debug("start payhooks....");
        hooksTask.run(hooks);
        return wechatPay.ok();
    }

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

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }
}

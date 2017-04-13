package com.flyhtml.payment.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.flyhtml.payment.channel.alipay.AlipayConfig;
import com.flyhtml.payment.channel.alipay.core.AlipayUtil;
import com.flyhtml.payment.channel.alipay.enums.Validate;
import com.flyhtml.payment.channel.alipay.model.Notify;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.model.PayNotify;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.hao0.common.date.Dates;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.asm.tree.InsnList.check;

/**
 * @author xiaowei
 * @time 17-3-31 上午11:07
 * @describe 回调控制层
 */
@RestController
@RequestMapping("/pay")
public class CallBackController extends BaseController {

    @RequestMapping("/{id}")
    public String index(@PathVariable("id") String id, HttpServletRequest request) throws AlipayApiException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        // 验签
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<>();
        for (String key : parameterMap.keySet()) {
            paramMap.put(key, parameterMap.get(key)[0]);
        }
        Boolean signCheck = AlipayUtil.signCheck(paramMap);
        if (!signCheck) {
            return Validate.INVALID_SIGNATURE.getName();
        }
        // 验证订单准确性
        Pay pay = payService.selectById(id);
        Notify notify = BeanUtils.toObject(paramMap, Notify.class, true);
        String notifyCheck = AlipayUtil.notifyCheck(notify, pay);
        // 插入通知对象
        PayNotify payNotify = new PayNotify();
        payNotify.setNotifyParam(new Gson().toJson(parameterMap));
        payNotify.setNotifyUrl(request.getRequestURI());
        payNotify.setResponseData(notifyCheck);
        payNotifyService.insertSelective(payNotify);
        // 更新支付对象为已支付状态
        Pay upPay = new Pay();
        upPay.setId(pay.getId());
        upPay.setIsPay(true);
        upPay.setPayTime(Dates.toDate(notify.getGmtPayment()));
        upPay.setChannelNo(notify.getTradeNo());
        payService.update(upPay);
        // 回调
        logger.debug("start payhooks....");
        String extra = pay.getExtra();
        Map<String, String> extraMap = new Gson().fromJson(extra, new TypeToken<Map<String, String>>() {
        }.getType());
        PayHooks hooks = new PayHooks();
        hooks.setId(pay.getId());
        hooks.setHooksUrl(extraMap.get("notifyUrl"));
        hooks.setHooksParam(new Gson().toJson(pay));
        hooks.setHooksTime(new Date());
        payHooksService.insertSelective(hooks);
        return notifyCheck;
    }

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }

}

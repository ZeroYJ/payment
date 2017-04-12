package com.flyhtml.payment.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.AlipayUtils;
import com.flyhtml.payment.channel.alibaba.core.Verifies;
import com.flyhtml.payment.channel.alipay.AlipayConfig;
import com.flyhtml.payment.channel.alipay.core.Alipay;
import com.flyhtml.payment.channel.alipay.model.Notify;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.db.model.Payment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // 验签
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<>();
        for (String key : parameterMap.keySet()) {
            paramMap.put(key, parameterMap.get(key)[0]);
        }
        boolean check = AlipaySignature.rsaCheckV1(paramMap, AlipayConfig.ALIPAY_RSA2_PUBLIC_KEY, AlipayConfig.CHARSET,
                                                   AlipayConfig.SIGNTYPE);
        System.out.println(check);
        if (!check) {
            return "error";
        }
        // 获取参数
        Payment payment = paymentService.selectById(id);
        Notify notify = BeanUtils.toObject(paramMap, Notify.class, true);
        return "success";
    }

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }

}

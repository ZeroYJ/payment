package com.flyhtml.payment.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.flyhtml.payment.channel.alipay.AlipayConfig;
import com.flyhtml.payment.channel.alipay.model.Notify;
import com.flyhtml.payment.common.util.BeanUtils;
import com.flyhtml.payment.db.model.Pay;
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
        if (!check) {
            return "error";
        }
        // 保存通知数据

        // 获取参数
        Pay pay = payService.selectById(id);
        Notify notify = BeanUtils.toObject(paramMap, Notify.class, true);
        // 订单效验
        // 订单号
        if (!pay.getOrderNo().equals(notify.getOutTradeNo())) {
            return "out_trade_no not found";
        }
        // 金额
        if (pay.getAmount().compareTo(new BigDecimal(notify.getTotalAmount())) != 0) {
            return "error";
        }
        // APPID
        if (!notify.getAppId().equals(AlipayConfig.APPID)) {
            return "error";
        }
        // 判断是否付款成功
        if (!notify.getTradeStatus().equals("TRADE_SUCCESS")) {
            return "error";
        }
        // 更新支付对象为已支付状态
        Pay upPay = new Pay();
        upPay.setId(upPay.getId());
        upPay.setIsPay(true);
        upPay.setPayTime(Dates.toDate(notify.getGmtPayment()));
        upPay.setChannelNo(notify.getTradeNo());
        payService.update(upPay);
        return "success";
    }

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }

}

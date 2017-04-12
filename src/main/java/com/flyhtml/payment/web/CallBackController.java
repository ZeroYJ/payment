package com.flyhtml.payment.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.flyhtml.payment.channel.alipayw.model.Notify;
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
    public String index(@PathVariable("id") String id, HttpServletRequest request) {
        // 验签

        // 获取参数
        Payment payment = paymentService.selectById(id);
        Map<String, String[]> parameterMap = request.getParameterMap();
        Notify notify = BeanUtils.toObject(parameterMap, Notify.class, true);
        return "success";
    }

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }

}

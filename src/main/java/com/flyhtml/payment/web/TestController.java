package com.flyhtml.payment.web;

import com.flyhtml.payment.channel.wechatpay.model.pay.JsPayResponse;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flyhtml.payment.grpc.PaymentClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:10
 * @describe
 */
@RestController
public class TestController extends BaseController {

    @RequestMapping("/create")
    public String create() throws InterruptedException {
        return PaymentClient.pay().getCredentialOrThrow("credential");
    }

    @RequestMapping("/goPay")
    public ModelAndView goPay(ModelAndView md) {
        String credential = PaymentClient.pay().getCredentialOrThrow("credential");
        JsPayResponse jsPayResponse = new Gson().fromJson(credential, JsPayResponse.class);
        md.setViewName("index");
        System.out.println(jsPayResponse);
        md.addObject("jsPay", jsPayResponse);
        return md;
    }

    @RequestMapping("/payNotify")
    public String payNotify() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            System.out.println(s + ":" + parameterMap.get(s)[0]);
        }
        return "success";
    }
}

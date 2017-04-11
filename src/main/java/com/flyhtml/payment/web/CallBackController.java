package com.flyhtml.payment.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/**
 * @author xiaowei
 * @time 17-3-31 上午11:07
 * @describe 回调控制层
 */
@RestController
@RequestMapping("/pay")
public class CallBackController {

    @RequestMapping("/{id}")
    public String index(HttpServletRequest request, @PathVariable("id") String id) {
        request.getHeaderNames();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Gson gson = new Gson();
        String notify = gson.toJson(parameterMap);
        System.out.println(notify);
        return "success";
    }

    @RequestMapping("/pay/success")
    public String html() {
        return "支付成功!";
    }

}

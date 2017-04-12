package com.flyhtml.payment.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flyhtml.payment.grpc.PaymentClient;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:10
 * @describe
 */
@RestController
public class TestController {

    @RequestMapping("/create")
    public String create() throws InterruptedException {
        return PaymentClient.pay().getCredentialOrThrow("credential");
    }
}

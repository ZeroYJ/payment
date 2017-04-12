package com.flyhtml.payment.web;

import com.flyhtml.payment.channel.alipay.core.AlipayBuilder;
import com.flyhtml.payment.channel.alipay.core.Pays;
import com.flyhtml.payment.channel.alipayw.AlipayConfig;
import com.flyhtml.payment.grpc.PaymentClient;
import io.grpc.payment.Voucher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:10
 * @describe
 */
@RestController
public class TestController {

    @RequestMapping("/create")
    public String create() throws InterruptedException {
        AlipayBuilder alipayBuilder = AlipayBuilder.newBuilder("2088421837490657", AlipayConfig.RSA_PRIVATE_KEY);
        Pays pay = alipayBuilder.build().pay();
        return null;
    }
}

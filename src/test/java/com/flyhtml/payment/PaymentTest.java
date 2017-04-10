package com.flyhtml.payment;

import java.math.BigDecimal;
import java.util.List;

import com.flyhtml.payment.common.RandomStrs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flyhtml.payment.db.model.Payment;
import com.flyhtml.payment.db.service.PaymentService;
import com.google.gson.Gson;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaowei
 * @time 17-4-7 上午9:51
 * @describe 支付测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PaymentTest {

    // 在Java类中创建 logger 实例
    private Logger         logger = LoggerFactory.getLogger(PaymentTest.class);
    @Autowired
    private PaymentService paymentService;

    @Test
    public void getAll() {
        Payment payment = new Payment();
        payment.setPage(1);
        payment.setRows(1);
        List<Payment> all = paymentService.selectAll(payment);
        logger.info(new Gson().toJson(all));
    }

    @Test
    public void insert() {
        Payment payment = new Payment();
        String id = "pa_" + RandomStrs.generate(29);
        payment.setId(id);
        payment.setIsPay(true);
        payment.setHasRefund(false);
        payment.setChannel("wx_pub");
        payment.setOrderNo("O1231ASJJDGG");
        payment.setIp("127.0.0.1");
        payment.setAmount(new BigDecimal(200));
        payment.setCurrency("cny");
        payment.setSubject("Iphone");
        payment.setBody("16G");
        paymentService.insertSelective(payment);
        logger.info(id);
    }
}

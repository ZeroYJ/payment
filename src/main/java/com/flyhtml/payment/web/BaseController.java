package com.flyhtml.payment.web;

import com.flyhtml.payment.db.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:36
 * @describe 控制层顶级类
 */
public class BaseController {

    @Autowired
    protected PaymentService paymentService;
}

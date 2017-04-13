package com.flyhtml.payment.web;

import com.flyhtml.payment.db.service.PayHooksService;
import com.flyhtml.payment.db.service.PayNotifyService;
import com.flyhtml.payment.db.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaowei
 * @time 17-4-12 上午10:36
 * @describe 控制层顶级类
 */
public class BaseController {

    // 在Java类中创建 logger 实例
    protected Logger           logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected PayService       payService;
    @Autowired
    protected PayNotifyService payNotifyService;
    @Autowired
    protected PayHooksService  payHooksService;
}

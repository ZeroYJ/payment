package com.flyhtml.payment.common.task;

import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.service.PayHooksService;
import me.hao0.common.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author xiaowei
 * @time 17-4-21 上午11:31
 * @describe 支付回调Task
 */
@Component
public class PayHooksTask {

    private final static Logger logger = LoggerFactory.getLogger(PayHooksTask.class);

    @Autowired
    private PayHooksService     hooksService;

    @Async
    public void run(PayHooks hooks) {
        if (hooks.getResponseData() != null && hooks.getResponseData().equals("success")) {
            return;
        }
        String requestUrl = hooks.getHooksUrl();
        logger.info("*****************Requset Hooks*********************");
        logger.info("request-url : {}", requestUrl);
        logger.info("request-param : {}", hooks.getHooksParam());
        String response = Http.post(requestUrl).connTimeout(30).body(hooks.getHooksParam()).request();
        logger.info("response-data : {}", response);
        logger.info("update hooks...");
        PayHooks update = new PayHooks();
        update.setId(hooks.getId());
        update.setHooksTime(new Date());
        update.setHooksCount(hooks.getHooksCount() + 1);
        update.setResponseData(response.equals("success") ? "success" : "error");
        hooksService.update(update);
    }
}

package com.flyhtml.payment.common.task;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.service.PayHooksService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.hao0.common.http.Http;

/**
 * @author xiaowei
 * @time 17-4-21 上午11:31
 * @describe 支付回调Task
 */
@Component
public class PayHooksTask {

  private static final Logger logger = LoggerFactory.getLogger(PayHooksTask.class);

  @Autowired private PayHooksService hooksService;

  @Async
  public void run(PayHooks hooks) {
    if (hooks.getResponseData() != null && hooks.getResponseData().equals("success")) {
      return;
    }
    String requestUrl = hooks.getHooksUrl();
    logger.info("*****************Requset Hooks*********************");
    logger.info("request-url : {}", requestUrl);
    logger.info("request-param : {}", hooks.getHooksParam());
    Map<String, String> param =
        new Gson()
            .fromJson(hooks.getHooksParam(), new TypeToken<Map<String, String>>() {}.getType());
    String response = Http.post(requestUrl).connTimeout(30).readTimeout(30).params(param).request();
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

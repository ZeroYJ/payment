package com.flyhtml.payment.common.scheduled;

import com.flyhtml.payment.common.task.PayHooksTask;
import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.service.PayHooksService;
import com.flyhtml.payment.db.service.PayNotifyService;
import com.flyhtml.payment.db.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaowei
 * @time 17-4-21 上午11:08
 * @describe 计划任务执行类
 */
@Component
public class ScheduledTaskService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

  private static final long min = 1000 * 60;

  @Autowired private PayService paymentService;
  @Autowired private PayNotifyService payNotifyService;
  @Autowired private PayHooksService payHooksService;
  @Autowired private PayHooksTask payHooksTask;

  @Scheduled(fixedRate = 2 * min)
  public void hooks() {
    logger.info("Start hooks task service....");
    List<PayHooks> payHooks = payHooksService.notSuccessHooks();
    for (PayHooks payHook : payHooks) {
      payHooksTask.run(payHook);
    }
  }
}

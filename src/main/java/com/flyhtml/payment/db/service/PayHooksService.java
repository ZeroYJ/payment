package com.flyhtml.payment.db.service;

import com.flyhtml.payment.db.base.BaseService;
import com.flyhtml.payment.db.mapper.PayHooksMapper;
import com.flyhtml.payment.db.model.PayHooks;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaowei
 * @time 17-4-13 下午12:36
 * @describe 支付回调服务
 */
@Service
public class PayHooksService extends BaseService<PayHooks, PayHooksMapper> {

    public List<PayHooks> notSuccessHooks() {
        return m.notSuccessHooks();
    }
}

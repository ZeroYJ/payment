package com.flyhtml.payment.db.service;

import org.springframework.stereotype.Service;

import com.flyhtml.payment.db.base.BaseService;
import com.flyhtml.payment.db.mapper.PaymentMapper;
import com.flyhtml.payment.db.model.Payment;

/**
 * @author xiaowei
 * @time 17-4-6 下午6:27
 * @describe 支付服务类
 */
@Service
public class PaymentService extends BaseService<Payment, PaymentMapper> {
}

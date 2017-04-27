package com.flyhtml.payment.db.service;

import org.springframework.stereotype.Service;

import com.flyhtml.payment.db.base.BaseService;
import com.flyhtml.payment.db.mapper.PayMapper;
import com.flyhtml.payment.db.model.Pay;

/**
 * @author xiaowei
 * @time 17-4-6 下午6:27
 * @describe 支付服务类
 */
@Service
public class PayService extends BaseService<Pay, PayMapper> {}

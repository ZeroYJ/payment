package com.flyhtml.payment.db.service;

import com.flyhtml.payment.db.mapper.PaymentMapper;
import com.flyhtml.payment.db.model.Payment;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaowei
 * @time 17-4-6 下午6:27
 * @describe 支付服务类
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    public List<Payment> getAll(Payment payment) {
        if (payment.getPage() != null && payment.getRows() != null) {
            Page<Object> objectPage = PageHelper.startPage(payment.getPage(), payment.getRows());
            System.out.println(objectPage);
        }
        return paymentMapper.selectAll();
    }

    public int insert(Payment payment) {
        return paymentMapper.insert(payment);
    }
}

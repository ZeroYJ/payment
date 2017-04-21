package com.flyhtml.payment.db.mapper;

import com.flyhtml.payment.db.base.BaseMapper;
import com.flyhtml.payment.db.model.PayHooks;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PayHooksMapper extends BaseMapper<PayHooks> {

    public List<PayHooks> notSuccessHooks();
}

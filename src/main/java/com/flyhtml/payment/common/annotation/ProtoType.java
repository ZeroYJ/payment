package com.flyhtml.payment.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaowei
 * @time 17-4-11 下午5:17
 * @describe
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ProtoType {

    /**
     * 是否任何情况下都可选
     *
     * @return optional or not
     */
    Class<?> type();
}

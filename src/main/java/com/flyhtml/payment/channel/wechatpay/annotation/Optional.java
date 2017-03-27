package com.flyhtml.payment.channel.wechatpay.annotation;

/**
 * 标记字段是可选的
 */
public @interface Optional {

    /**
     * 是否任何情况下都可选
     * 
     * @return optional or not
     */
    boolean any() default true;
}

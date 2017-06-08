package com.flyhtml.payment.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaowei
 * @time 17-4-11 下午5:17
 * @describe Proto数据类型注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ProtoType {

  /**
   * proto_filed数据类型,
   *
   * <pre>
   *      目前支持转化类型:
   *      JAVA --> Proto
   *      String --> map （String型字符串转Map）
   *      Date --> long (Date转Unix时间戳)
   *      BigDecimal --> int32　(金额元转分)
   * </pre>
   *
   * @return optional or not
   */
  Class<?> type();
}

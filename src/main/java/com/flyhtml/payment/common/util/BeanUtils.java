package com.flyhtml.payment.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import com.flyhtml.payment.common.annotation.ProtoType;
import com.flyhtml.payment.common.serializer.BigDecimalSerializer;
import com.flyhtml.payment.common.serializer.DateSerializer;
import com.flyhtml.payment.db.model.Pay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.Message;

import io.grpc.payment.Voucher;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.BeanFactoryUtils;

/**
 * @author xiaowei
 * @time 17-4-11 上午10:27
 * @describe 转化工具类
 */
public class BeanUtils {

  /**
   * * paramMap转Obejct
   *
   * @param _toCamel 是否需要下划线转驼峰命名
   */
  public static <T extends Object> T toObject(
      Map<String, ?> paramMap, Class<T> clazz, Boolean _toCamel) {
    try {
      if (paramMap.isEmpty()) {
        return null;
      }
      T object = clazz.newInstance();
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields) {
        String key = _toCamel ? Underline2Camel.camel2Underline(field.getName()) : field.getName();
        if (!paramMap.containsKey(key)) {
          continue;
        }
        String value = paramMap.get(key).toString();
        field.setAccessible(true);
        field.set(object, value);
      }
      return object;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /** * 对象转Proto对象 */
  public static <T extends Message> T toProto(Object object, Class<T> clazz) {
    try {
      Class<?> objectClass = object.getClass();
      Method newBuilder = clazz.getMethod("newBuilder");
      Object builder = newBuilder.invoke(null);
      Field[] fields = objectClass.getDeclaredFields();
      for (Field field : fields) {
        String fieldName = field.getName();
        String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getMethod = objectClass.getMethod("get" + name);
        Object value = getMethod.invoke(object);
        if (value == null) {
          continue;
        }
        ProtoType annotation = field.getAnnotation(ProtoType.class);
        Method setMethod;
        try {
          if (annotation != null) {
            if (value instanceof String) {
              if (annotation.type() == Map.class) {
                Map<String, String> map =
                    new Gson()
                        .fromJson(
                            (String) value, new TypeToken<Map<String, String>>() {}.getType());
                setMethod =
                    builder.getClass().getDeclaredMethod("putAll" + name, annotation.type());
                setMethod.invoke(builder, map);
              }
            } else if (value instanceof Date) {
              if (annotation.type() == long.class) {
                long time = ((Date) value).getTime() / 1000;
                setMethod = builder.getClass().getDeclaredMethod("set" + name, annotation.type());
                if (setMethod == null) {
                  continue;
                }
                setMethod.invoke(builder, time);
              }
            } else if (value instanceof BigDecimal) {
              if (annotation.type() == int.class) {
                int intValue = ((BigDecimal) value).multiply(new BigDecimal(100)).intValue();
                setMethod = builder.getClass().getDeclaredMethod("set" + name, annotation.type());
                setMethod.invoke(builder, intValue);
              }
            } else if (value instanceof Integer) {

            } else if (value instanceof Map) {

            } else if (value instanceof Collection) {

            }
          } else {
            Class<?> type = field.getType();
            if (value instanceof Boolean) {
              type = boolean.class;
            }
            setMethod = builder.getClass().getDeclaredMethod("set" + name, type);
            setMethod.invoke(builder, value);
          }
        } catch (NoSuchMethodException exception) {
          continue;
        }
      }
      return (T) builder.getClass().getDeclaredMethod("build").invoke(builder);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}

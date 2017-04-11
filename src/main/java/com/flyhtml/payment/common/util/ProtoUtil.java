package com.flyhtml.payment.common.util;

import com.flyhtml.payment.common.annotation.ProtoType;
import com.flyhtml.payment.db.model.Payment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.Message;
import io.grpc.payment.Voucher;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowei
 * @time 17-4-11 上午10:27
 * @describe
 */
public class ProtoUtil {

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
                if (annotation != null) {
                    if (value instanceof String) {
                        if (annotation.type() == Map.class) {
                            Map<String, String> map = new Gson().fromJson((String) value,
                                                                          new TypeToken<Map<String, String>>() {
                                                                          }.getType());
                            setMethod = builder.getClass().getDeclaredMethod("putAll" + name, annotation.type());
                            setMethod.invoke(builder, map);
                        }

                    } else if (value instanceof Date) {
                        if (annotation.type() == long.class) {
                            long time = ((Date) value).getTime() / 1000;
                            setMethod = builder.getClass().getDeclaredMethod("set" + name, annotation.type());
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
                    setMethod = builder.getClass().getDeclaredMethod("set" + name,
                                                                     annotation != null ? annotation.type() : field.getType());
                    setMethod.invoke(builder, value);
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

    public <T extends Object> T formProto(Message proto, Class<T> clazz) {
        return null;
    }

    public static void main(String[] args) {
        // io.grpc.payment.Voucher$Builder.setId()
        Payment payment = new Payment();
        payment.setId("pa_" + RandomStrs.generate(24));
        payment.setGmtCreate(new Date());
        payment.setBody("abasdfas");
        payment.setAmount(new BigDecimal(6488));
        Map<String, String> sd = new HashMap<>();
        sd.put("sd", "sd");
        payment.setExtra(new Gson().toJson(sd));
        Voucher voucher = toProto(payment, Voucher.class);
        System.out.println(voucher.toString());
    }
}

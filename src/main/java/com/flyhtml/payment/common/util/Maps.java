package com.flyhtml.payment.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.hao0.common.security.MD5;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Strings;

import me.hao0.common.xml.XmlReaders;
import me.hao0.common.xml.XmlWriters;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:19
 * @describe Map, XML转化类
 */
public class Maps {

  /**
   * 转换微信XML为Map(仅支持2级)
   *
   * @param xml xml内容
   * @return Map对象
   */
  public static Map<String, Object> toMap(String xml) {
    xml = xml.replaceAll("(\\r|\\n)", "");
    if (Strings.isNullOrEmpty(xml)) {
      return Collections.emptyMap();
    }
    XmlReaders readers = XmlReaders.create(xml);
    return toMap(readers);
  }

  /**
   * 转换微信XML为Map(仅支持2级)
   *
   * @param readers xmlReaders
   * @return Map对象
   */
  public static Map<String, Object> toMap(XmlReaders readers) {
    return toMap(readers, "xml");
  }

  /***
   * 转换指定tagname下的xml
   * @param readers
   * @param tagName
   * @return
   */
  public static Map<String, Object> toMap(XmlReaders readers, String tagName) {
    Node root = readers.getNode(tagName);
    if (root == null) {
      return Collections.emptyMap();
    }
    NodeList children = root.getChildNodes();
    if (children.getLength() == 0) {
      return Collections.emptyMap();
    }
    Map<String, Object> data = new HashMap<>(children.getLength());
    Node n;
    for (int i = 0; i < children.getLength(); i++) {
      n = children.item(i);
      data.put(n.getNodeName(), n.getTextContent());
    }
    data.remove("#text");
    return data;
  }

  /**
   * Map转换为XML
   *
   * @param params Map参数
   * @return XML字符串
   */
  public static String toXml(final Map<String, String> params) {
    XmlWriters writers = XmlWriters.create();
    for (Map.Entry<String, String> param : params.entrySet()) {
      if (!Strings.isNullOrEmpty(param.getValue())) {
        writers.element(param.getKey(), param.getValue());
      }
    }
    return writers.build();
  }

  /**
   * 支付请求前签名
   *
   * @param params 参数(已经升序, 排出非空值和sign)
   * @return MD5的签名字符串(大写)
   */
  public static String toString(final Map<String, String> params) {
    Map<String, String> validParams = new TreeMap<>();
    validParams.putAll(params);
    StringBuilder signing = new StringBuilder();

    for (Map.Entry<String, String> entry : validParams.entrySet()) {
      if (!me.hao0.common.util.Strings.isNullOrEmpty(entry.getValue())) {
        signing.append(entry.getKey()).append('=').append(entry.getValue()).append("&");
      }
    }

    return signing.toString().substring(0, signing.length() - 1);
  }
}

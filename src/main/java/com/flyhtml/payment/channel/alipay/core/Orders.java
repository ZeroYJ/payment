package com.flyhtml.payment.channel.alipay.core;

import com.flyhtml.payment.channel.alipay.model.enums.AlipayField;
import com.flyhtml.payment.channel.alipay.model.enums.Service;
import java.util.Map;

/**
 * @author xiaowei
 * @time 17-4-28 上午11:28
 * @describe 订单组件
 */
public class Orders extends Component {

  Orders(Alipay alipay) {
    super(alipay);
  }

  /**
   * * 根据商户订单号查询订单
   *
   * @param orderNo
   * @return
   */
  public Map<String, Object> tradeQuery(String orderNo) {
    Map<String, String> ordersParam = bulidOrdersParam(Service.TRADE_QUERY);
    ordersParam.put(AlipayField.OUT_TRADE_NO.field(), orderNo);
    buildMd5SignParams(ordersParam);
    return doPost(ordersParam);
  }

  /**
   * * 构建查询公共参数
   *
   * @param service
   * @return
   */
  private Map<String, String> bulidOrdersParam(Service service) {
    Map<String, String> ordersParam = alipay.queryConfig;
    ordersParam.put(AlipayField.SERVICE.field(), service.value());
    return ordersParam;
  }
}

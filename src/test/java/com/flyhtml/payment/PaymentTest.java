package com.flyhtml.payment;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.flyhtml.payment.channel.alipay.mapi.AlipayMapiSupport;
import com.flyhtml.payment.channel.alipay.sdk.AlipaySdkSupport;
import com.flyhtml.payment.channel.wechatpay.WechatSupport;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundApplyResponse;
import com.flyhtml.payment.channel.wechatpay.model.refund.RefundQueryResponse;
import com.flyhtml.payment.db.model.Refund;
import com.flyhtml.payment.db.service.RefundService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.flyhtml.payment.common.serializer.BigDecimalSerializer;
import com.flyhtml.payment.common.serializer.DateSerializer;
import com.flyhtml.payment.db.model.PayHooks;
import com.flyhtml.payment.db.model.PayNotify;
import com.flyhtml.payment.db.service.PayHooksService;
import com.flyhtml.payment.db.service.PayNotifyService;
import com.flyhtml.payment.grpc.PaymentClient;
import com.google.gson.GsonBuilder;
import io.grpc.payment.Voucher;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flyhtml.payment.common.util.RandomStrs;
import com.flyhtml.payment.db.model.Pay;
import com.flyhtml.payment.db.service.PayService;
import com.google.gson.Gson;

/**
 * @author xiaowei
 * @time 17-4-7 上午9:51
 * @describe 支付测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Import(TestConfiguration.class)
public class PaymentTest {

  // 在Java类中创建 logger 实例
  private Logger logger = LoggerFactory.getLogger(PaymentTest.class);
  @Autowired
  private PayService paymentService;
  @Autowired
  private PayNotifyService payNotifyService;
  @Autowired
  private PayHooksService payHooksService;
  @Autowired
  private RefundService refundService;
  @Autowired
  private AlipayMapiSupport alipay;
  @Autowired
  private AlipaySdkSupport alipaySdk;
  @Autowired
  private WechatSupport wechat;

  private Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(BigDecimal.class,
      new BigDecimalSerializer()).registerTypeAdapter(Date.class,
      new DateSerializer()).create();

  @Test
  public void getAll() {
    // Pay payment = new Pay();
    // payment.setPage(1);
    // payment.setRows(1);
    // List<Pay> all = paymentService.selectAll(payment);
    // logger.info(new Gson().toJson(all));
    Pay pay = new Pay();
    pay.setOrderNo("O33UzF5gCOyvw1E7YyK0vJ6VANvw10m");
    pay.setGmtCreate(new Date());
    pay.setAmount(new BigDecimal("90.58"));
    // Pay pay1 = paymentService.selectOne(pay);
    // System.out.println(pay1);
    String s = gson.toJson(pay);
    System.out.println(s);
  }

  @Test
  public void insert() {
    Pay payment = new Pay();
    String id = "pa_" + RandomStrs.generate(29);
    payment.setId(id);
    payment.setIsPay(true);
    payment.setHasRefund(false);
    payment.setChannel("wx_pub");
    payment.setOrderNo("O1231ASJJDGG");
    payment.setIp("127.0.0.1");
    payment.setAmount(new BigDecimal(200));
    payment.setCurrency("cny");
    payment.setSubject("Iphone");
    payment.setBody("16G");
    paymentService.insertSelective(payment);
    logger.info(id);
  }

  @Test
  public void insertNotify() {
    PayNotify payNotify = new PayNotify();
    payNotify.setResponseData("success");
    payNotify.setNotifyUrl("/v1/12");
    payNotify.setNotifyParam("sadasd");
    int i = payNotifyService.insertSelective(payNotify);
    logger.warn(i + "");
  }

  @Test
  public void insertHooks() {
    PayHooks hooks = new PayHooks();
    hooks.setId(RandomStrs.generate(20));
    hooks.setHooksUrl("localhost");
    hooks.setHooksParam("");
    hooks.setHooksTime(new Date());
    hooks.setHooksCount(0);
    payHooksService.insertSelective(hooks);
  }

  @Test
  public void notSuccess() {
    List<PayHooks> payHooks = payHooksService.notSuccessHooks();
    System.out.println(payHooks);
  }

  @Test
  public void grpcQuery() {
    Voucher pay = PaymentClient.query();
    System.out.println(pay);
  }

  @Test
  public void grpcCreate() {
    PaymentClient paymentClient = new PaymentClient("fuliaoyi.com", 9090);
    Voucher voucher = paymentClient.create();
    System.out.println(voucher);
  }

  @Test
  public void queryAlipay() {
    Map<String, Object> map = alipay.tradeQuery("T_OA8eGpd0JbDUiCOaMJqngdvUgO");
    for (String s : map.keySet()) {
      System.out.println(s);
    }
  }

  @Test
  public void wechatRefund() {
    RefundApplyResponse resp = wechat
        .applyRefund("d00add9d45244f478b9c312253d1bc35", "re_d00add9d45244f478b9c312253d1bc35_1", 1, 1, "111");
    System.out.println(resp);
  }

  @Test
  public void alipayRefund() {
    AlipayTradeRefundResponse response = alipaySdk
        .applyRefund("35ef4d42ae7a471c8c2a9362e10045f0", "re_35ef4d42ae7a471c8c2a9362e10045f0_1",
            new BigDecimal("0.01"),
            "111", "测试退款");
    System.out.println(new Gson().toJson(response));
  }

  @Test
  public void queryRefund() {
    RefundQueryResponse refund = wechat.queryRefund("re_d00add9d45244f478b9c312253d1bc35");
    System.out.println(refund);
  }

  @Test
  public void insertRefund() {
    Refund refund = new Refund();
    refund.setId("re_" + RandomStrs.generate(24));
    refund.setPayId("aaa");
    refund.setStatus(0);
    refund.setOrderNo("bbb");
    refund.setAmount(new BigDecimal(1).divide(new BigDecimal(100)));
    refund.setReason("测试退款");
    refund.setOpUserId("0qwqsadfasdf");
    refundService.insertSelective(refund);
  }
}

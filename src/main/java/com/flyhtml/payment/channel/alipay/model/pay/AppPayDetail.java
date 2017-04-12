package com.flyhtml.payment.channel.alipay.model.pay;

import com.flyhtml.payment.channel.alipay.model.enums.GoodsType;
import com.flyhtml.payment.channel.wechatpay.annotation.Optional;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:32
 * @describe 支付宝APP支付明细
 */
public class AppPayDetail extends PayDetail {

    private static final long serialVersionUID = 7265488308580697604L;

    /**
     * 客户端号，标识客户端
     */
    @Optional
    private String            appId;

    /**
     * 客户端来源
     */
    @Optional
    private String            appenv;

    /**
     * 是否发起实名校验
     */
    @Optional
    private String            rnCheck;

    /**
     * 授权令牌(32)
     */
    @Optional
    private String            externToken;

    /**
     * 商户业务扩展参数
     */
    @Optional
    private String            outContext;

    /**
     * 商品详情
     */
    private String            body;

    /**
     * 商品类型
     */
    @Optional
    private GoodsType         goodsType;

    public AppPayDetail(String outTradeNo, String orderName, String totalFee, String body) {
        super(outTradeNo, orderName, totalFee);
        this.body = body;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppenv() {
        return appenv;
    }

    public void setAppenv(String appenv) {
        this.appenv = appenv;
    }

    public String getRnCheck() {
        return rnCheck;
    }

    public void setRnCheck(String rnCheck) {
        this.rnCheck = rnCheck;
    }

    public String getExternToken() {
        return externToken;
    }

    public void setExternToken(String externToken) {
        this.externToken = externToken;
    }

    public String getOutContext() {
        return outContext;
    }

    public void setOutContext(String outContext) {
        this.outContext = outContext;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "AppPayDetail{" + "appId='" + appId + '\'' + ", appenv='" + appenv + '\'' + ", rnCheck='" + rnCheck
               + '\'' + ", externToken='" + externToken + '\'' + ", outContext='" + outContext + '\'' + ", body='"
               + body + '\'' + ", goodsType=" + goodsType + "} " + super.toString();
    }
}

package com.flyhtml.payment.channel.alipay.mapi.model.pay;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:29
 * @describe 支付宝PC网页支付明细
 */
public class WebPayDetail extends PayDetail {

    private static final long serialVersionUID = -1542442458795168095L;

    /**
     * 客户端Ip
     */
    protected String          exterInvokeIp;

    /**
     * 防钓鱼时间戳
     */
    protected String          antiPhishingKey;

    /**
     * 支付宝错误通知跳转
     */
    protected String          errorNotifyUrl;

    /**
     * 公用回传参数
     */
    protected String          extraCommonParam;

    public WebPayDetail(String outTradeNo, String orderName, String totalFee) {
        super(outTradeNo, orderName, totalFee);
    }

    public String getErrorNotifyUrl() {
        return errorNotifyUrl;
    }

    public void setErrorNotifyUrl(String errorNotifyUrl) {
        this.errorNotifyUrl = errorNotifyUrl;
    }

    public String getExterInvokeIp() {
        return exterInvokeIp;
    }

    public void setExterInvokeIp(String exterInvokeIp) {
        this.exterInvokeIp = exterInvokeIp;
    }

    public String getExtraCommonParam() {
        return extraCommonParam;
    }

    public void setExtraCommonParam(String extraCommonParam) {
        this.extraCommonParam = extraCommonParam;
    }

    public String getAntiPhishingKey() {
        return antiPhishingKey;
    }

    public void setAntiPhishingKey(String antiPhishingKey) {
        this.antiPhishingKey = antiPhishingKey;
    }

    @Override
    public String toString() {
        return "WebPayDetail{" + "exterInvokeIp='" + exterInvokeIp + '\'' + ", antiPhishingKey='" + antiPhishingKey
               + '\'' + ", errorNotifyUrl='" + errorNotifyUrl + '\'' + ", extraCommonParam='" + extraCommonParam + '\''
               + "} " + super.toString();
    }
}

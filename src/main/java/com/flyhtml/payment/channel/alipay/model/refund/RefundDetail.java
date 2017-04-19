package com.flyhtml.payment.channel.alipay.model.refund;

import java.io.Serializable;
import java.util.List;

import com.flyhtml.payment.channel.alipay.annotation.Optional;

/**
 * @author xiaowei
 * @time 17-4-12 下午3:41
 * @describe 退款明细
 */
public class RefundDetail implements Serializable {

    private static final long      serialVersionUID = -145560925778001071L;

    /**
     * 服务器异步通知页面路径
     */
    @Optional
    private String                 notifyUrl;

    /**
     * 退款批次号
     */
    private String                 batchNo;

    /**
     * 单笔数据集
     */
    private List<RefundDetailData> detailDatas;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<RefundDetailData> getDetailDatas() {
        return detailDatas;
    }

    public void setDetailDatas(List<RefundDetailData> detailDatas) {
        this.detailDatas = detailDatas;
    }

    /**
     * 格式化退款数据
     * 
     * @return 退款数据
     */
    public String formatDetailDatas() {
        StringBuilder details = new StringBuilder();
        for (RefundDetailData data : detailDatas) {
            details.append(data.format()).append("#");
        }
        details.deleteCharAt(details.length() - 1);
        return details.toString();
    }

    @Override
    public String toString() {
        return "RefundDetail{" + "notifyUrl='" + notifyUrl + '\'' + ", batchNo='" + batchNo + '\'' + ", detailDatas="
               + detailDatas + '}';
    }
}

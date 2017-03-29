package com.flyhtml.payment.channel.wechatpay.core;

import static me.hao0.common.util.Preconditions.checkNotNull;
import static me.hao0.common.util.Preconditions.checkNotNullAndEmpty;

import java.util.*;

import com.flyhtml.payment.channel.wechatpay.exception.WepayException;
import com.flyhtml.payment.channel.wechatpay.model.bill.*;
import com.flyhtml.payment.channel.wechatpay.model.enums.BillType;
import com.flyhtml.payment.channel.wechatpay.model.enums.WepayField;
import com.flyhtml.payment.channel.wechatpay.util.Maps;
import com.flyhtml.payment.channel.wechatpay.util.RandomStrs;

import me.hao0.common.http.Http;
import me.hao0.common.json.Jsons;
import me.hao0.common.xml.XmlReaders;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:04
 * @describe 账单组件
 */
public class Bills extends Component {

    /**
     * 下载账单
     */
    private static final String DOWNLOAD        = "https://api.mch.weixin.qq.com/pay/downloadbill";

    private static final String LINE_SEPARATOR  = "\\n";

    private static final String FIELD_SEPARATOR = ",`";

    Bills(Wepay wepay) {
        super(wepay);
    }

    /**
     * 查询所有账单
     * 
     * @param deviceInfo 微信支付分配的终端设备号，填写此字段，只下载该设备号的对账单
     * @param date 账单的日期
     * @return 账单明细
     */
    public BillDetail<CommonBill> queryAll(String deviceInfo, String date) {
        String data = query(deviceInfo, date, BillType.ALL);
        return renderBillDetail(data, BillFields.ALL, CommonBill.class);
    }

    /**
     * 查询交易成功账单
     * 
     * @param deviceInfo 微信支付分配的终端设备号，填写此字段，只下载该设备号的对账单
     * @param date 账单的日期
     * @return 账单明细
     */
    public BillDetail<Bill> querySuccess(String deviceInfo, String date) {
        String data = query(deviceInfo, date, BillType.SUCCESS);
        return renderBillDetail(data, BillFields.SUCCESS, Bill.class);
    }

    /**
     * 查询退款账单
     * 
     * @param deviceInfo 微信支付分配的终端设备号，填写此字段，只下载该设备号的对账单
     * @param date 账单的日期
     * @return 账单明细
     */
    public BillDetail<RefundBill> queryRefund(String deviceInfo, String date) {
        String data = query(deviceInfo, date, BillType.REFUND);
        return renderBillDetail(data, BillFields.REFUND, RefundBill.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends Bill> BillDetail<T> renderBillDetail(String billData, List<String> fields, Class<T> billClazz) {
        String[] dataLines = billData.split(LINE_SEPARATOR);
        if (dataLines.length > 0) {
            List<T> bills = new ArrayList<>();
            T bill;
            for (int i = 1; i < dataLines.length - 2; i++) {
                bill = renderBill(dataLines[i], fields, billClazz);
                bills.add(bill);
            }
            String countData = dataLines[dataLines.length - 1];
            BillCount count = renderCount(countData);
            return new BillDetail<>(bills, count);
        }
        return BillDetail.empty();
    }

    private BillCount renderCount(String countData) {
        // remove first `
        countData = countData.substring(1).replaceAll("\\r", "");
        String[] fieldVals = countData.split(FIELD_SEPARATOR);
        Map<String, Object> billCount = new HashMap<>();
        List<String> fieldNames = BillFields.COUNT;
        for (int i = 0; i < fieldNames.size(); i++) {
            billCount.put(fieldNames.get(i), fieldVals[i]);
        }
        return Jsons.DEFAULT.fromJson(Jsons.DEFAULT.toJson(billCount), BillCount.class);
    }

    private <T extends Bill> T renderBill(String dataLine, List<String> fieldNames, Class<T> billClazz) {
        // remove first `
        dataLine = dataLine.substring(1).replaceAll("\\r", "");
        String[] fieldVals = dataLine.split(FIELD_SEPARATOR);
        Map<String, Object> billData = new HashMap<>();
        for (int i = 0; i < fieldNames.size(); i++) {
            billData.put(fieldNames.get(i), fieldVals[i]);
        }
        return Jsons.DEFAULT.fromJson(Jsons.DEFAULT.toJson(billData), billClazz);
    }

    /**
     * 查询账单
     * 
     * @param deviceInfo 微信支付分配的终端设备号，填写此字段，只下载该设备号的对账单
     * @param date 账单的日期
     * @param type 账单类型
     * @return 账单数据
     */
    public String query(String deviceInfo, String date, BillType type) {
        checkNotNullAndEmpty(date, "date");
        checkNotNull(type, "bill type can't be null");
        Map<String, String> downloadParams = buildDownloadParams(deviceInfo, date, type);
        String billData = Http.post(DOWNLOAD).body(Maps.toXml(downloadParams)).request();
        if (billData.startsWith("<xml>")) {
            XmlReaders readers = XmlReaders.create(billData);
            throw new WepayException(readers.getNodeStr(WepayField.RETURN_CODE),
                                     readers.getNodeStr(WepayField.RETURN_MSG));
        }
        return billData;
    }

    private Map<String, String> buildDownloadParams(String deviceInfo, String date, BillType type) {
        Map<String, String> downloadParams = new TreeMap<>();

        buildConfigParams(downloadParams);

        put(downloadParams, WepayField.NONCE_STR, RandomStrs.generate(16));
        put(downloadParams, WepayField.BILL_TYPE, type.type());
        put(downloadParams, WepayField.BILL_DATE, date);

        putIfNotEmpty(downloadParams, WepayField.DEVICE_INFO, deviceInfo);

        buildSignParams(downloadParams);

        return downloadParams;
    }
}

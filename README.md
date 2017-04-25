辅料易支付GRPC服务
================================

辅料易支付GRPC服务,用于对接MRP2系统以及辅料易平台支付环节，包括支付宝，微信以及银联支付。

相关
--------------------------------
* 测试地址：fuliaoyi.com:9090
* <a href="https://github.com/fuliaoyi/Zed/blob/master/src/main/proto/payment.proto">proto文件</a>
* 创建订单[create]
```$xslt
参数(Make):
string orderNo = 1;                         // 商户订单ID
string channel = 2;                         // 支付渠道，详情见支付渠道说明
string subject = 3;                         // 商品标题
string body = 4;                            // 商品描述
int32 amount = 5;                           // 订单金额，单位为分
string ip = 6;                              // 调用支付的IP
map<string, string> extra = 7;              // 支付方式所需额外参数，详情见支付渠道说明
string custom = 8;                          // 商户自定义参数

结果(Voucher):
string id = 1;                              // 支付Id
int64 gmtCreate = 2;                        // 创建时间，unix时间戳
bool isPay = 3;                          　　// 是否支付
bool hasRefund = 4;                         // 是否包含退款
string channel = 5;                         // 支付渠道，详情见支付渠道说明
string orderNo = 6;                         // 商户订单ID
string ip = 7;                              // 调用支付的IP
int32 amount = 8;                           // 订单金额，单位为分
string currency = 9;                        // 货币代码
string subject = 10;                        // 商品标题
string body = 11;                           // 商品描述
int64 payTime = 12;                         // 支付时间，unix时间戳
int64 expireTime = 13;                      // 过期时间，unix时间戳
string custom = 14;                         // 商户自定义参数
map<string, string> credential = 15;        // 支付凭据，详情见支付凭据说明
map<string, string> extra = 16;             // 商户自定义参数
```
* 支付渠道说明
```$xslt
*所有支付渠道均需要参数[notifyUrl],此为支付成功后的通知商户的通知地址
wx_pub:
    微信公众号支付
    所需额外参数:
        openId:用户对应公众号的openId

wx_qr:
    微信扫码支付
    所需额外参数:
        productId:订单对应商品ID
        
alipay_wap:
    支付宝手机网页支付
    所需额外参数:
        returnUrl:支付成功后返回的页面地址
        
alipay_web:
    支付宝即时到帐支付
    所需额外参数:
        returnUrl:支付成功后返回的页面地址
        errorUrl:支付失败后返回的页面地址
```
* 支付凭据说明
```$xslt
*支付凭据需要取结果返回的credential里的credential对象，例Java: credential.get("credential")
wx_pub:
    credential：json串,用于jssdk调起支付

wx_qr:
    credential：微信二维码地址，例: weixin://wxpay/bizpayurl?pr=Q3DAHiX
    获取到地址后可以通过第三方api将地址转换为二维码，用户用微信扫码即可支付
        
alipay_wap:
    credential：用于生成支付宝订单的自动提交form,格式如<form ...><input...></from>
    获取到此form后直接输出到页面即可进行支付
        
alipay_web:
    credential：同alipay_wap
```
* 支付成功回调参数
```$xslt
string id;              //支付id
long gmtCreate;         //创建时间
long gmtModified;       //修改时间
bool isPay;             //是否已付款
bool hasRefund;         //是否存在退款信息
string channel;         //支付渠道
string orderNo;         //商户订单id
string ip;              //客户端ip
decimal amount;         //订单金额
string currency;        //货币代码
string subject;         //商品标题
string body;            //商品描述信息
string channelNo;       //渠道交易号
long payTime;           //支付时间
long expireTime;        //失效时间
string custom;          //自定义数据
string credential;      //支付凭据
string extra;           //额外参数
```
###################################支付服务创库脚本##################################
##付款表
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id`           VARCHAR(32)   NOT NULL COMMENT '主键',
  `gmt_create`   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_pay`       TINYINT(1)    NOT NULL COMMENT '是否已付款',
  `has_refund`   TINYINT(1)    NOT NULL COMMENT '是否存在退款信息',
  `channel`      VARCHAR(10)   NOT NULL COMMENT '付款渠道',
  `order_no`     VARCHAR(32)   NOT NULL COMMENT '订单ID',
  `ip`           VARCHAR(20)   NOT NULL COMMENT '客户端IP',
  `amount`       DECIMAL(10,2) NOT NULL COMMENT '订单金额',
  `currency`     VARCHAR(3)    NOT NULL COMMENT '货币代码,目前仅支持cny',
  `subject`      VARCHAR(32)   NOT NULL COMMENT '商品标题',
  `body`         VARCHAR(128)  NOT NULL COMMENT '商品描述信息',
  `pay_time`     DATETIME      NULL COMMENT '支付时间',
  `expire_time`  DATETIME      NULL COMMENT '失效时间',
  `credential`   TEXT          NULL COMMENT '支付凭证,JSON格式',
  `extra`        TEXT          NULL COMMENT '额外参数,JSON格式',
  `description`  VARCHAR(100)  NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT '付款表';
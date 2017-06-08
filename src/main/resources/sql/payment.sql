###################################支付服务创库脚本##################################
USE `fly_payment`;
##支付表
DROP TABLE IF EXISTS `pay`;
CREATE TABLE `pay` (
  `id`           VARCHAR(32)   NOT NULL COMMENT '主键',
  `gmt_create`   DATETIME               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_pay`       TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '是否已付款',
  `has_refund`   TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '是否存在退款信息',
  `channel`      VARCHAR(10)   NOT NULL COMMENT '支付渠道',
  `order_no`     VARCHAR(32)   NOT NULL COMMENT '订单ID',
  `ip`           VARCHAR(20)   NOT NULL COMMENT '客户端IP',
  `amount`       DECIMAL(10,2) NOT NULL COMMENT '订单金额',
  `currency`     VARCHAR(3)    NOT NULL DEFAULT 'cny' COMMENT '货币代码,目前仅支持cny',
  `subject`      VARCHAR(32)   NOT NULL COMMENT '商品标题',
  `body`         VARCHAR(128)  NOT NULL COMMENT '商品描述信息',
  `channel_no`   VARCHAR(50)   NULL COMMENT '支付渠道交易号',
  `pay_time`     DATETIME      NULL COMMENT '支付时间',
  `expire_time`  DATETIME      NULL COMMENT '失效时间',
  `credential`   TEXT          NULL COMMENT '支付凭证,JSON格式',
  `extra`        TEXT          NULL COMMENT '额外参数,JSON格式',
  `custom`       VARCHAR(100)  NULL COMMENT '自定义数据',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT '支付表';

##支付通知表
DROP TABLE IF EXISTS `pay_notify`;
CREATE TABLE `pay_notify` (
  `id`            INT(9)       NOT NULL  AUTO_INCREMENT COMMENT '主键',
  `gmt_create`    DATETIME               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `notify_url`    VARCHAR(200)  NOT NULL COMMENT '通知地址',
  `notify_param`  TEXT         NOT NULL COMMENT '通知参数',
  `response_data` VARCHAR(200) NOT NULL COMMENT '响应数据',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT '支付通知表';

##支付回调表
DROP TABLE IF EXISTS `pay_hooks`;
CREATE TABLE `pay_hooks` (
  `id`            VARCHAR(32)  NOT NULL COMMENT '主键,同pay_id',
  `gmt_create`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `hooks_url`     VARCHAR(200)  NOT NULL COMMENT '回调地址',
  `hooks_param`   TEXT         NOT NULL COMMENT '回调参数',
  `hooks_count`   INT(2)       NOT NULL COMMENT '已回调次数',
  `hooks_time`    DATETIME     NULL COMMENT '最新回调时间',
  `response_data` VARCHAR(200) NULL COMMENT '响应数据',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT '支付回调表';

##退款表
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
  `id`            VARCHAR(32) NOT NULL COMMENT '主键',
  `gmt_create`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_succeed`    TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否成功',
  `status`        INT(1)      NOT NULL COMMENT '0=处理中,1=成功,2=失败',
  `pay_id`        VARCHAR(32) NOT NULL COMMENT '支付对象ID',
  `order_no`      VARCHAR(32) NOT NULL COMMENT '订单ID',
  `amount`        DECIMAL(10,2) NOT NULL COMMENT '退款金额',
  `reason`          VARCHAR(255)  NOT NULL COMMENT '退款原因',
  `op_user_id`      VARCHAR(32) NOT NULL COMMENT '操作人ID',
  `succeed_time`  DATETIME NULL COMMENT '退款成功时间',
  `error_code`    VARCHAR(100) NULL  COMMENT '退款错误码',
  `error_msg`     VARCHAR(100) NULL  COMMENT '退款错误信息',
  `remarks`       TEXT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT '退款表';
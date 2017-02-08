/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 8.0.0-dmr : Database - outsourced
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`outsourced` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `outsourced`;

/*Table structure for table `commodity` */

DROP TABLE IF EXISTS `commodity`;

CREATE TABLE `commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品表',
  `commodity_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品名称',
  `barcode` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '条形码编码',
  `inventory` int(11) NOT NULL DEFAULT '0' COMMENT '库存量',
  `reserve` int(11) NOT NULL DEFAULT '0' COMMENT '预定量',
  `sell` int(11) NOT NULL DEFAULT '0' COMMENT '售出量',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '商品状态',
  `commodity_doc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品简介',
  `long_text` text COLLATE utf8_unicode_ci COMMENT '详细介绍',
  `picture_one` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品图片1',
  `picture_two` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品图片2',
  `pricture_tree` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品图片3',
  `create_user_id` int(11) NOT NULL COMMENT '商品录入人id',
  `coupon_type_name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '可用优惠券类型名称',
  `coupon_type_id` int(11) DEFAULT NULL COMMENT '可使用优惠券类型id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `picture_tree` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `commodity_pay` */

DROP TABLE IF EXISTS `commodity_pay`;

CREATE TABLE `commodity_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付流水信息表',
  `commodity_id` int(11) NOT NULL COMMENT '商品id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `commodity_name` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `commodity_num` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `commodity_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品金额',
  `coupon_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `coupon_id` int(11) DEFAULT NULL COMMENT '优惠券id',
  `coupon_type` int(11) DEFAULT NULL COMMENT '优惠券类型',
  `coupon_name` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '优惠券名称',
  `courier_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '快递名称',
  `courier_fees` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT '快递费用',
  `courier_code` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '快递单号',
  `pay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `pay_channel` int(11) DEFAULT NULL COMMENT '支付渠道1.微信2.支付宝',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '支付状态0,未支付,1已支付,2已取消',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '支付流水创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `commodity_water` */

DROP TABLE IF EXISTS `commodity_water`;

CREATE TABLE `commodity_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单流水表',
  `water_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '流水码',
  `commodity_id` int(11) NOT NULL COMMENT '商品编码',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `courier_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '快递名称',
  `courier_fees` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT '快递费用',
  `total_prices` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `user_id` int(11) NOT NULL COMMENT '订单用户',
  `pay_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单生成时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `coupon` */

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券表',
  `coupon_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL COMMENT '优惠券名称',
  `coupon_amount` int(11) NOT NULL DEFAULT '1' COMMENT '优惠金额',
  `coupon_type` int(11) NOT NULL DEFAULT '0' COMMENT '优惠券类型',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '优惠券状态 可用不可用',
  `user_id` int(11) NOT NULL COMMENT '优惠券绑定用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '优惠券发放时间',
  `update_time` datetime DEFAULT NULL COMMENT '优惠券使用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `coupon_config` */

DROP TABLE IF EXISTS `coupon_config`;

CREATE TABLE `coupon_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券配置表(优惠券类型id)',
  `coupon_type_name` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '优惠券类型名称',
  `context` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '有效性0无效 1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '类型创建时间',
  `status_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '有效期截至时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `courier` */

DROP TABLE IF EXISTS `courier`;

CREATE TABLE `courier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流信息表',
  `courier_code` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '快递单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `shopping_car` */

DROP TABLE IF EXISTS `shopping_car`;

CREATE TABLE `shopping_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车',
  `commodity_id` int(11) NOT NULL COMMENT '商品id',
  `commodity_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品名称',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `num` int(11) NOT NULL COMMENT '商品数量',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '加入购物车时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统配置表',
  `sys_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '配置名',
  `sys_key` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '配置键',
  `sys_value` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '配置值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '配置时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `user_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用昵称',
  `phone` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT '手机号',
  `email` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `password` varchar(33) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `salt` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '密钥',
  `ip` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0.0.0.1' COMMENT 'ip',
  `sex` varchar(1) COLLATE utf8_unicode_ci DEFAULT '男' COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份号',
  `address` varchar(115) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '居住地址',
  `invite_code` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邀请码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户有效性',
  `openid` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信openId',
  `image` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `type` int(11) NOT NULL COMMENT '用户权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `user_address` */

DROP TABLE IF EXISTS `user_address`;

CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址表',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `recipient_name` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '收件人名称',
  `recipient_phone` varchar(18) COLLATE utf8_unicode_ci NOT NULL COMMENT '收件人手机号',
  `province` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `county` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区县',
  `specific_addrss` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '具体地址',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 是否默认',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `specific_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `id_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_info_id` int(11) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `update_date` datetime NOT NULL,
  `user_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `user_oauth` */

DROP TABLE IF EXISTS `user_oauth`;

CREATE TABLE `user_oauth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户访问权限控制表',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `token` varchar(33) COLLATE utf8_unicode_ci NOT NULL COMMENT 'token',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '用户权限',
  `valid_time` int(3) NOT NULL DEFAULT '6' COMMENT '有效时间(小时)',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

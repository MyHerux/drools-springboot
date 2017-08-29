/*
Navicat MySQL Data Transfer

Source Server         : MyDB
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : xus

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-08-29 20:17:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for drools_rule
-- ----------------------------
DROP TABLE IF EXISTS `drools_rule`;
CREATE TABLE `drools_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL,
  `rule` mediumtext CHARACTER SET utf8mb4,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `visible` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of drools_rule
-- ----------------------------
INSERT INTO `drools_rule` VALUES ('3', 'rule_005', 'package com.xu.drools;\r\nimport com.xu.drools.bean.Person;\r\nrule \"2\"\r\n	when\r\n        $p : Person(age < 30);\r\n    then\r\n		System.out.println(\"hello, young xu2!\");\r\n		$p.setDesc(\"young \"+$p.getName());\r\n		retract($p)\r\nend', '2017-01-17 10:43:14', '2017-08-29 20:15:37', '1');

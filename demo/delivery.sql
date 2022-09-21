/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : delivery

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2022-09-21 21:35:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `cid` int(11) NOT NULL,
  `no_load_power` double NOT NULL,
  `max_power` double NOT NULL,
  `speed` int(11) NOT NULL,
  `max_load` int(11) NOT NULL,
  `battery` double NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1', '1', '1.2', '1', '1', '1.2');
INSERT INTO `car` VALUES ('2', '1', '1.2', '1', '1', '1.2');
INSERT INTO `car` VALUES ('3', '1', '1.2', '1', '1', '1.2');

-- ----------------------------
-- Table structure for car_station
-- ----------------------------
DROP TABLE IF EXISTS `car_station`;
CREATE TABLE `car_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `longitude` decimal(10,6) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  `big_car_status` tinyint(1) NOT NULL DEFAULT '1',
  `big_car_id` int(11) NOT NULL DEFAULT '1',
  `middle_car_status` tinyint(1) NOT NULL DEFAULT '1',
  `middle_car_id` int(11) NOT NULL DEFAULT '2',
  `small_car_status` tinyint(1) NOT NULL DEFAULT '1',
  `small_car_id` int(11) NOT NULL DEFAULT '3',
  PRIMARY KEY (`id`),
  KEY `big_car_id` (`big_car_id`),
  KEY `middle_car_id` (`middle_car_id`),
  KEY `small_car_id` (`small_car_id`),
  KEY `name` (`name`),
  CONSTRAINT `big_car_id` FOREIGN KEY (`big_car_id`) REFERENCES `car` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `middle_car_id` FOREIGN KEY (`middle_car_id`) REFERENCES `car` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `small_car_id` FOREIGN KEY (`small_car_id`) REFERENCES `car` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_station
-- ----------------------------
INSERT INTO `car_station` VALUES ('1', 'C1', '117.192553', '31.759471', '1', '1', '1', '2', '1', '3');
INSERT INTO `car_station` VALUES ('2', 'C2', '117.205943', '31.757110', '1', '1', '1', '2', '1', '3');
INSERT INTO `car_station` VALUES ('3', 'C3', '117.228028', '31.740973', '1', '1', '1', '2', '1', '3');
INSERT INTO `car_station` VALUES ('4', 'C4', '117.220845', '31.768617', '1', '1', '1', '2', '1', '3');
INSERT INTO `car_station` VALUES ('5', 'C5', '117.231344', '31.759778', '1', '1', '1', '2', '1', '3');
INSERT INTO `car_station` VALUES ('6', 'C6', '117.207641', '31.729275', '1', '1', '1', '2', '1', '3');

-- ----------------------------
-- Table structure for drone
-- ----------------------------
DROP TABLE IF EXISTS `drone`;
CREATE TABLE `drone` (
  `did` int(11) NOT NULL,
  `speed` double NOT NULL,
  `no_load_power` double NOT NULL,
  `max_power` double NOT NULL,
  `max_load` int(11) NOT NULL,
  `battery` int(11) NOT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of drone
-- ----------------------------
INSERT INTO `drone` VALUES ('1', '12', '150', '320', '1500', '400');
INSERT INTO `drone` VALUES ('2', '9', '120', '280', '1000', '320');
INSERT INTO `drone` VALUES ('3', '8', '80', '170', '500', '180');

-- ----------------------------
-- Table structure for drone_station
-- ----------------------------
DROP TABLE IF EXISTS `drone_station`;
CREATE TABLE `drone_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `longitude` decimal(10,6) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  `wid` int(11) DEFAULT NULL,
  `big_drone_status` tinyint(1) DEFAULT '1',
  `big_drone_id` int(11) DEFAULT '1',
  `middle_drone_status` tinyint(1) DEFAULT '1',
  `middle_drone_id` int(11) DEFAULT '2',
  `small_drone_status` tinyint(1) DEFAULT '1',
  `small_drone_id` int(11) DEFAULT '3',
  PRIMARY KEY (`id`),
  KEY `wid_warehouse_id` (`wid`),
  KEY `name` (`name`),
  CONSTRAINT `wid_warehouse_id` FOREIGN KEY (`wid`) REFERENCES `warehouse` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of drone_station
-- ----------------------------
INSERT INTO `drone_station` VALUES ('1', 'W1', '117.214161', '31.751530', '1', '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('2', 'D1', '117.200029', '31.753592', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('3', 'D2', '117.209581', '31.762060', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('4', 'D3', '117.209867', '31.774985', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('5', 'D4', '117.204802', '31.749343', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('6', 'D5', '117.217905', '31.756361', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('7', 'D6', '117.223643', '31.759551', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('8', 'D7', '117.213935', '31.742092', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('9', 'D8', '117.189316', '31.743619', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('10', 'D9', '117.226037', '31.747311', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('11', 'D10', '117.205524', '31.740505', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('12', 'D11', '117.180692', '31.751023', null, '1', '1', '1', '2', '1', '3');
INSERT INTO `drone_station` VALUES ('13', 'D12', '117.231273', '31.753442', null, '1', '1', '1', '2', '1', '3');

-- ----------------------------
-- Table structure for order_record
-- ----------------------------
DROP TABLE IF EXISTS `order_record`;
CREATE TABLE `order_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` int(11) DEFAULT '1',
  `start_station` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `des_latitude` decimal(10,6) NOT NULL,
  `des_longitude` decimal(10,6) NOT NULL,
  `order_id` int(11) NOT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 NOT NULL,
  `length` int(11) NOT NULL,
  `width` double(11,0) NOT NULL,
  `height` int(11) NOT NULL,
  `weight` double(7,5) NOT NULL,
  `goods` varchar(255) CHARACTER SET utf8 NOT NULL,
  `deadline` datetime NOT NULL,
  `status` int(11) DEFAULT '0',
  `info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of order_record
-- ----------------------------
INSERT INTO `order_record` VALUES ('1', '1', 'W1', '31.753708', '117.219950', '23456', '张三', '20', '20', '20', '1.20000', '西瓜', '2022-09-24 15:04:34', '0', '好吃');
INSERT INTO `order_record` VALUES ('2', '1', 'W1', '31.765488', '117.205724', '24980', '李四', '20', '33', '12', '1.80000', '棉被', '2022-09-21 11:00:08', '1', '暖和');
INSERT INTO `order_record` VALUES ('3', '1', 'W1', '31.728819', '117.228605', '24567', '张三', '22', '22', '22', '1.30000', '西瓜', '2022-09-23 11:24:29', '2', '的');

-- ----------------------------
-- Table structure for path
-- ----------------------------
DROP TABLE IF EXISTS `path`;
CREATE TABLE `path` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `did` int(11) DEFAULT '0',
  `cid` int(11) DEFAULT '0',
  `station_number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of path
-- ----------------------------
INSERT INTO `path` VALUES ('1', '23456', '1', '0', '1');
INSERT INTO `path` VALUES ('2', '23456', '0', '1', '3');
INSERT INTO `path` VALUES ('3', '23456', '2', '0', '2');
INSERT INTO `path` VALUES ('4', '24980', '1', '0', '1');
INSERT INTO `path` VALUES ('5', '24980', '2', '0', '2');
INSERT INTO `path` VALUES ('7', '24980', '0', '2', '3');
INSERT INTO `path` VALUES ('8', '24567', '1', '0', '1');
INSERT INTO `path` VALUES ('9', '24567', '10', '0', '2');
INSERT INTO `path` VALUES ('10', '24567', '0', '3', '3');

-- ----------------------------
-- Table structure for t_map
-- ----------------------------
DROP TABLE IF EXISTS `t_map`;
CREATE TABLE `t_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` int(11) NOT NULL,
  `end_did` int(11) NOT NULL DEFAULT '0',
  `end_cid` int(11) NOT NULL DEFAULT '0',
  `distance` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `start_car_station` (`end_did`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_map
-- ----------------------------
INSERT INTO `t_map` VALUES ('1', '1', '0', '2', '995');
INSERT INTO `t_map` VALUES ('2', '1', '2', '0', '1357');
INSERT INTO `t_map` VALUES ('3', '1', '3', '0', '1249');
INSERT INTO `t_map` VALUES ('4', '2', '0', '1', '963');
INSERT INTO `t_map` VALUES ('5', '1', '8', '0', '1050');
INSERT INTO `t_map` VALUES ('6', '1', '10', '0', '1218');
INSERT INTO `t_map` VALUES ('7', '8', '11', '0', '815');
INSERT INTO `t_map` VALUES ('8', '5', '8', '0', '1182');
INSERT INTO `t_map` VALUES ('9', '5', '11', '0', '986');
INSERT INTO `t_map` VALUES ('10', '6', '7', '0', '648');
INSERT INTO `t_map` VALUES ('11', '7', '13', '0', '991');
INSERT INTO `t_map` VALUES ('12', '10', '13', '0', '843');
INSERT INTO `t_map` VALUES ('13', '6', '13', '0', '1306');
INSERT INTO `t_map` VALUES ('14', '7', '0', '5', '729');
INSERT INTO `t_map` VALUES ('15', '13', '0', '5', '705');
INSERT INTO `t_map` VALUES ('16', '6', '10', '0', '1267');
INSERT INTO `t_map` VALUES ('17', '1', '5', '0', '918');
INSERT INTO `t_map` VALUES ('18', '1', '6', '0', '644');
INSERT INTO `t_map` VALUES ('19', '5', '2', '0', '654');
INSERT INTO `t_map` VALUES ('20', '9', '11', '0', '1573');
INSERT INTO `t_map` VALUES ('21', '2', '0', '2', '683');
INSERT INTO `t_map` VALUES ('22', '3', '0', '2', '649');
INSERT INTO `t_map` VALUES ('23', '5', '12', '0', '2289');
INSERT INTO `t_map` VALUES ('24', '9', '12', '0', '1160');
INSERT INTO `t_map` VALUES ('25', '12', '0', '1', '1464');
INSERT INTO `t_map` VALUES ('26', '3', '0', '4', '1292');
INSERT INTO `t_map` VALUES ('27', '6', '0', '4', '1392');
INSERT INTO `t_map` VALUES ('28', '7', '0', '4', '1043');
INSERT INTO `t_map` VALUES ('29', '3', '4', '0', '1439');
INSERT INTO `t_map` VALUES ('30', '8', '0', '3', '1339');
INSERT INTO `t_map` VALUES ('31', '10', '0', '3', '730');
INSERT INTO `t_map` VALUES ('32', '8', '0', '6', '1546');
INSERT INTO `t_map` VALUES ('34', '11', '0', '6', '1266');
INSERT INTO `t_map` VALUES ('35', '5', '9', '0', '1598');
INSERT INTO `t_map` VALUES ('36', '4', '0', '4', '1257');
INSERT INTO `t_map` VALUES ('37', '3', '0', '1', '1637');

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `longitude` decimal(10,6) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES ('1', 'W1', '117.180692', '31.751023');

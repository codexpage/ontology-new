/*
 Navicat Premium Data Transfer

 Source Server         : ontology1
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : localhost
 Source Database       : ontology

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : utf-8

 Date: 08/12/2016 13:42:50 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `CO2sensor`
-- ----------------------------
DROP TABLE IF EXISTS `CO2sensor`;
CREATE TABLE `CO2sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorid` int(11) DEFAULT NULL,
  `CO2` double DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `humiditysensor`
-- ----------------------------
DROP TABLE IF EXISTS `humiditysensor`;
CREATE TABLE `humiditysensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorid` int(11) DEFAULT NULL,
  `humidity` double DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `infraredsensor`
-- ----------------------------
DROP TABLE IF EXISTS `infraredsensor`;
CREATE TABLE `infraredsensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorid` int(11) DEFAULT NULL,
  `infrared` double(11,0) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `luminancesensor`
-- ----------------------------
DROP TABLE IF EXISTS `luminancesensor`;
CREATE TABLE `luminancesensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorid` int(11) DEFAULT NULL,
  `luminance` double DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `temperaturesensor`
-- ----------------------------
DROP TABLE IF EXISTS `temperaturesensor`;
CREATE TABLE `temperaturesensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorid` int(11) DEFAULT NULL,
  `temperature` double DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=936 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : MysqlForQZB
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : company

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 21/12/2022 20:49:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `部门` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`部门`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('人力资源部门');
INSERT INTO `department` VALUES ('仓储部门');
INSERT INTO `department` VALUES ('后勤部门');
INSERT INTO `department` VALUES ('品控部门');
INSERT INTO `department` VALUES ('市场营销');
INSERT INTO `department` VALUES ('待业部门');
INSERT INTO `department` VALUES ('技术研发');
INSERT INTO `department` VALUES ('新部门');
INSERT INTO `department` VALUES ('测试部门');
INSERT INTO `department` VALUES ('生产');
INSERT INTO `department` VALUES ('行政');
INSERT INTO `department` VALUES ('财务');
INSERT INTO `department` VALUES ('采购');
INSERT INTO `department` VALUES ('销售');
INSERT INTO `department` VALUES ('高管');

SET FOREIGN_KEY_CHECKS = 1;

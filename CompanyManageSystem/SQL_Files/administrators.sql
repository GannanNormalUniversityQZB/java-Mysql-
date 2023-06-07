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

 Date: 21/12/2022 20:49:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrators
-- ----------------------------
DROP TABLE IF EXISTS `administrators`;
CREATE TABLE `administrators`  (
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identity` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrators
-- ----------------------------
INSERT INTO `administrators` VALUES ('lxy', '123456789', '员工');
INSERT INTO `administrators` VALUES ('qzb', '210703071', '管理员');
INSERT INTO `administrators` VALUES ('ytg', '123456789', '开发人员');
INSERT INTO `administrators` VALUES ('李欣芸', '123', '管理员');
INSERT INTO `administrators` VALUES ('邱志斌', '123456789', '员工');
INSERT INTO `administrators` VALUES ('马艺', '123', '员工');

SET FOREIGN_KEY_CHECKS = 1;

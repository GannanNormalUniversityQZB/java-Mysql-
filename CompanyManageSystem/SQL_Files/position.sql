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

 Date: 21/12/2022 20:49:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `所属部门` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `职位` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES ('人力资源部门', '人资经理');
INSERT INTO `position` VALUES ('仓储部门', '仓储经理');
INSERT INTO `position` VALUES ('仓储部门', '仓库专员');
INSERT INTO `position` VALUES ('仓储部门', '仓库主管');
INSERT INTO `position` VALUES ('财务', '会计');
INSERT INTO `position` VALUES ('后勤部门', '保洁');
INSERT INTO `position` VALUES ('财务', '出纳');
INSERT INTO `position` VALUES ('行政', '前台');
INSERT INTO `position` VALUES ('高管', '副总经理');
INSERT INTO `position` VALUES ('后勤部门', '厨师');
INSERT INTO `position` VALUES ('后勤部门', '司机');
INSERT INTO `position` VALUES ('后勤部门', '后勤经理');
INSERT INTO `position` VALUES ('人力资源部门', '员工培训主管');
INSERT INTO `position` VALUES ('品控部门', '品控经理');
INSERT INTO `position` VALUES ('品控部门', '品质工程师');
INSERT INTO `position` VALUES ('市场营销', '客户关系专员');
INSERT INTO `position` VALUES ('市场营销', '市场拓展');
INSERT INTO `position` VALUES ('市场营销', '市场策划');
INSERT INTO `position` VALUES ('市场营销', '市场经理');
INSERT INTO `position` VALUES ('待业部门', '待业人员');
INSERT INTO `position` VALUES ('新部门', '总监');
INSERT INTO `position` VALUES ('新部门', '总监助理');
INSERT INTO `position` VALUES ('高管', '总经理');
INSERT INTO `position` VALUES ('技术研发', '技术主管');
INSERT INTO `position` VALUES ('技术研发', '技术员');
INSERT INTO `position` VALUES ('技术研发', '技术经理');
INSERT INTO `position` VALUES ('人力资源部门', '招聘主管');
INSERT INTO `position` VALUES ('行政', '法律事务专员');
INSERT INTO `position` VALUES ('测试部门', '待业人员');
INSERT INTO `position` VALUES ('后勤部门', '物料采购员');
INSERT INTO `position` VALUES ('仓储部门', '物流专员');
INSERT INTO `position` VALUES ('仓储部门', '物流主管');
INSERT INTO `position` VALUES ('生产', '生产经理');
INSERT INTO `position` VALUES ('销售', '电话销售');
INSERT INTO `position` VALUES ('技术研发', '研发人员');
INSERT INTO `position` VALUES ('销售', '线下销售');
INSERT INTO `position` VALUES ('人力资源部门', '绩效考核主管');
INSERT INTO `position` VALUES ('高管', '董事长');
INSERT INTO `position` VALUES ('行政', '行政专员');
INSERT INTO `position` VALUES ('行政', '行政经理');
INSERT INTO `position` VALUES ('生产', '设备工程师');
INSERT INTO `position` VALUES ('生产', '设备研发人员');
INSERT INTO `position` VALUES ('生产', '设备维护员');
INSERT INTO `position` VALUES ('财务', '财务经理');
INSERT INTO `position` VALUES ('品控部门', '质检员');
INSERT INTO `position` VALUES ('测试部门', '部门经理');
INSERT INTO `position` VALUES ('采购', '采购员');
INSERT INTO `position` VALUES ('采购', '采购策划');
INSERT INTO `position` VALUES ('采购', '采购经理');
INSERT INTO `position` VALUES ('采购', '采购预算员');
INSERT INTO `position` VALUES ('销售', '销售代表');
INSERT INTO `position` VALUES ('销售', '销售经理');
INSERT INTO `position` VALUES ('财务', '预算管理');
INSERT INTO `position` VALUES ('人力资源部门', '待业人员');
INSERT INTO `position` VALUES ('仓储部门', '待业人员');
INSERT INTO `position` VALUES ('后勤部门', '待业人员');
INSERT INTO `position` VALUES ('品控部门', '待业人员');
INSERT INTO `position` VALUES ('市场营销', '待业人员');
INSERT INTO `position` VALUES ('技术研发', '待业人员');
INSERT INTO `position` VALUES ('新部门', '待业人员');
INSERT INTO `position` VALUES ('生产', '待业人员');
INSERT INTO `position` VALUES ('行政', '待业人员');
INSERT INTO `position` VALUES ('财务', '待业人员');
INSERT INTO `position` VALUES ('采购', '待业人员');
INSERT INTO `position` VALUES ('销售', '待业人员');
INSERT INTO `position` VALUES ('高管', '待业人员');

SET FOREIGN_KEY_CHECKS = 1;

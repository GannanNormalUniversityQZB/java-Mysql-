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

 Date: 21/12/2022 20:49:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `姓名` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `工号` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `部门` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `性别` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `学历` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `职位` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `待遇` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `入职年份` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `入职月份` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`工号`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('邱志斌', '210703071', '高管', '男', '研究生', '董事长', '10000+', '2021年', '九月');
INSERT INTO `employee` VALUES ('邓紫棋', '210703072', '财务', '女', '本科', '财务经理', '8000', '2018年', '五月');
INSERT INTO `employee` VALUES ('鹿晗', '210703073', '仓储部门', '男', '本科', '物流专员', '5000', '2019年', '一月');
INSERT INTO `employee` VALUES ('刘诗诗', '210703074', '行政', '女', '本科', '行政经理', '8000', '2020年', '三月');
INSERT INTO `employee` VALUES ('彭于晏', '210703075', '市场营销', '男', '研究生', '市场经理', '8000', '2016年', '十一月');
INSERT INTO `employee` VALUES ('赵丽颖', '210703076', '销售', '女', '专科', '销售经理', '8000', '2017年', '三月');
INSERT INTO `employee` VALUES ('王源', '210703077', '技术研发', '男', '本科', '技术经理', '8000', '2021年', '八月');
INSERT INTO `employee` VALUES ('王俊凯', '210703078', '品控部门', '男', '高中', '品控经理', '8000', '2015年', '四月');
INSERT INTO `employee` VALUES ('易烊千玺', '210703079', '生产', '男', '研究生', '生产经理', '8000', '2020年', '八月');
INSERT INTO `employee` VALUES ('刘德华', '210703080', '采购', '男', '高中', '采购经理', '8000', '2018年', '六月');
INSERT INTO `employee` VALUES ('刘涛', '210703082', '后勤部门', '女', '专科', '后勤经理', '8000', '2018年', '十月');
INSERT INTO `employee` VALUES ('王力宏', '210703083', '技术研发', '男', '专科', '技术员', '6000', '2019年', '十月');
INSERT INTO `employee` VALUES ('张杰', '210703084', '财务', '男', '初中', '会计', '6000', '2017年', '十月');
INSERT INTO `employee` VALUES ('唐嫣', '210703085', '采购', '女', '本科', '采购员', '5000', '2020年', '十月');
INSERT INTO `employee` VALUES ('杨洋', '210703086', '人力资源部门', '男', '高中', '绩效考核主管', '7000', '2015年', '十二月');
INSERT INTO `employee` VALUES ('蔡徐坤', '210703087', '市场营销', '男', '研究生', '客户关系专员', '5000', '2020年', '十二月');
INSERT INTO `employee` VALUES ('盖聂', '210703088', '高管', '男', '专科', '副总经理', '10000+', '2018年', '四月');
INSERT INTO `employee` VALUES ('姬如千泷', '210703089', '财务', '女', '本科', '出纳', '6000', '2019年', '十月');
INSERT INTO `employee` VALUES ('高渐离', '210703090', '销售', '男', '初中', '线下销售', '5000', '2021年', '十二月');
INSERT INTO `employee` VALUES ('端木蓉', '210703091', '技术研发', '女', '高中', '研发人员', '7000', '2021年', '三月');
INSERT INTO `employee` VALUES ('荆轲', '210703092', '人力资源部门', '男', '本科', '招聘主管', '6000', '2019年', '十二月');
INSERT INTO `employee` VALUES ('卫庄', '210703093', '品控部门', '男', '本科', '质检员', '5000', '2015年', '六月');
INSERT INTO `employee` VALUES ('赤练', '210703094', '生产', '女', '本科', '设备研发人员', '7000', '2017年', '四月');
INSERT INTO `employee` VALUES ('白凤', '210703095', '仓储部门', '男', '本科', '仓库主管', '7000', '2021年', '四月');
INSERT INTO `employee` VALUES ('少羽', '210703096', '后勤部门', '男', '专科', '物料采购员', '5000', '2016年', '十月');
INSERT INTO `employee` VALUES ('石兰', '210703097', '行政', '女', '专科', '前台', '4000', '2019年', '八月');
INSERT INTO `employee` VALUES ('张良', '210703098', '后勤部门', '男', '专科', '司机', '4000', '2018年', '六月');
INSERT INTO `employee` VALUES ('颜路', '210703099', '行政', '男', '专科', '法律事务专员', '6000', '2017年', '三月');
INSERT INTO `employee` VALUES ('伏念', '210703100', '人力资源部门', '男', '高中', '绩效考核主管', '5000', '2016年', '十二月');
INSERT INTO `employee` VALUES ('韩非', '210703101', '技术研发', '男', '本科', '技术主管', '6000', '2019年', '四月');
INSERT INTO `employee` VALUES ('晓梦', '210703102', '销售', '女', '研究生', '销售代表', '7000', '2015年', '十月');
INSERT INTO `employee` VALUES ('李星云', '210703103', '后勤部门', '男', '专科', '司机', '4000', '2021年', '十月');
INSERT INTO `employee` VALUES ('李欣芸', '210703104', '市场营销', '女', '本科', '市场拓展', '6000', '2022年', '十二月');
INSERT INTO `employee` VALUES ('程小杨', '210703107', '高管', '男', '研究生', '董事长', '10000+', '2022年', '九月');
INSERT INTO `employee` VALUES ('刘世兰', '210703108', '待业部门', '女', '本科', '待业人员', '6000', '2022年', '十二月');
INSERT INTO `employee` VALUES ('张三', '210703109', '测试部门', '男', '小学', '待业人员', '3000', '1999年', '一月');
INSERT INTO `employee` VALUES ('张思', '210703110', '待业部门', '女', '小学', '待业人员', '3000', '1999年', '一月');
INSERT INTO `employee` VALUES ('张武', '210703111', '待业部门', '女', '小学', '待业人员', '3000', '1999年', '一月');
INSERT INTO `employee` VALUES ('张三', '210703112', '待业部门', '女', '小学', '待业人员', '3000', '1999年', '一月');
INSERT INTO `employee` VALUES ('张期', '210703113', '待业部门', '女', '小学', '待业人员', '3000', '1999年', '一月');

SET FOREIGN_KEY_CHECKS = 1;

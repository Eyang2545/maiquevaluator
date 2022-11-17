/*
 Navicat Premium Data Transfer

 Source Server         : 远程数据库
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : rm-bp18x2ks086oj7n2xho.mysql.rds.aliyuncs.com:3306
 Source Schema         : evaluator_platform

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 16/11/2022 12:08:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `evaluatorId` bigint NULL DEFAULT NULL COMMENT '关联的测评师id',
  `teamNumber` int NULL DEFAULT NULL,
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teamCycle` int NULL DEFAULT NULL COMMENT '团队周期',
  `createTime`   datetime default CURRENT_TIMESTAMP null comment '创建时间',
  `updateTime`   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
  `isDelete` tinyint(3) UNSIGNED ZEROFILL NOT NULL,
  `endTime` datetime NULL DEFAULT NULL COMMENT '团队的结束时间',
  `teamName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

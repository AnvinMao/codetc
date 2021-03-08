/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : codetc_db

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2021-01-16 20:30:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作用户',
  `operation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型',
  `span_time` int(11) NOT NULL COMMENT '消耗时间(ms)',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方法',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作IP',
  `insert_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES ('10', '0', '用户登录', '142', 'com.codetc.web.controller.AuthController.login()', '[LoginParam(username=avin, password=123456)]', '0:0:0:0:0:0:0:1', '2021-01-16 20:28:19');
INSERT INTO `logs` VALUES ('11', '0', '用户登录', '5', 'com.codetc.web.controller.AuthController.login()', '[LoginParam(username=avin, password=123456)]', '0:0:0:0:0:0:0:1', '2021-01-16 20:28:50');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `value` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '查看用户', 'user:read');
INSERT INTO `permission` VALUES ('2', '编辑用户', 'user:edit');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '管理员', '1');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `nickname` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1-正常，0-禁用）',
  `create_time` datetime NOT NULL DEFAULT '2020-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '$2a$10$8bIzNuYt.DIxHF6H9qRBH.3zs36QgyulOvTTQ6B0n.H/Vh2MfHv.a', '1', '1', '2020-01-01 00:00:00');
INSERT INTO `user` VALUES ('2', 'anvin', 'anvin', '$2a$10$Cdx4grs/RpxpocRp8xyR6u06YH151wDgb3KkEG6f8MQkWWKD4Rzly', '1', '1', '2021-01-15 18:17:13');

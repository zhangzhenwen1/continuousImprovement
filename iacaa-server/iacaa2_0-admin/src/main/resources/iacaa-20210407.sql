/*
 Navicat MySQL Data Transfer

 Source Server         : docker-local
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : iacaa20

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 07/04/2021 14:41:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cultivation
-- ----------------------------
DROP TABLE IF EXISTS `t_cultivation`;
CREATE TABLE `t_cultivation`  (
  `id` int(8) UNIQUE COMMENT '培养方案编制年份',
  `cultivationName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养方案名称',
  `cultivationDescribe` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养方案描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cultivation
-- ----------------------------
 INSERT INTO `t_cultivation` VALUES (2011, '2011版', '201120111版');
 INSERT INTO `t_cultivation` VALUES (2022, '2022版', '20220222版');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `courseType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程类别',
  `credits` int(4) NOT NULL COMMENT '学分',
  `creditHours` int(4) NOT NULL COMMENT '学时',
  `edit_user_id` int(5) NULL DEFAULT NULL COMMENT '管理账户',
  `edit_status` int(1) NULL DEFAULT NULL COMMENT '课程编辑状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_assessmentInfo
-- ----------------------------
DROP TABLE IF EXISTS `t_assessmentInfo`;
CREATE TABLE `t_assessmentInfo`  (
  `assessmentName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案描述',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `assessmentDescribe` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养方案描述',
  PRIMARY KEY (`assessmentName`,`cultivationId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_assessmentInfo
-- ----------------------------
 INSERT INTO `t_assessmentInfo` VALUES ('平时成绩', 2011, '平时成绩');
 INSERT INTO `t_assessmentInfo` VALUES ('课程报告', 2011, '课程报告');
 INSERT INTO `t_assessmentInfo` VALUES ('实验成绩', 2011, '实验成绩');
 INSERT INTO `t_assessmentInfo` VALUES ('期末成绩', 2011, '期末成绩');

-- ----------------------------
-- Table structure for t_grad_requirement
-- ----------------------------
DROP TABLE IF EXISTS `t_grad_requirement`;
CREATE TABLE `t_grad_requirement`  (
  `id` int(3) NOT NULL COMMENT '唯一标识',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业要求',
  `attributeDescribe` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',

  PRIMARY KEY (`id`,`cultivationId`) USING BTREE,
  CONSTRAINT `cultivationIdKey_attribute` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_grad_requirement
-- ----------------------------
INSERT INTO `t_grad_requirement` VALUES (1, 2011, '工程知识', '工程知识工程知识');
INSERT INTO `t_grad_requirement` VALUES (2, 2011, '分析问题', '202202分析问题22版');
INSERT INTO `t_grad_requirement` VALUES (1, 2022, '工程知识', '工程知识工程知识');
INSERT INTO `t_grad_requirement` VALUES (2, 2022, '分析问题', '202202分析问题22版');

-- ----------------------------
-- Table structure for t_target
-- ----------------------------
DROP TABLE IF EXISTS `t_target`;
CREATE TABLE `t_target`  (
  `id` int(5) NOT NULL COMMENT '二级指标点编号',
  `attributeId` int(8) NOT NULL COMMENT '一级指标点编号',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `subAttributeDescribe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `idx_target` int(5) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  PRIMARY KEY (`id`,`attributeId`,`cultivationId`) USING BTREE,
  KEY `idx_target`(`idx_target`),
  CONSTRAINT `attributeIdKey_subAttribute` FOREIGN KEY (`attributeId`) REFERENCES `t_grad_requirement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cultivationIdKey_subAttribute` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_target
-- ----------------------------
INSERT INTO `t_target` VALUES (1, 1, 2011, '分解1工程知识11',1);
INSERT INTO `t_target` VALUES (2, 1, 2011, '分解2工程知识11',2);
INSERT INTO `t_target` VALUES (1, 2, 2011, '分解1分析问题11',3);
INSERT INTO `t_target` VALUES (2, 2, 2011, '分解2分析问题11',4);
INSERT INTO `t_target` VALUES (1, 1, 2022, '分解1工程知识22',5);
INSERT INTO `t_target` VALUES (2, 1, 2022, '分解2工程知识22',6);
INSERT INTO `t_target` VALUES (1, 2, 2022, '分解1分析问题22',7);
INSERT INTO `t_target` VALUES (2, 2, 2022, '分解2分析问题22',8);

-- ----------------------------
-- Table structure for t_course_target
-- ----------------------------
DROP TABLE IF EXISTS `t_course_target`;
CREATE TABLE `t_course_target`  (
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `attributeId` int(8) NOT NULL COMMENT '一级指标点编号',
  `subAttributeId` int(5) NOT NULL COMMENT '关联指标点',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `supportRatio` double NOT NULL COMMENT '关联比例',
  PRIMARY KEY (`cultivationId`,`attributeId`,`subAttributeId`,`courseId`) USING BTREE,
  CONSTRAINT `cultivationIdKey@course_target` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attributeIdKey@course_target` FOREIGN KEY (`attributeId`) REFERENCES `t_grad_requirement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `subAttributeIdKey@course_target` FOREIGN KEY (`subAttributeId`) REFERENCES `t_target` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `courseIdKey@course_target` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `t_course_target` VALUES (2011, 1, 1, 1, 1);
INSERT INTO `t_course_target` VALUES (2011, 1, 2, 2, 1);
INSERT INTO `t_course_target` VALUES (2011, 2, 1, 2, 1);
INSERT INTO `t_course_target` VALUES (2011, 2, 2, 1, 1);
INSERT INTO `t_course_target` VALUES (2022, 1, 1, 2, 1);
INSERT INTO `t_course_target` VALUES (2022, 1, 2, 1, 1);
INSERT INTO `t_course_target` VALUES (2022, 2, 1, 2, 1);
INSERT INTO `t_course_target` VALUES (2022, 2, 2, 1, 1);

-- ----------------------------
-- Table structure for t_check_link
-- ----------------------------
DROP TABLE IF EXISTS `t_check_link`;
CREATE TABLE `t_check_link`  (
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `assessmentName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考核环节名称',
  `totalScore` double NOT NULL COMMENT '总分',
  `scoreRatio` double NOT NULL COMMENT '成绩占比',
  PRIMARY KEY (`courseId`,`cultivationId`,`assessmentName`) USING BTREE,
  CONSTRAINT `courseId@assessment` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `assessmentNameKey@assessment` FOREIGN KEY (`assessmentName`) REFERENCES `t_assessmentInfo` (`assessmentName`) ON UPDATE CASCADE,
  CONSTRAINT `cultivationIdKey@assessment` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_courseObjective
-- ----------------------------
DROP TABLE IF EXISTS `t_courseObjective`;
CREATE TABLE `t_courseObjective`  (
  `objectiveId` int(7) NOT NULL COMMENT '唯一标识',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `objectiveDescribe` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `attributeId` int(5) NOT NULL COMMENT '关联指标点',
  `subAttributeId` int(5) NOT NULL COMMENT '关联次级指标点',
  `objectiveSupportRatio` double NOT NULL COMMENT '占比',
  PRIMARY KEY (`courseId`,`objectiveId`, `cultivationId`) USING BTREE,
  INDEX `objectiveId`(`objectiveId`) USING BTREE,
  CONSTRAINT `courseId@courseObjective` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `cultivationIdKey@courseObjective` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attributeIdKey@courseObjective` FOREIGN KEY (`attributeId`) REFERENCES `t_grad_requirement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `subAttributeIdKey@courseObjective` FOREIGN KEY (`subAttributeId`) REFERENCES `t_target` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_course_task_check_link
-- ----------------------------
DROP TABLE IF EXISTS `t_course_task_check_link`;
CREATE TABLE `t_course_task_check_link`  (
  `objectiveId` int(7) NOT NULL COMMENT '唯一标识',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `assessmentName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考核环节名称',
  `ratio` double NOT NULL COMMENT '占比',
  PRIMARY KEY (`courseId`,`objectiveId`, `cultivationId`, `assessmentName`) USING BTREE,
  CONSTRAINT `courseId@assessObjective` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `cultivationIdKey@assessObjective` FOREIGN KEY (`cultivationId`) REFERENCES `t_cultivation` (`id`),
  CONSTRAINT `objectiveIdKey@assessObjective` FOREIGN KEY (`objectiveId`) REFERENCES `t_courseObjective` (`objectiveId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assessmentNameKey@assessObjective` FOREIGN KEY (`assessmentName`) REFERENCES `t_assessmentInfo` (`assessmentName`) ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_semesterInfo
-- ----------------------------
DROP TABLE IF EXISTS `t_semesterInfo`;
CREATE TABLE `t_semesterInfo`  (
  `semesterId` int(8) NOT NULL UNIQUE COMMENT '学期',
  `semesterDescribe` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  PRIMARY KEY (`semesterId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_semesterInfo
-- ----------------------------
INSERT INTO `t_semesterInfo` VALUES (111, '2011～2012学年 第一学期', 2011);
INSERT INTO `t_semesterInfo` VALUES (112, '2011～2012学年 第二学期', 2011);

-- ----------------------------
-- Table structure for t_teacherInfo
-- ----------------------------
DROP TABLE IF EXISTS `t_teacherInfo`;
CREATE TABLE `t_teacherInfo`  (
  `teacherId` int(8) NOT NULL AUTO_INCREMENT COMMENT '学期',
  `teacherName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `teacherGender` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `teacherBirthDate` DATE NULL DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`teacherId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_courseMeasure
-- ----------------------------
DROP TABLE IF EXISTS `t_courseMeasure`;
CREATE TABLE `t_courseMeasure`  (
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `semesterId` int(5) NOT NULL COMMENT '学期',
  `measure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`courseId`,`semesterId`) USING BTREE,
  CONSTRAINT `courseId@t_courseMeasure` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `semesterId@t_courseMeasure` FOREIGN KEY (`semesterId`) REFERENCES `t_semesterInfo` (`semesterId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_studentInfo
-- ----------------------------
DROP TABLE IF EXISTS `t_studentInfo`;
CREATE TABLE `t_studentInfo`  (
  `studentId` int(8) NOT NULL UNIQUE COMMENT '学号',
  `studentName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `grade` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年级',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  PRIMARY KEY (`studentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_studentInfo
-- ----------------------------
INSERT INTO `t_studentInfo` VALUES (111111, '张三', '2011级',2011);
INSERT INTO `t_studentInfo` VALUES (111112, '李四', '2011级',2011);

-- ----------------------------
-- Table structure for t_stuScore
-- ----------------------------
DROP TABLE IF EXISTS `t_stuScore`;
CREATE TABLE `t_stuScore`  (
  `studentId` int(8) NOT NULL COMMENT '学号',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `semesterId` int(8) NOT NULL COMMENT '学期',
  `assessmentName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考核环节名称',
  `score` double NULL DEFAULT NULL COMMENT '占比',
  PRIMARY KEY (`studentId`, `courseId`, `assessmentName`,`semesterId`) USING BTREE,
  CONSTRAINT `studentId@t_stu_score` FOREIGN KEY (`studentId`) REFERENCES `t_studentInfo` (`studentId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assessmentNameKey@t_stu_score` FOREIGN KEY (`assessmentName`) REFERENCES `t_assessmentInfo` (`assessmentName`) ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_objectiveEvaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_objectiveEvaluate`;
CREATE TABLE `t_objectiveEvaluate`  (
  `studentId` int(8) NOT NULL COMMENT '学号',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `objectiveId` int(5) NOT NULL COMMENT '',
  `attributeId`int(5) NOT NULL COMMENT'关联次级指标点',
  `subAttributeId`int(5) NOT NULL COMMENT'关联次级指标点',
  `semesterId` int(8) NOT NULL COMMENT '学期',
  `cultivationId` int(8) NOT NULL COMMENT '培养方案编制年份',
  `evaluate` double NULL DEFAULT NULL COMMENT '占比',
  `subAttributeEvaluate` double NULL DEFAULT NULL COMMENT '占比',
  PRIMARY KEY (`studentId`, `courseId`, `objectiveId`,`semesterId`) USING BTREE,
  CONSTRAINT `courseId@t_objectiveEvaluate` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `studentId@t_objectiveEvaluate` FOREIGN KEY (`studentId`) REFERENCES `t_studentInfo` (`studentId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_subAttributeEvaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_subAttributeEvaluate`;
CREATE TABLE `t_subAttributeEvaluate`  (
  `studentId` int(8) NOT NULL COMMENT '学号',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `semesterId` int(8) NOT NULL COMMENT '学期',
  `objectiveId` int(5) NOT NULL COMMENT '',
  `attributeId` int(5) NOT NULL COMMENT '关联次级指标点',
  `subAttributeId` int(5) NOT NULL COMMENT '关联次级指标点',
  `evaluate` double NULL DEFAULT NULL COMMENT '占比',
  PRIMARY KEY (`studentId`, `courseId`, `objectiveId`,`semesterId`) USING BTREE,
  CONSTRAINT `courseId@t_subAttributeEvaluate` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_attributeEvaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_attributeEvaluate`;
CREATE TABLE `t_attributeEvaluate`  (
  `studentId` int(8) NOT NULL COMMENT '学号',
  `courseId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '培养方案名称',
  `attributeId` int(5) NOT NULL COMMENT '关联次级指标点',
  `evaluate` double NULL DEFAULT NULL COMMENT '占比',
  PRIMARY KEY (`studentId`, `courseId`, `attributeId`) USING BTREE,
  CONSTRAINT `courseId@t_attributeEvaluate` FOREIGN KEY (`courseId`) REFERENCES `t_course` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `studentId@t_attributeEvaluate` FOREIGN KEY (`studentId`) REFERENCES `t_studentInfo` (`studentId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for t_stu_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `t_stu_evaluation`;
CREATE TABLE `t_stu_evaluation`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `course_task` int(7) NULL DEFAULT NULL COMMENT '课程目标',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'id地址',
  `stu_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '学号',
  `score` int(1) NULL DEFAULT 0 COMMENT '成绩',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `coursekey@stu_evaluation` FOREIGN KEY (`course_task`) REFERENCES `t_courseObjective` (`objectiveId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 426 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tb_client
-- ----------------------------
DROP TABLE IF EXISTS `tb_client`;
CREATE TABLE `tb_client`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auto_grant` bit(1) NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `redirect_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `refresh_token_timeout` bigint(20) NOT NULL,
  `scopes` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `secret` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `timeout` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_p5csaxy20iqoh8rg382cda9x2`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_client
-- ----------------------------
INSERT INTO `tb_client` VALUES (1, b'0', 'husen19950528201314lq19980824', NULL, NULL, NULL, 5184000000, NULL, '$2a$10$ed5bqt7P9DgaMo1XmDGNNuClN5Ub4uGxeoOr5IM0hT.cvEcvSLFTy', 2592000000);
INSERT INTO `tb_client` VALUES (2, b'0', 'user123456user', NULL, NULL, NULL, 5184000000, NULL, '$10$MUuK12NjTO4ZvRJG11utwOS3tvI3p/KDqJQVGKszcv1EAPeai7MQS', 2592000000);
INSERT INTO `tb_client` VALUES (3, b'0', 'Iacaa20Server', NULL, NULL, NULL, 5184000000, NULL, '$10$MUuK12NjTO4ZvRJG11utwOS3tvI3p/KDqJQVGKszcv1EAPeai7MQS', 2592000000);

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES (1, 1);
INSERT INTO `tb_permission` VALUES (2, 2);
INSERT INTO `tb_permission` VALUES (3, 3);
INSERT INTO `tb_permission` VALUES (4, 4);
INSERT INTO `tb_permission` VALUES (5, 5);
INSERT INTO `tb_permission` VALUES (6, 6);
INSERT INTO `tb_permission` VALUES (7, 7);
INSERT INTO `tb_permission` VALUES (8, 8);
INSERT INTO `tb_permission` VALUES (9, 9);
INSERT INTO `tb_permission` VALUES (10, 10);
INSERT INTO `tb_permission` VALUES (11, 11);
INSERT INTO `tb_permission` VALUES (12, 12);
INSERT INTO `tb_permission` VALUES (13, 13);
INSERT INTO `tb_permission` VALUES (14, 14);
INSERT INTO `tb_permission` VALUES (15, 15);
INSERT INTO `tb_permission` VALUES (16, 16);
INSERT INTO `tb_permission` VALUES (17, 17);
INSERT INTO `tb_permission` VALUES (18, 18);
INSERT INTO `tb_permission` VALUES (19, 19);
INSERT INTO `tb_permission` VALUES (20, 20);
INSERT INTO `tb_permission` VALUES (21, 21);
INSERT INTO `tb_permission` VALUES (22, 22);
INSERT INTO `tb_permission` VALUES (23, 23);
INSERT INTO `tb_permission` VALUES (24, 24);
INSERT INTO `tb_permission` VALUES (25, 25);
INSERT INTO `tb_permission` VALUES (26, 26);
INSERT INTO `tb_permission` VALUES (27, 27);
INSERT INTO `tb_permission` VALUES (28, 28);
INSERT INTO `tb_permission` VALUES (29, 29);
INSERT INTO `tb_permission` VALUES (30, 30);
INSERT INTO `tb_permission` VALUES (31, 31);
INSERT INTO `tb_permission` VALUES (32, 32);
INSERT INTO `tb_permission` VALUES (33, 33);
INSERT INTO `tb_permission` VALUES (34, 34);

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `resource_server_id` bigint(20) NOT NULL,
  `resource_server_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (1, '更新角色', 1, 'AuthCenter', 'role:update');
INSERT INTO `tb_resource` VALUES (2, '删除角色', 1, 'AuthCenter', 'role:delete');
INSERT INTO `tb_resource` VALUES (3, '创建角色', 1, 'AuthCenter', 'role:create');
INSERT INTO `tb_resource` VALUES (4, '查询角色', 1, 'AuthCenter', 'role:list');
INSERT INTO `tb_resource` VALUES (5, '删除角色', 1, 'AuthCenter', 'role:deleteAll');
INSERT INTO `tb_resource` VALUES (6, '分页查询角色', 1, 'AuthCenter', 'role:page');
INSERT INTO `tb_resource` VALUES (7, '创建客户端', 1, 'AuthCenter', 'client:create');
INSERT INTO `tb_resource` VALUES (8, '根据clientId查询client', 1, 'AuthCenter', 'client:findByClientId');
INSERT INTO `tb_resource` VALUES (9, '绑定用户到客户端', 1, 'AuthCenter', 'client:bindUser');
INSERT INTO `tb_resource` VALUES (10, '权限列表', 1, 'AuthCenter', 'permission:list');
INSERT INTO `tb_resource` VALUES (11, '角色权限列表', 1, 'AuthCenter', 'permission:listByRole');
INSERT INTO `tb_resource` VALUES (12, '创建用户', 1, 'AuthCenter', 'user:create');
INSERT INTO `tb_resource` VALUES (13, '设置用户角色', 1, 'AuthCenter', 'user:setUserRole');
INSERT INTO `tb_resource` VALUES (14, '根据ID查询用户', 2, 'UserServer', 'User:get');
INSERT INTO `tb_resource` VALUES (15, '更新用户', 2, 'UserServer', 'User:update');
INSERT INTO `tb_resource` VALUES (16, '删除用户', 2, 'UserServer', 'User:delete');
INSERT INTO `tb_resource` VALUES (17, '创建用户', 2, 'UserServer', 'User:create');
INSERT INTO `tb_resource` VALUES (18, '分页查询用户', 2, 'UserServer', 'User:page');
INSERT INTO `tb_resource` VALUES (19, '根据用户名查询用户', 2, 'UserServer', 'User:findByUsername');
INSERT INTO `tb_resource` VALUES (20, '判断用户是否存在', 2, 'UserServer', 'User:userIsExisted');
INSERT INTO `tb_resource` VALUES (21, '根据ID查询用户自定义信息', 2, 'UserServer', 'UserCustomizeInfo:get');
INSERT INTO `tb_resource` VALUES (22, '更新用户自定义信息', 2, 'UserServer', 'UserCustomizeInfo:update');
INSERT INTO `tb_resource` VALUES (23, '删除用户自定义信息', 2, 'UserServer', 'UserCustomizeInfo:delete');
INSERT INTO `tb_resource` VALUES (24, '创建用户自定义信息', 2, 'UserServer', 'UserCustomizeInfo:create');
INSERT INTO `tb_resource` VALUES (25, '更新毕业要求', 3, 'Iacaa20Server', 'GradRequirement:update');
INSERT INTO `tb_resource` VALUES (26, '毕业要求列表', 3, 'Iacaa20Server', 'GradRequirement:list');
INSERT INTO `tb_resource` VALUES (27, '保存毕业要求', 3, 'Iacaa20Server', 'GradRequirement:save');
INSERT INTO `tb_resource` VALUES (28, '毕业要求分页列表', 3, 'Iacaa20Server', 'GradRequirement:pageList');
INSERT INTO `tb_resource` VALUES (29, '删除单个毕业要求', 3, 'Iacaa20Server', 'GradRequirement:deleteOne');
INSERT INTO `tb_resource` VALUES (30, '毕业要求Vo列表', 3, 'Iacaa20Server', 'GradRequirement:voList');
INSERT INTO `tb_resource` VALUES (31, '删除毕业要求列表', 3, 'Iacaa20Server', 'GradRequirement:deleteList');
INSERT INTO `tb_resource` VALUES (32, '导出模板', 3, 'Iacaa20Server', 'GradRequirement:exportTemplate');
INSERT INTO `tb_resource` VALUES (33, '毕业要求Vo全部列表', 3, 'Iacaa20Server', 'GradRequirement:voListAll');
INSERT INTO `tb_resource` VALUES (34, '获取单个毕业要求', 3, 'Iacaa20Server', 'GradRequirement:getOne');

-- ----------------------------
-- Table structure for tb_resource_server
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource_server`;
CREATE TABLE `tb_resource_server`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_9rkldek41vc4qypvfk6x9ltb9`(`client_id`) USING BTREE,
  UNIQUE INDEX `UK_adedt277hncymwr33q04ghv8b`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource_server
-- ----------------------------
INSERT INTO `tb_resource_server` VALUES (1, 'husen19950528201314lq19980824', 'AuthCenter');
INSERT INTO `tb_resource_server` VALUES (2, 'user123456user', 'UserServer');
INSERT INTO `tb_resource_server` VALUES (3, 'Iacaa20Server', 'Iacaa20Server');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_1ncmoedv5ta7r19y9d4oidn0y`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '教师角色', '教师');

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES (9, 32, 1);
INSERT INTO `tb_role_permission` VALUES (10, 33, 1);
INSERT INTO `tb_role_permission` VALUES (11, 34, 1);
INSERT INTO `tb_role_permission` VALUES (12, 25, 1);
INSERT INTO `tb_role_permission` VALUES (13, 26, 1);
INSERT INTO `tb_role_permission` VALUES (14, 27, 1);
INSERT INTO `tb_role_permission` VALUES (15, 28, 1);
INSERT INTO `tb_role_permission` VALUES (16, 29, 1);
INSERT INTO `tb_role_permission` VALUES (17, 30, 1);
INSERT INTO `tb_role_permission` VALUES (18, 31, 1);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_user_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `last_modified_by` bigint(20) NULL DEFAULT NULL,
  `last_modified_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_4wv83hfajry5tdoamn8wsqa6x`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '$2a$10$HpJqGCYwgoc./l5aClq86.Fw.N05POL1FolWrJcdfVzvjYpqu20di', 'teacher', 0, '2021-03-31 09:30:23.268000', 0, '2021-03-31 09:30:23.268000');

-- ----------------------------
-- Table structure for tb_user_client_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_client_relation`;
CREATE TABLE `tb_user_client_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_client_relation
-- ----------------------------
INSERT INTO `tb_user_client_relation` VALUES (1, 1, 0);

-- ----------------------------
-- Table structure for tb_user_customize_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_customize_info`;
CREATE TABLE `tb_user_customize_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `info` varchar(4096) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;

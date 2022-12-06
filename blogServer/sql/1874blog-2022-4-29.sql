/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : 1874blog

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 29/04/2022 20:28:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for con_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `con_article_tag`;
CREATE TABLE `con_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_tag_1`(`article_id`) USING BTREE,
  INDEX `fk_article_tag_2`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 279 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_article_tag
-- ----------------------------
INSERT INTO `con_article_tag` VALUES (277, 76, 26);
INSERT INTO `con_article_tag` VALUES (278, 77, 26);

-- ----------------------------
-- Table structure for con_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `con_role_menu`;
CREATE TABLE `con_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1817 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_role_menu
-- ----------------------------
INSERT INTO `con_role_menu` VALUES (1650, 1, 1);
INSERT INTO `con_role_menu` VALUES (1651, 1, 2);
INSERT INTO `con_role_menu` VALUES (1652, 1, 6);
INSERT INTO `con_role_menu` VALUES (1653, 1, 7);
INSERT INTO `con_role_menu` VALUES (1654, 1, 8);
INSERT INTO `con_role_menu` VALUES (1655, 1, 9);
INSERT INTO `con_role_menu` VALUES (1656, 1, 10);
INSERT INTO `con_role_menu` VALUES (1657, 1, 5);
INSERT INTO `con_role_menu` VALUES (1790, 4, 1);
INSERT INTO `con_role_menu` VALUES (1791, 4, 2);
INSERT INTO `con_role_menu` VALUES (1792, 4, 6);
INSERT INTO `con_role_menu` VALUES (1793, 4, 7);
INSERT INTO `con_role_menu` VALUES (1794, 4, 8);
INSERT INTO `con_role_menu` VALUES (1795, 4, 9);
INSERT INTO `con_role_menu` VALUES (1796, 4, 10);
INSERT INTO `con_role_menu` VALUES (1797, 4, 3);
INSERT INTO `con_role_menu` VALUES (1798, 4, 11);
INSERT INTO `con_role_menu` VALUES (1799, 4, 12);
INSERT INTO `con_role_menu` VALUES (1800, 4, 202);
INSERT INTO `con_role_menu` VALUES (1801, 4, 13);
INSERT INTO `con_role_menu` VALUES (1802, 4, 14);
INSERT INTO `con_role_menu` VALUES (1803, 4, 201);
INSERT INTO `con_role_menu` VALUES (1804, 4, 4);
INSERT INTO `con_role_menu` VALUES (1805, 4, 16);
INSERT INTO `con_role_menu` VALUES (1806, 4, 15);
INSERT INTO `con_role_menu` VALUES (1807, 4, 17);
INSERT INTO `con_role_menu` VALUES (1808, 4, 214);
INSERT INTO `con_role_menu` VALUES (1809, 4, 215);
INSERT INTO `con_role_menu` VALUES (1810, 4, 18);
INSERT INTO `con_role_menu` VALUES (1811, 4, 19);
INSERT INTO `con_role_menu` VALUES (1812, 4, 20);
INSERT INTO `con_role_menu` VALUES (1813, 4, 208);
INSERT INTO `con_role_menu` VALUES (1814, 4, 209);
INSERT INTO `con_role_menu` VALUES (1815, 4, 210);
INSERT INTO `con_role_menu` VALUES (1816, 4, 211);
INSERT INTO `con_role_menu` VALUES (1817, 4, 5);

-- ----------------------------
-- Table structure for con_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `con_role_resource`;
CREATE TABLE `con_role_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5596 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_role_resource
-- ----------------------------
INSERT INTO `con_role_resource` VALUES (5356, 1, 165);
INSERT INTO `con_role_resource` VALUES (5357, 1, 190);
INSERT INTO `con_role_resource` VALUES (5358, 1, 191);
INSERT INTO `con_role_resource` VALUES (5359, 1, 192);
INSERT INTO `con_role_resource` VALUES (5360, 1, 182);
INSERT INTO `con_role_resource` VALUES (5361, 1, 183);
INSERT INTO `con_role_resource` VALUES (5362, 1, 184);
INSERT INTO `con_role_resource` VALUES (5363, 1, 185);
INSERT INTO `con_role_resource` VALUES (5364, 1, 186);
INSERT INTO `con_role_resource` VALUES (5365, 1, 187);
INSERT INTO `con_role_resource` VALUES (5366, 1, 188);
INSERT INTO `con_role_resource` VALUES (5367, 1, 189);
INSERT INTO `con_role_resource` VALUES (5368, 1, 250);
INSERT INTO `con_role_resource` VALUES (5369, 1, 284);
INSERT INTO `con_role_resource` VALUES (5370, 1, 170);
INSERT INTO `con_role_resource` VALUES (5371, 1, 214);
INSERT INTO `con_role_resource` VALUES (5372, 1, 215);
INSERT INTO `con_role_resource` VALUES (5373, 1, 216);
INSERT INTO `con_role_resource` VALUES (5375, 1, 217);
INSERT INTO `con_role_resource` VALUES (5484, 4, 165);
INSERT INTO `con_role_resource` VALUES (5485, 4, 190);
INSERT INTO `con_role_resource` VALUES (5486, 4, 191);
INSERT INTO `con_role_resource` VALUES (5487, 4, 192);
INSERT INTO `con_role_resource` VALUES (5488, 4, 179);
INSERT INTO `con_role_resource` VALUES (5489, 4, 180);
INSERT INTO `con_role_resource` VALUES (5490, 4, 181);
INSERT INTO `con_role_resource` VALUES (5491, 4, 202);
INSERT INTO `con_role_resource` VALUES (5492, 4, 203);
INSERT INTO `con_role_resource` VALUES (5493, 4, 167);
INSERT INTO `con_role_resource` VALUES (5494, 4, 196);
INSERT INTO `con_role_resource` VALUES (5495, 4, 197);
INSERT INTO `con_role_resource` VALUES (5496, 4, 198);
INSERT INTO `con_role_resource` VALUES (5497, 4, 182);
INSERT INTO `con_role_resource` VALUES (5498, 4, 183);
INSERT INTO `con_role_resource` VALUES (5499, 4, 184);
INSERT INTO `con_role_resource` VALUES (5500, 4, 185);
INSERT INTO `con_role_resource` VALUES (5501, 4, 186);
INSERT INTO `con_role_resource` VALUES (5502, 4, 187);
INSERT INTO `con_role_resource` VALUES (5503, 4, 188);
INSERT INTO `con_role_resource` VALUES (5504, 4, 189);
INSERT INTO `con_role_resource` VALUES (5505, 4, 250);
INSERT INTO `con_role_resource` VALUES (5506, 4, 284);
INSERT INTO `con_role_resource` VALUES (5507, 4, 169);
INSERT INTO `con_role_resource` VALUES (5508, 4, 204);
INSERT INTO `con_role_resource` VALUES (5509, 4, 205);
INSERT INTO `con_role_resource` VALUES (5510, 4, 170);
INSERT INTO `con_role_resource` VALUES (5511, 4, 214);
INSERT INTO `con_role_resource` VALUES (5512, 4, 215);
INSERT INTO `con_role_resource` VALUES (5513, 4, 216);
INSERT INTO `con_role_resource` VALUES (5514, 4, 220);
INSERT INTO `con_role_resource` VALUES (5515, 4, 224);
INSERT INTO `con_role_resource` VALUES (5516, 4, 243);
INSERT INTO `con_role_resource` VALUES (5517, 4, 245);
INSERT INTO `con_role_resource` VALUES (5518, 4, 290);
INSERT INTO `con_role_resource` VALUES (5519, 4, 218);
INSERT INTO `con_role_resource` VALUES (5520, 4, 219);
INSERT INTO `con_role_resource` VALUES (5521, 4, 222);
INSERT INTO `con_role_resource` VALUES (5522, 4, 295);
INSERT INTO `con_role_resource` VALUES (5523, 4, 173);
INSERT INTO `con_role_resource` VALUES (5524, 4, 200);
INSERT INTO `con_role_resource` VALUES (5525, 4, 201);
INSERT INTO `con_role_resource` VALUES (5526, 4, 211);
INSERT INTO `con_role_resource` VALUES (5527, 4, 217);
INSERT INTO `con_role_resource` VALUES (5528, 4, 252);
INSERT INTO `con_role_resource` VALUES (5529, 4, 253);
INSERT INTO `con_role_resource` VALUES (5530, 4, 254);
INSERT INTO `con_role_resource` VALUES (5531, 4, 175);
INSERT INTO `con_role_resource` VALUES (5532, 4, 210);
INSERT INTO `con_role_resource` VALUES (5533, 4, 213);
INSERT INTO `con_role_resource` VALUES (5534, 4, 223);
INSERT INTO `con_role_resource` VALUES (5535, 4, 288);
INSERT INTO `con_role_resource` VALUES (5536, 4, 193);
INSERT INTO `con_role_resource` VALUES (5537, 4, 194);
INSERT INTO `con_role_resource` VALUES (5538, 4, 195);
INSERT INTO `con_role_resource` VALUES (5539, 4, 206);
INSERT INTO `con_role_resource` VALUES (5540, 4, 207);
INSERT INTO `con_role_resource` VALUES (5541, 4, 208);
INSERT INTO `con_role_resource` VALUES (5542, 4, 212);
INSERT INTO `con_role_resource` VALUES (5543, 4, 269);
INSERT INTO `con_role_resource` VALUES (5544, 4, 273);
INSERT INTO `con_role_resource` VALUES (5545, 4, 275);
INSERT INTO `con_role_resource` VALUES (5546, 4, 276);
INSERT INTO `con_role_resource` VALUES (5547, 4, 277);
INSERT INTO `con_role_resource` VALUES (5548, 4, 278);
INSERT INTO `con_role_resource` VALUES (5549, 4, 279);
INSERT INTO `con_role_resource` VALUES (5550, 4, 280);
INSERT INTO `con_role_resource` VALUES (5551, 4, 281);
INSERT INTO `con_role_resource` VALUES (5552, 4, 296);
INSERT INTO `con_role_resource` VALUES (5553, 4, 297);
INSERT INTO `con_role_resource` VALUES (5554, 4, 298);
INSERT INTO `con_role_resource` VALUES (5555, 4, 299);
INSERT INTO `con_role_resource` VALUES (5556, 4, 300);
INSERT INTO `con_role_resource` VALUES (5557, 4, 301);
INSERT INTO `con_role_resource` VALUES (5558, 4, 302);
INSERT INTO `con_role_resource` VALUES (5559, 4, 303);
INSERT INTO `con_role_resource` VALUES (5560, 4, 304);
INSERT INTO `con_role_resource` VALUES (5561, 4, 227);
INSERT INTO `con_role_resource` VALUES (5563, 4, 305);
INSERT INTO `con_role_resource` VALUES (5596, 2, 227);
INSERT INTO `con_role_resource` VALUES (5597, 2, 243);
INSERT INTO `con_role_resource` VALUES (5598, 2, 245);
INSERT INTO `con_role_resource` VALUES (5599, 2, 234);
INSERT INTO `con_role_resource` VALUES (5600, 2, 235);
INSERT INTO `con_role_resource` VALUES (5601, 2, 310);
INSERT INTO `con_role_resource` VALUES (5602, 2, 307);
INSERT INTO `con_role_resource` VALUES (5603, 2, 308);
INSERT INTO `con_role_resource` VALUES (5604, 2, 309);

-- ----------------------------
-- Table structure for con_role_user
-- ----------------------------
DROP TABLE IF EXISTS `con_role_user`;
CREATE TABLE `con_role_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `USERID`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_role_user
-- ----------------------------
INSERT INTO `con_role_user` VALUES (17, 4, 11);
INSERT INTO `con_role_user` VALUES (20, 2, 15);

-- ----------------------------
-- Table structure for sys_quartz
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz`;
CREATE TABLE `sys_quartz`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名',
  `job_classname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类名',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组',
  `time_type` int NULL DEFAULT NULL COMMENT '时间',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'corn表达式（与时间二选一）',
  `invoke_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需要的参数',
  `time` int NULL DEFAULT NULL COMMENT '即时任务时间',
  `status` int NULL DEFAULT NULL COMMENT '任务状态(0暂停,1运行)',
  `create_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_quartz
-- ----------------------------
INSERT INTO `sys_quartz` VALUES (9, '清除每日访问ip', 'ClearViewIpSet', 'SystemJob', NULL, '0 30 0 * * ?', NULL, NULL, 1, 'admin', '2022-04-19 23:22:40', NULL);
INSERT INTO `sys_quartz` VALUES (10, '保存每日访问ip', 'SaveUniqueView', 'SystemJob', NULL, '0 10 0 * * ?', NULL, NULL, 1, 'admin', '2022-04-19 23:23:09', NULL);

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '作者',
  `category_id` int NULL DEFAULT NULL COMMENT '文章分类',
  `article_cover` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章缩略图',
  `article_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `article_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发表时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_top` tinyint(1) NULL DEFAULT NULL COMMENT '是否置顶 0否 1是',
  `is_draft` tinyint(1) NULL DEFAULT 0 COMMENT '是否为草稿 0否 1是',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES (76, 11, 23, 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/Articles/DefaultCover.webp', '2022-04-17 测试', '开发商Blue Meridian及发行商Crytivo宣布：史前动物园管理模拟器《史前王国》将于今年4月27日开放EA测试，感兴趣的玩家不妨一试。（暂不支持中文）\n\n本作是一款众筹作品，去年完成筹款后进行了一段时间的A测。EA版本包含创意工具，基本管理功能以及超过20只史前动物；而游戏完整版将实现之前的所有承诺，包括各种机制、更多的物种以及扩展内容。\n\n此外，根据Steam页面显示，只有当开发商及社区对最终版本感到满意时，EA才会结束，在此之前开发团队将持续开发至少18个月。', '2022-04-17 15:39:45', NULL, 0, 0, 0);
INSERT INTO `tb_article` VALUES (77, 11, 23, 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/Articles/DefaultCover.webp', '2022-04-22 战神', '《战神》发售4周年了，索尼圣莫尼卡工作室发布视频，致谢所有《战神》玩家。\n\n由于官方没有放出续作《战神：诸神黄昏》的任何新消息，玩家们急了，本作真的能在今年发售吗？2022年都过去4个月了，还是什么新消息都没有。\n\n于是就有玩家在圣莫尼卡工作室动画总监Bruno Velazquez的推特下留言：能再次向我们保证《战神：诸神黄昏》百分百能在今年发售吗？\n\nBruno Velazquez则回复道：《战神：诸神黄昏》今年即将到来。\n\n虽然还没有准确的发售时间，但Bruno Velazquez的发言无疑安抚了大部分玩家焦急的心。\n\n《战神：诸神黄昏》是一款由索尼制作并发行的动作冒险游戏，是《战神4》的正统续作。诸神黄昏即将降临，玩家们将展开一系列惊心动魄的冒险。', '2022-04-22 11:56:16', NULL, 1, 0, 0);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (23, '测试', '2022-04-17 15:36:02');

-- ----------------------------
-- Table structure for tb_chat_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_record`;
CREATE TABLE `tb_chat_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '聊天内容',
  `ip_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `type` tinyint NULL DEFAULT NULL COMMENT '类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1226 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_chat_record
-- ----------------------------
INSERT INTO `tb_chat_record` VALUES (1226, NULL, '未知ip', 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp', 'hello', '未知ip', '', 3, '2022-04-25 18:42:45');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '评论用户Id',
  `article_id` int NULL DEFAULT NULL COMMENT '评论文章id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime(0) NOT NULL COMMENT '评论时间',
  `reply_id` bigint NULL DEFAULT NULL COMMENT '回复用户id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论id',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `is_read` tinyint NULL DEFAULT 0 COMMENT '是否已读 0否 1是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_comment_article`(`article_id`) USING BTREE,
  INDEX `fk_comment_parent`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 306 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (306, 15, 76, 'hello<img src= \'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/emoji/miaoa.PNG\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>', '2022-04-21 20:31:19', NULL, NULL, 0, 0);
INSERT INTO `tb_comment` VALUES (307, 15, 76, 'test', '2022-04-29 10:40:32', 15, 306, 0, 1);
INSERT INTO `tb_comment` VALUES (308, 15, 76, 'where', '2022-04-29 10:44:59', 15, 306, 0, 0);

-- ----------------------------
-- Table structure for tb_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_link`;
CREATE TABLE `tb_friend_link`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `link_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接名',
  `link_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接头像',
  `link_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接地址',
  `link_intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接介绍',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_friend_link_user`(`link_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------
INSERT INTO `tb_friend_link` VALUES (17, '测试', 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp', 'test', '测试', '2022-04-22 12:19:14');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单icon',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `order_num` tinyint NULL DEFAULT NULL COMMENT '排序',
  `parent_id` int NULL DEFAULT NULL COMMENT '父id',
  `is_disable` tinyint(1) NULL DEFAULT 0 COMMENT '是否禁用 0否1是',
  `is_hidden` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏  0否1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 215 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', '2021-01-26 17:06:51', '2021-01-26 17:06:53', 1, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (2, '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07', '2021-01-25 20:43:09', 2, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2021-01-25 20:44:20', 3, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (4, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', 5, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (5, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', '2021-01-26 17:22:38', '2022-03-28 20:03:23', 8, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (6, '添加文章', '/articles', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:30:48', '2021-01-26 14:30:51', 1, 2, 0, 0);
INSERT INTO `tb_menu` VALUES (7, '修改文章', '/articles/*', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:31:32', '2021-01-26 14:31:34', 2, 2, 0, 1);
INSERT INTO `tb_menu` VALUES (8, '文章列表', '/article-list', '/article/ArticleList.vue', 'el-icon-mywenzhangliebiao', '2021-01-26 14:32:13', '2021-01-26 14:32:16', 3, 2, 0, 0);
INSERT INTO `tb_menu` VALUES (9, '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', '2021-01-26 14:33:42', '2021-01-26 14:33:43', 4, 2, 0, 0);
INSERT INTO `tb_menu` VALUES (10, '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', '2021-01-26 14:34:33', '2021-01-26 14:34:36', 5, 2, 0, 0);
INSERT INTO `tb_menu` VALUES (11, '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', '2021-01-26 14:35:31', '2021-01-26 14:35:34', 1, 3, 0, 0);
INSERT INTO `tb_menu` VALUES (12, '留言管理', '/messages', '/message/Message.vue', 'el-icon-myliuyan', '2021-01-26 14:36:09', '2021-01-26 14:36:13', 2, 3, 0, 0);
INSERT INTO `tb_menu` VALUES (13, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', '2021-01-26 14:38:09', '2021-01-26 14:38:12', 1, 202, 0, 0);
INSERT INTO `tb_menu` VALUES (14, '角色管理', '/roles', '/role/Role.vue', 'el-icon-myjiaoseliebiao', '2021-01-26 14:39:01', '2021-01-26 14:39:03', 2, 202, 0, 0);
INSERT INTO `tb_menu` VALUES (15, '资源管理', '/resources', '/resource/Resource.vue', 'el-icon-myxitong', '2021-01-26 14:40:14', '2021-01-26 14:40:16', 2, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (16, '菜单管理', '/menus', '/menu/Menu.vue', 'el-icon-mycaidan', '2021-01-26 14:40:54', '2021-01-26 14:40:56', 1, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (17, '友链管理', '/links', '/friendLink/FriendLink.vue', 'el-icon-mydashujukeshihuaico-', '2021-01-26 14:41:35', '2021-01-26 14:41:37', 3, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (18, '关于我', '/about', '/about/About.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:42:05', '2022-04-07 13:48:56', 6, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (19, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', 6, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (20, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21', '2021-01-31 15:53:25', 1, 19, 0, 0);
INSERT INTO `tb_menu` VALUES (201, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51', '2021-02-05 14:59:53', 7, 202, 0, 0);
INSERT INTO `tb_menu` VALUES (202, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59', '2021-02-06 23:45:03', 4, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (208, '音乐管理', '/music-submenu', 'Layout', 'icon-music', '2021-08-18 14:04:48', '2021-08-18 14:09:57', 7, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (209, '歌手管理', '/singer', '/singer/Singer.vue', 'el-icon-myuser', '2021-08-18 14:06:59', '2021-08-18 14:07:41', 1, 208, 0, 0);
INSERT INTO `tb_menu` VALUES (210, '音乐列表', '/musicList', '/musicList/MusicList.vue', 'icon-musiclist', '2021-08-18 14:09:38', '2021-08-18 14:09:38', 2, 208, 0, 0);
INSERT INTO `tb_menu` VALUES (211, '歌手歌曲', '/singer/musics', '/singer/singerMusic.vue', 'icon-music', '2021-08-19 16:33:42', '2021-08-19 16:40:30', 7, 208, 0, 1);
INSERT INTO `tb_menu` VALUES (214, '系统信息', '/sysInfo', '/systemInfo/SysInfo.vue', 'el-icon-myguanyuwo', '2022-04-07 13:34:34', '2022-04-07 13:34:34', 4, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (215, '定时任务', '/sysQuartz', '/quartz/Quartz.vue', 'el-icon-myliuyan', '2022-04-09 11:40:14', '2022-04-09 11:40:14', 5, 4, 0, 0);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户地址',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '留言内容',
  `time` int NULL DEFAULT NULL COMMENT '弹幕速度',
  `create_time` datetime(0) NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3456 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
INSERT INTO `tb_message` VALUES (3456, '192.168.85.1', '本地局域网', '游客', 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp', '测试弹幕', 8, '2022-04-22 18:40:43');

-- ----------------------------
-- Table structure for tb_music
-- ----------------------------
DROP TABLE IF EXISTS `tb_music`;
CREATE TABLE `tb_music`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `singer_id` int NULL DEFAULT NULL COMMENT '歌手id',
  `music_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲名字',
  `music_createTime` datetime(0) NULL DEFAULT NULL COMMENT '歌曲创建时间',
  `music_updateTime` datetime(0) NULL DEFAULT NULL COMMENT '歌曲跟新时间',
  `music_Img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲图片',
  `music_lyric` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '歌词',
  `music_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲地址',
  `music_al` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲专辑',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '歌曲信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_music
-- ----------------------------
INSERT INTO `tb_music` VALUES (31, 25, '旧情人，我是时间的新欢', '2022-04-27 19:10:48', NULL, '/img/musicImg/music_cover.jpg', '[ti:旧情人，我是时间的新欢]\r\n[ar:尧十三]\r\n[al:飞船，宇航员]\r\n[by:]\r\n[offset:0]\r\n[00:00.10]旧情人，我是时间的新欢 - 尧十三\r\n[00:00.20]词：尧十三/马頔\r\n[00:00.30]曲：尧十三\r\n[00:00.40]\r\n[00:01.09]你是谁的新欢和旧爱\r\n[00:07.92]\r\n[00:12.31]当你行走在黑夜里\r\n[00:17.22]\r\n[00:18.40]看一次不散场的电影\r\n[00:24.26]等一个等不来的人\r\n[00:29.85]谁渴望\r\n[00:31.88]\r\n[00:34.03]谁悲伤\r\n[00:36.46]\r\n[00:37.32]你是谁的新欢和旧爱\r\n[00:43.23]\r\n[00:44.61]当他拥抱赤裸的你\r\n[00:50.46]时间就变成了船儿\r\n[00:56.50]在海上颠簸摇晃\r\n[01:01.74]守着船上的人们远航\r\n[01:07.65]\r\n[01:09.79]一直到你独自醒来\r\n[01:15.18]发疯一样地寻找\r\n[01:19.01]没有答案\r\n[01:21.01]你在黄昏时转身离开\r\n[01:26.06]\r\n[01:26.95]一直到他从流光里\r\n[01:32.44]匆匆赶来\r\n[01:36.00]带着红纹石的种子\r\n[01:40.80]撕开黑夜的防备\r\n[01:44.68]割破双手\r\n[01:46.79]染红了谁的脸\r\n[01:51.12]\r\n[02:35.32]你是谁的新欢和旧爱\r\n[02:44.14]当他拍掉你身上的雨\r\n[02:50.55]把你的眼泪装进酒杯\r\n[02:55.58]\r\n[02:56.93]当做他唯一的依靠\r\n[03:01.82]然后成为你伟大的船长\r\n[03:07.77]\r\n[03:09.93]一直到你独自醒来\r\n[03:15.18]发疯一样地寻找\r\n[03:18.97]没有答案\r\n[03:21.02]你在黄昏时转身离开\r\n[03:27.18]一直到他从流光里\r\n[03:32.33]匆匆赶来\r\n[03:35.97]带着红纹石的种子\r\n[03:40.74]撕开黑夜的防备\r\n[03:44.63]割破双手\r\n[03:46.83]染红了你的脸\r\n[03:53.00]\r\n[03:55.55]你是谁的新欢和旧爱\r\n[04:01.40]\r\n[04:05.89]如果他善待你的美丽\r\n[04:11.60]会不会对你手下留情\r\n[04:17.49]\r\n[04:18.47]会不会一无所有\r\n[04:23.27]再见旧情人\r\n[04:26.53]我是时间的新欢', '/musics/1651057847572旧情人，我是时间的新欢.mp3', '旧情人，我是时间的新欢');
INSERT INTO `tb_music` VALUES (32, 26, '你离开了南京，从此没有人和我说话', '2022-04-27 19:13:06', NULL, '/img/musicImg/music_cover.jpg', '暂无歌词', '/musics/1651057986027李志 - 你离开了南京，从此没有人和我说话.mp3', '你离开了南京，从此没有人和我说话');
INSERT INTO `tb_music` VALUES (33, 26, '天空之城', '2022-04-27 19:14:30', NULL, '/img/musicImg/music_cover.jpg', '暂无歌词', '/musics/1651058069540天空之城.mp3', '《我爱南京》');

-- ----------------------------
-- Table structure for tb_music_list
-- ----------------------------
DROP TABLE IF EXISTS `tb_music_list`;
CREATE TABLE `tb_music_list`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT ' 主键',
  `list_id` int NULL DEFAULT NULL COMMENT '列表id:1热歌榜,-1个人收藏',
  `music_id` int NULL DEFAULT NULL COMMENT ' 歌曲id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '个人收藏的用户的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_music_list
-- ----------------------------
INSERT INTO `tb_music_list` VALUES (7, 1, 31, NULL);
INSERT INTO `tb_music_list` VALUES (8, 1, 32, NULL);
INSERT INTO `tb_music_list` VALUES (9, 1, 33, NULL);

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回数据',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `ip_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 432 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------
INSERT INTO `tb_operation_log` VALUES (408, '日志模块', '删除', '/OperationLog/admin/logs', 'com.zzh.controller.OperationLogController.deleteLogs', '删除操作日志', '[[407,366,365,364,253]]', 'DELETE', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-22 12:08:59');
INSERT INTO `tb_operation_log` VALUES (409, '友链模块', '新增或修改', '/friend-link/admin/save', 'com.zzh.controller.FriendLinkController.addOrUpdate', '修改或保存友链', '[{\"linkAddress\":\"test\",\"linkAvatar\":\"https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp\",\"linkIntro\":\"测试\",\"linkName\":\"测试\"}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-22 12:19:14');
INSERT INTO `tb_operation_log` VALUES (410, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"id\":179,\"isAnonymous\":1,\"isDisable\":0,\"requestMethod\":\"GET\",\"resourceName\":\"查看关于我信息\",\"url\":\"/about\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-22 16:22:11');
INSERT INTO `tb_operation_log` VALUES (411, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"id\":238,\"isAnonymous\":1,\"isDisable\":0,\"requestMethod\":\"GET\",\"resourceName\":\"查看留言列表\",\"url\":\"/message/getList\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-22 18:29:28');
INSERT INTO `tb_operation_log` VALUES (412, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"id\":239,\"isAnonymous\":1,\"isDisable\":0,\"requestMethod\":\"POST\",\"resourceName\":\"添加留言\",\"url\":\"/message/add\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-22 18:37:14');
INSERT INTO `tb_operation_log` VALUES (413, '定时任务模块', '保存', '/quartz/add', 'com.zzh.controller.QuartzController.addSysQuartz', '添加一个定时任务', '[{\"cronExpression\":\"0/5 * * * * ?\",\"jobClassname\":\"ClearViewIpSet\",\"jobGroup\":\"commonJob\",\"jobName\":\"test\"}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 11:38:26');
INSERT INTO `tb_operation_log` VALUES (414, '定时任务模块', '修改', '/quartz/pauseJob/11', 'com.zzh.controller.QuartzController.pauseQuartz', '暂停一个任务', '[11]', 'PUT', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 11:38:46');
INSERT INTO `tb_operation_log` VALUES (415, '定时任务模块', '删除', '/quartz/deleteJob', 'com.zzh.controller.QuartzController.deleteQuartz', '删除一个任务', '[11]', 'DELETE', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 11:38:48');
INSERT INTO `tb_operation_log` VALUES (416, '定时任务模块', '保存', '/quartz/add', 'com.zzh.controller.QuartzController.addSysQuartz', '添加一个定时任务', '[{\"cronExpression\":\"0/5 * * * * ?\",\"jobClassname\":\"ClearViewIpSet\",\"jobGroup\":\"commonJob\",\"jobName\":\"测试\"}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 12:07:38');
INSERT INTO `tb_operation_log` VALUES (417, '定时任务模块', '修改', '/quartz/pauseJob/12', 'com.zzh.controller.QuartzController.pauseQuartz', '暂停一个任务', '[12]', 'PUT', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 12:07:49');
INSERT INTO `tb_operation_log` VALUES (418, '定时任务模块', '删除', '/quartz/deleteJob', 'com.zzh.controller.QuartzController.deleteQuartz', '删除一个任务', '[12]', 'DELETE', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-23 12:07:51');
INSERT INTO `tb_operation_log` VALUES (419, '角色模块', '新增或修改', '/role/admin/saveOrUpdateRole', 'com.zzh.controller.RoleController.saveOrUpdateRole', '保存或更新角色', '[{\"id\":2,\"resourceIdList\":[227,243,245,234,235],\"roleLabel\":\"用户\",\"roleName\":\"ROLE_USER\",\"type\":2}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-24 21:50:09');
INSERT INTO `tb_operation_log` VALUES (420, '歌手模块', '新增或修改', '/singer/admin/saveOrUpdateSinger', 'com.zzh.controller.SingerController.saveOrUpdateSinger', '修改或添加歌手', '[{\"singerBirth\":143222400000,\"singerIntroduction\":\"陈奕迅（Eason Chan），1974年7月27日出生于中国香港，祖籍广东省东莞市 [1]  ，华语流行乐男歌手、演员、作曲人，拥有英国金斯顿大学荣誉博士的学历 [195]  。\\n1995年参加第14届新秀歌唱大赛并获得冠军，同年正式出道。1996年发行个人首张专辑《陈奕迅》。1997年主演个人首部电影《旺角大家姐之冤魂不散》。1998年凭借歌曲《天下无双》在乐坛获得关注。2000年发行的歌曲《K歌之王》奠定其在歌坛的地位。2001年发行流行摇滚风格的专辑《反正是我》。2003年发行个人首张概念专辑《黑白灰》，专辑中的歌曲《十年》获得第4届音乐风云榜十大金曲奖 [2]  ；同年凭借专辑《Special Thanks to》获得第14届台湾金曲奖最佳国语男歌手奖、最佳流行音乐演唱专辑奖 [3]  。\\n2005年发行粤语专辑《U87》，专辑中的歌曲《浮夸》成为其歌唱生涯的代表作品之一。2006年起连续九年获得叱咤乐坛流行榜颁奖典礼我最喜爱的男歌手奖，连续两年获得十大劲歌金曲颁奖典礼最受欢迎男歌星奖 [4]  [59]  。2007年发行专辑《认了吧》 [166]  。2009年凭借专辑《不想放手》获得第20届台湾金曲奖最佳国语专辑奖 [72]  。2010年凭借剧情片《金钱帝国》获得星光大典港台年度电影男演员奖 [5]  。2012年发行舞曲风格的粤语专辑《…3mm》。2015年凭借专辑《米·闪》获得第26届台湾金曲奖最佳国语男歌手奖 [6-7]  。2016年举行“Another Eason\'s LIFE世界巡回演唱会” [8]  。2018年凭借专辑《C\'mon in~》获得第29届台湾金曲奖最佳国语男歌手奖 [9-10]  。2020年发行粤语单曲《致明日的舞》 [106]  。2021年发行单曲《孤勇者》 [162]  。\",\"singerLocation\":\"香港\",\"singerName\":\"陈奕迅\",\"singerSex\":true}]', 'POST', '{\"msg\":\"保存成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 11:39:17');
INSERT INTO `tb_operation_log` VALUES (421, '歌手模块', '新增或修改', '/singer/admin/saveOrUpdateSinger', 'com.zzh.controller.SingerController.saveOrUpdateSinger', '修改或添加歌手', '[{\"singerBirth\":199036800000,\"singerIntroduction\":\"周杰伦（Jay Chou），1979年1月18日出生于台湾省新北市，祖籍福建省泉州市永春县，中国台湾流行乐男歌手、音乐人、演员、导演、编剧，毕业于淡江中学。\\n2000年发行首张个人专辑《Jay》。2001年发行的专辑《范特西》奠定其融合中西方音乐的风格 [1]  。2002年举行“The One”世界巡回演唱会 [2]  。2003年成为美国《时代周刊》封面人物 [3-4]  。2004年获得世界音乐大奖中国区最畅销艺人奖 [333]  。2005年凭借动作片《头文字D》获得金马奖、金像奖最佳新人奖 [6]  。2006年起连续三年获得世界音乐大奖中国区最畅销艺人奖 [7]  。2007年自编自导的文艺片《不能说的秘密》获得金马奖年度台湾杰出电影奖 [8]  。\\n2008年凭借歌曲《青花瓷》获得第19届金曲奖最佳作曲人奖。2009年入选美国CNN评出的“25位亚洲最具影响力人物” [9]  ，同年凭借专辑《魔杰座》获得第20届金曲奖最佳国语男歌手奖 [10]  。2010年入选美国《Fast Company》评出的“全球百大创意人物” [11]  。2011年凭借专辑《跨时代》再度获得金曲奖最佳国语男歌手奖，并且第四次获得金曲奖最佳国语专辑奖；同年主演好莱坞电影《青蜂侠》 [12]  。2012年登福布斯中国名人榜榜首 [13]  。2014年发行华语乐坛首张数字音乐专辑《哎呦，不错哦》 [14]  。2021年在央视春晚演唱歌曲《Mojito》 [149]  。\\n演艺事业外，他还涉足商业、设计等领域。2007年成立杰威尔有限公司 [16]  。2011年担任华硕笔电设计师，并入股香港文化传信集团 [17-18]  。\\n周杰伦热心公益慈善，多次向中国内地灾区捐款捐物。2008年捐款援建希望小学 [19]  。2014年担任中国禁毒宣传形象大使 [20]  。\",\"singerLocation\":\"中国台湾\",\"singerName\":\"周杰伦\",\"singerSex\":true}]', 'POST', '{\"msg\":\"保存成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 12:33:39');
INSERT INTO `tb_operation_log` VALUES (422, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"parentId\":262,\"requestMethod\":\"GET\",\"resourceName\":\"获取用户收藏歌单\",\"url\":\"/musicList/getCollect\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 13:24:03');
INSERT INTO `tb_operation_log` VALUES (423, '角色模块', '新增或修改', '/role/admin/saveOrUpdateRole', 'com.zzh.controller.RoleController.saveOrUpdateRole', '保存或更新角色', '[{\"id\":2,\"resourceIdList\":[227,243,245,234,235,307],\"roleLabel\":\"用户\",\"roleName\":\"ROLE_USER\",\"type\":2}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 13:24:21');
INSERT INTO `tb_operation_log` VALUES (424, '资源模块', '删除', '/resource/admin/delete', 'com.zzh.controller.ResourceController.deleteResource', '删除资源', '[266]', 'DELETE', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 19:36:33');
INSERT INTO `tb_operation_log` VALUES (425, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"parentId\":262,\"requestMethod\":\"POST\",\"resourceName\":\"添加歌曲到个人收藏\",\"url\":\"/music/addUserCollect\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 19:37:00');
INSERT INTO `tb_operation_log` VALUES (426, '角色模块', '新增或修改', '/role/admin/saveOrUpdateRole', 'com.zzh.controller.RoleController.saveOrUpdateRole', '保存或更新角色', '[{\"id\":2,\"resourceIdList\":[227,243,245,234,235,307,308],\"roleLabel\":\"用户\",\"roleName\":\"ROLE_USER\",\"type\":2}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-26 19:37:18');
INSERT INTO `tb_operation_log` VALUES (427, '歌手模块', '删除', '/singer/admin/delete', 'com.zzh.controller.SingerController.deleteSinger', '删除歌手信息', '[23]', 'DELETE', '{\"msg\":\"删除成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 19:04:26');
INSERT INTO `tb_operation_log` VALUES (428, '歌手模块', '删除', '/singer/admin/delete', 'com.zzh.controller.SingerController.deleteSinger', '删除歌手信息', '[24]', 'DELETE', '{\"msg\":\"删除成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 19:04:28');
INSERT INTO `tb_operation_log` VALUES (429, '歌手模块', '新增或修改', '/singer/admin/saveOrUpdateSinger', 'com.zzh.controller.SingerController.saveOrUpdateSinger', '修改或添加歌手', '[{\"singerBirth\":525283200000,\"singerIntroduction\":\"尧十三，原名唐尧，1986年8月25日出生于贵州省毕节市织金县，中国内地男歌手，毕业于武汉大学医学院临床专业。\\n2011年，加入麻油叶民间民谣组织 [1]  ；同年，推出个人原创单曲《瞎子》 [2]  。2012年，为电影《浮城谜事》弹奏吉他曲《我想弹琴给你听》 [3]  。2013年，正式签约摩登天空 [1]  。 2014年，为电影《推拿》献唱片尾曲《他妈的》 [3]  。2015年，推出个人原创单曲《北方女王》 [4]  ；同年，推出首张个人音乐专辑《飞船，宇航员》 [5]  。2019年1月17日，推出个人原创单曲《很久》 [6]  。\",\"singerLocation\":\"中国\",\"singerName\":\"尧十三\",\"singerSex\":true}]', 'POST', '{\"msg\":\"保存成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 19:09:05');
INSERT INTO `tb_operation_log` VALUES (430, '歌手模块', '新增或修改', '/singer/admin/saveOrUpdateSinger', 'com.zzh.controller.SingerController.saveOrUpdateSinger', '修改或添加歌手', '[{\"singerBirth\":279734400000,\"singerIntroduction\":\"李志，1978年11月13日生于江苏省常州市金坛区，中国内地民谣男歌手、独立音乐人，东南大学工科肄业。\\n2004年12月，推出首张个人音乐专辑《被禁忌的游戏》 [1]  。2005年12月，推出第二张个人音乐专辑《梵高先生》 [1]  。2006年11月，推出第三张个人音乐专辑《这个世界会好吗》 [2]  。2007年，举行“将进酒”全国巡回演唱会 [3]  。2009年9月，推出第四张个人音乐专辑《我爱南京》 [4]  。2010年9月，推出第五张个人音乐专辑《你好，郑州》。2011年9月，推出第六张个人音乐专辑《F》 [5]  。2014年11月13日，推出第七张个人音乐专辑《1701》 [6]  。2015年1月19日，凭借专辑《1701》入围“第四届阿比鹿音乐奖”年度民谣专辑 [7]  ；同年，举行“看见”全国巡回演唱会 [8]  。2016年3月14日，推出个人跨年Live数字专辑《动静》 [9]  ；同年10月18日，推出第八张个人音乐专辑《8》 [10]  ；同年11月20日，推出第九张个人音乐专辑《在每一条伤心的应天大街上》 [11]  。2018年10月23日，李志宣布与太合音乐集团旗下的麦田音乐厂牌达成合作 [12]  。\",\"singerLocation\":\"中国\",\"singerName\":\"李志\",\"singerSex\":true}]', 'POST', '{\"msg\":\"保存成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 19:12:24');
INSERT INTO `tb_operation_log` VALUES (431, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"parentId\":262,\"requestMethod\":\"DELETE\",\"resourceName\":\"移除收藏音乐\",\"url\":\"/music/removeCollect\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 20:36:20');
INSERT INTO `tb_operation_log` VALUES (432, '角色模块', '新增或修改', '/role/admin/saveOrUpdateRole', 'com.zzh.controller.RoleController.saveOrUpdateRole', '保存或更新角色', '[{\"id\":2,\"resourceIdList\":[227,243,245,234,235,307,308,309],\"roleLabel\":\"用户\",\"roleName\":\"ROLE_USER\",\"type\":2}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-27 20:36:36');
INSERT INTO `tb_operation_log` VALUES (433, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"parentId\":176,\"requestMethod\":\"GET\",\"resourceName\":\"查询未读信息\",\"url\":\"/comment/getNotRead\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-29 18:55:28');
INSERT INTO `tb_operation_log` VALUES (434, '角色模块', '新增或修改', '/role/admin/saveOrUpdateRole', 'com.zzh.controller.RoleController.saveOrUpdateRole', '保存或更新角色', '[{\"id\":2,\"resourceIdList\":[227,243,245,234,235,310,307,308,309],\"roleLabel\":\"用户\",\"roleName\":\"ROLE_USER\",\"type\":2}]', 'POST', '{\"msg\":\"操作成功\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-29 18:55:47');
INSERT INTO `tb_operation_log` VALUES (435, '资源模块', '新增或修改', '/resource/admin/addOrUpdate', 'com.zzh.controller.ResourceController.addOrUpdateResource', '添加或更新资源', '[{\"id\":310,\"isAnonymous\":0,\"isDisable\":0,\"requestMethod\":\"GET\",\"resourceName\":\"查询收到的回复\",\"url\":\"/comment/getUserComment\"}]', 'POST', '{\"msg\":\"操作成功!\",\"code\":\"200\"}', 11, 'admin', '192.168.85.1', '本地局域网', '2022-04-29 19:29:37');

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` int NULL DEFAULT NULL COMMENT '父权限id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_disable` tinyint(1) NULL DEFAULT 0 COMMENT '是否禁用 0否 1是',
  `is_anonymous` tinyint NULL DEFAULT 0 COMMENT '是否匿名访问 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 310 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (165, '分类模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (166, '博客信息模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (167, '友链模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (168, '文章模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (169, '日志模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (170, '标签模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (171, '用户信息模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (172, '用户账号模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (173, '留言模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (174, '菜单模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (175, '角色模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (176, '评论模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (177, '资源模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (178, '查看博客信息', '/', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (179, '查看关于我信息', '/about', 'GET', 166, '2022-03-09 21:27:51', '2022-04-22 16:22:11', 0, 1);
INSERT INTO `tb_resource` VALUES (180, '查看后台信息', '/admin/index', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (181, '修改关于我信息', '/admin/about', 'PUT', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (182, '查看后台文章', '/article/admin/getArticles', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (183, '添加或修改文章', '/article/saveOrUpdateArticle', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (184, '恢复或删除文章', '/article/admin/deleteOrRecArticle', 'PUT', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (185, '物理删除文章', '/article/admin/deleteArticles', 'DELETE', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (186, '上传文章图片', '/article/uploadImg', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (187, '查看文章选项', '/article/admin/options', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (188, '修改文章置顶', '/admin/article/top/*', 'PUT', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (189, '根据id查看后台文章', '/article/getArticlesById/*', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (190, '查看后台分类列表', '/category/admin/categories', 'POST', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (191, '添加或修改分类', '/category/admin/saveOrUpdateCategory', 'POST', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (192, '删除分类', '/category/admin/deleteCategory', 'DELETE', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (193, '查询后台评论', '/comment/admin/getBackComments', 'POST', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (194, '删除或恢复评论', '/comment/admin/updateCommentsStatus', 'PUT', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (195, '物理删除评论', '/comment/admin/deleteComments', 'DELETE', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (196, '查看后台友链列表', '/friend-link/admin/getLinks', 'POST', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (197, '保存或修改友链', '/friend-link/admin/save', 'POST', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (198, '删除友链', '/friend-link/admin/deleteLinks', 'DELETE', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (199, '查看菜单列表', '/admin/menu/getMenu', 'POST', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (200, '查看后台留言列表', '/message/admin/getBackMessages', 'POST', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (201, '删除留言', '/message/admin/deleteMessages', 'DELETE', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (202, '查看公告', '/admin/notice', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (203, '修改公告', '/admin/notice', 'PUT', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (204, '查看操作日志', '/OperationLog/admin/logs', 'POST', 169, '2022-03-09 21:27:51', '2022-04-01 12:42:32', 0, 0);
INSERT INTO `tb_resource` VALUES (205, '删除操作日志', '/OperationLog/admin/logs', 'DELETE', 169, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (206, '查看资源列表', '/resource/admin/resourceList', 'GET', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (207, '新增或修改资源', '/resource/admin/addOrUpdate', 'POST', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (208, '删除资源', '/resource//admin/delete', 'DELETE', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (210, '保存或更新角色', '/role/admin/saveOrUpdateRole', 'POST', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (211, '查看角色菜单选项', '/menu/admin/role/menus', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (212, '查看角色资源选项', '/resource/admin/resources', 'GET', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (213, '查询角色列表', '/role/admin/roles', 'POST', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (214, '查看后台标签列表', '/tag/admin/selectTags', 'POST', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (215, '添加或修改标签', '/tag/admin/saveOrUpdateTag', 'POST', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (216, '删除标签', '/tag/admin/deleteTag', 'DELETE', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (217, '查看用户菜单', '/menu/admin/getMenus', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (218, '查看后台用户列表', '/user/admin/users', 'GET', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (219, '修改用户禁用状态', '/user/admin/enabledState/*', 'PUT', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (220, '查看在线用户', '/user/admin/online', 'GET', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (222, '修改管理员密码', '/admin/users/password', 'PUT', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (223, '查询用户角色选项', '/role/admin/roleList', 'GET', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (224, '修改用户角色', '/userInfo/admin/updateUserInfo', 'PUT', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (225, '查看首页文章', '/article/homeData', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (226, '查看文章归档', '/article/archives', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (227, '点赞文章', '/article/like', 'PUT', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (228, '查看最新文章', '/article/newest', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (229, '搜索文章', '/article/search', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (230, '根据id查看文章', '/article/*', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (231, '查看分类列表', '/category/categoryList', 'GET', 165, '2022-03-09 21:27:51', '2022-04-20 20:29:55', 0, 1);
INSERT INTO `tb_resource` VALUES (232, '查看分类下对应的文章', '/article/category/*', 'GET', 168, '2022-03-09 21:27:51', '2022-04-20 20:46:55', 0, 1);
INSERT INTO `tb_resource` VALUES (233, '查询评论', '/comment/*', 'GET', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (234, '添加评论或回复', '/comment/addComment', 'POST', 176, '2022-03-09 21:27:51', '2022-04-21 17:10:05', 0, 0);
INSERT INTO `tb_resource` VALUES (235, '评论点赞', '/comment/like', 'POST', 176, '2022-03-09 21:27:51', '2022-04-21 15:14:10', 0, 0);
INSERT INTO `tb_resource` VALUES (236, '查询评论下的回复', '/comment/replies/*', 'GET', 176, '2022-03-09 21:27:51', '2022-04-21 15:02:45', 0, 1);
INSERT INTO `tb_resource` VALUES (237, '查看友链列表', '/friend-link/getList', 'GET', 167, '2022-03-09 21:27:51', '2022-04-21 11:56:22', 0, 1);
INSERT INTO `tb_resource` VALUES (238, '查看留言列表', '/message/getList', 'GET', 173, '2022-03-09 21:27:51', '2022-04-22 18:29:28', 0, 1);
INSERT INTO `tb_resource` VALUES (239, '添加留言', '/message/add', 'POST', 173, '2022-03-09 21:27:51', '2022-04-22 18:37:14', 0, 1);
INSERT INTO `tb_resource` VALUES (240, '查看标签列表', '/tag/getList', 'GET', 170, '2022-03-09 21:27:51', '2022-04-20 20:59:49', 0, 1);
INSERT INTO `tb_resource` VALUES (241, '查看分类下对应的文章', '/article/tag/*', 'GET', 168, '2022-03-09 21:27:51', '2022-04-20 21:03:02', 0, 1);
INSERT INTO `tb_resource` VALUES (242, '用户注册', '/user/register', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (243, '修改用户头像', '/userInfo/avatar', 'POST', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (244, '发送邮箱验证码', '/user/register/code', 'GET', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (245, '修改用户资料', '/userInfo/setInfo', 'PUT', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (248, '修改密码', '/users/password', 'PUT', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (249, '上传语音', '/voice', 'POST', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (250, '上传文章封面', '/article/uploadCover', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (252, '查看后台菜单', '/menu/admin/menuList', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (253, '更新或添加菜单数据', '/menu/admin/updateOrSaveMenu', 'POST', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (254, '删除菜单数据', '/admin/deleteMenu', 'DELETE', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (262, '音乐模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (263, '获取歌单音乐', '/musicList/getList', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (264, '查找指定音乐url', '/music/getUrl', 'GET', 262, '2022-03-09 21:27:51', '2022-04-26 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (265, '获取歌曲歌词', '/music/lyric', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (267, '模糊查询', '/music/getMusicLike', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (268, '查询用户文章', '/articles/getUserArticle', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (269, '查询后台歌曲', '/music/admin/getMusicList', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (271, '添加歌曲', '/admin/music/add', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (272, '更新歌曲文件', '/admin/music/updateFile/*', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (273, '更新歌曲信息', '/music/admin/updateMusicInfo', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (274, '删除歌曲', '/music/admin/delete', 'DELETE', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (275, '后台查询歌手', '/singer/admin/findAll', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (276, '更新歌曲图片', '/music/admin/updateImg', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (277, '查询后台歌手', '/singer/admin/list', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (278, '修改或保存歌手信息', '/singer/admin/saveOrUpdateSinger', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (279, '删除歌手信息', '/singer/admin/delete', 'DELETE', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (280, '更新歌手图片', '/singer/admin/updateImg', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (281, '根据歌手id查找歌曲', '/music/admin/listBySinger', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (283, '普通登录', '/login', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (284, '删除图片', '/article/deleteImg', 'POST', 168, '2022-03-11 22:36:04', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (286, '退出', '/user/logout', 'POST', 168, '2022-03-12 22:03:07', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (288, '删除角色', '/role/admin/delete', 'DELETE', 175, '2022-03-22 13:29:03', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (290, '强制下线', '/user/admin/forceOffline', 'PUT', 172, '2022-03-25 20:26:57', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (294, 'tokne登录', '/verifyToken', 'GET', 172, '2022-04-06 21:03:47', NULL, 0, 1);
INSERT INTO `tb_resource` VALUES (295, '获取系统信息', '/SystemInfo/getSystemInfo', 'GET', 172, '2022-04-07 13:29:48', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (296, '获取系统信息', '/sys', NULL, NULL, '2022-04-08 16:59:06', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (297, '定时任务模块', NULL, NULL, NULL, '2022-04-08 16:59:46', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (298, '添加定时任务', '/quartz/add', 'POST', 297, '2022-04-08 17:00:30', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (299, '通过id暂停任务', '/quartz/pauseJob/*', 'PUT', 297, '2022-04-08 17:01:12', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (300, '通过id恢复任务', '/quartz/resumeJob/*', 'PUT', 297, '2022-04-08 17:01:36', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (301, '通过id删除任务', '/quartz/deleteJob', 'DELETE', 297, '2022-04-08 17:02:22', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (302, '恢复所有暂停的任务', '/quartz/resumeAll', 'PUT', 297, '2022-04-08 17:03:11', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (303, '暂停所有任务', '/quartz/pauseAll', 'PUT', 297, '2022-04-08 17:03:40', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (304, '查询所有任务信息', '/quartz/admin/getAll', 'POST', 297, '2022-04-10 18:48:26', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (305, '删除账户', '/user/deleteAccount', 'DELETE', 172, '2022-04-18 16:22:52', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (307, '获取用户收藏歌单', '/musicList/getCollect', 'GET', 262, '2022-04-26 13:24:03', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (308, '添加歌曲到个人收藏', '/music/addUserCollect', 'POST', 262, '2022-04-26 19:37:00', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (309, '移除收藏音乐', '/music/removeCollect', 'DELETE', 262, '2022-04-27 20:36:19', NULL, 0, 0);
INSERT INTO `tb_resource` VALUES (310, '查询收到的回复', '/comment/getUserComment', 'GET', 176, '2022-04-29 18:55:28', '2022-04-29 19:29:37', 0, 0);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名',
  `role_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ' 创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_disable` tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `RoleName`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'ROLE_ADMIN', '管理员', '2022-03-09 21:27:27', '2022-03-28 20:07:00', 0);
INSERT INTO `tb_role` VALUES (2, 'ROLE_USER', '用户', '2022-03-09 21:27:27', '2022-04-29 18:55:46', 0);
INSERT INTO `tb_role` VALUES (4, 'ROLE_ROOT', '超级管理员', '2022-03-09 21:27:27', '2022-04-10 18:48:56', 0);

-- ----------------------------
-- Table structure for tb_singer
-- ----------------------------
DROP TABLE IF EXISTS `tb_singer`;
CREATE TABLE `tb_singer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `singer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌手名字',
  `singer_sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别 0女1男',
  `singer_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌手图片',
  `singer_birth` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `singer_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `singer_introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = ' 歌手' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_singer
-- ----------------------------
INSERT INTO `tb_singer` VALUES (25, '尧十三', 1, '/img/singerImg/singer_cover.jpg', '1986-08-25 01:00:00', '中国', '尧十三，原名唐尧，1986年8月25日出生于贵州省毕节市织金县，中国内地男歌手，毕业于武汉大学医学院临床专业。\n2011年，加入麻油叶民间民谣组织 [1]  ；同年，推出个人原创单曲《瞎子》 [2]  。2012年，为电影《浮城谜事》弹奏吉他曲《我想弹琴给你听》 [3]  。2013年，正式签约摩登天空 [1]  。 2014年，为电影《推拿》献唱片尾曲《他妈的》 [3]  。2015年，推出个人原创单曲《北方女王》 [4]  ；同年，推出首张个人音乐专辑《飞船，宇航员》 [5]  。2019年1月17日，推出个人原创单曲《很久》 [6]  。');
INSERT INTO `tb_singer` VALUES (26, '李志', 1, '/img/singerImg/singer_cover.jpg', '1978-11-13 00:00:00', '中国', '李志，1978年11月13日生于江苏省常州市金坛区，中国内地民谣男歌手、独立音乐人，东南大学工科肄业。\n2004年12月，推出首张个人音乐专辑《被禁忌的游戏》 [1]  。2005年12月，推出第二张个人音乐专辑《梵高先生》 [1]  。2006年11月，推出第三张个人音乐专辑《这个世界会好吗》 [2]  。2007年，举行“将进酒”全国巡回演唱会 [3]  。2009年9月，推出第四张个人音乐专辑《我爱南京》 [4]  。2010年9月，推出第五张个人音乐专辑《你好，郑州》。2011年9月，推出第六张个人音乐专辑《F》 [5]  。2014年11月13日，推出第七张个人音乐专辑《1701》 [6]  。2015年1月19日，凭借专辑《1701》入围“第四届阿比鹿音乐奖”年度民谣专辑 [7]  ；同年，举行“看见”全国巡回演唱会 [8]  。2016年3月14日，推出个人跨年Live数字专辑《动静》 [9]  ；同年10月18日，推出第八张个人音乐专辑《8》 [10]  ；同年11月20日，推出第九张个人音乐专辑《在每一条伤心的应天大街上》 [11]  。2018年10月23日，李志宣布与太合音乐集团旗下的麦田音乐厂牌达成合作 [12]  。');

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES (26, 'beta', '2022-04-17 15:36:12');

-- ----------------------------
-- Table structure for tb_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_unique_view`;
CREATE TABLE `tb_unique_view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL COMMENT '时间',
  `views_count` int NOT NULL COMMENT '访问量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 240 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_unique_view
-- ----------------------------
INSERT INTO `tb_unique_view` VALUES (235, '2022-04-23 11:04:59', 2);
INSERT INTO `tb_unique_view` VALUES (236, '2022-04-24 18:16:34', 1);
INSERT INTO `tb_unique_view` VALUES (237, '2022-04-25 11:34:24', 1);
INSERT INTO `tb_unique_view` VALUES (238, '2022-04-26 11:06:31', 1);
INSERT INTO `tb_unique_view` VALUES (239, '2022-04-27 11:02:58', 2);
INSERT INTO `tb_unique_view` VALUES (240, '2022-04-28 10:39:37', 1);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT ' id',
  `info_id` bigint NULL DEFAULT NULL COMMENT '用户信息id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '账号是否可用(1可用 0不可用)',
  `not_lock` tinyint(1) NULL DEFAULT 1 COMMENT '账号是否锁定(1锁定)',
  `last_login` datetime(0) NULL DEFAULT NULL COMMENT '上次登陆时间',
  `ip_addr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上次登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ip源',
  `browser` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '系统',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `USERNAME`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (11, 12, 'admin', '$2a$10$nUzL5ZZ6h808EWKeDr1Jg.qGuRPAkfCyaAjv1/9lChSsb6SK5A6wm', 1, 1, '2022-04-29 19:28:43', '192.168.85.1', '本地局域网', 'Chrome 10', 'Windows 10');
INSERT INTO `tb_user` VALUES (15, 16, '3376753860@qq.com', '$2a$10$gc9tXoYQFjRguXPo4szyf.xe9XEsW.SyuWznUMmgMXDZSdarbT3Xm', 1, 1, '2022-04-29 20:24:22', '192.168.85.1', '本地局域网', 'Chrome 10', 'Windows 10');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱号',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户简介',
  `web_site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人网站',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (12, '374368081@qq.com', '测试账号', 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/1429690935532331009.jpg', 'test', '222', '2022-03-05 17:34:53', '2022-04-05 16:52:17');
INSERT INTO `tb_user_info` VALUES (16, '3376753860@qq.com', '测试账户2', 'https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/8b5bf2be-e558-4c8e-887e-0a1ed5fdce45.png', 'hello', 'test', '2022-04-19 20:34:27', '2022-04-24 22:40:58');

SET FOREIGN_KEY_CHECKS = 1;

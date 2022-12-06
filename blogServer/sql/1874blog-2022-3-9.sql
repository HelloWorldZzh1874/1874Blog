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

 Date: 09/03/2022 22:58:12
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
) ENGINE = InnoDB AUTO_INCREMENT = 272 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for con_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `con_role_menu`;
CREATE TABLE `con_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1613 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_role_menu
-- ----------------------------
INSERT INTO `con_role_menu` VALUES (1587, 4, 1);
INSERT INTO `con_role_menu` VALUES (1588, 4, 2);
INSERT INTO `con_role_menu` VALUES (1589, 4, 6);
INSERT INTO `con_role_menu` VALUES (1590, 4, 7);
INSERT INTO `con_role_menu` VALUES (1591, 4, 8);
INSERT INTO `con_role_menu` VALUES (1592, 4, 9);
INSERT INTO `con_role_menu` VALUES (1593, 4, 10);
INSERT INTO `con_role_menu` VALUES (1594, 4, 3);
INSERT INTO `con_role_menu` VALUES (1595, 4, 11);
INSERT INTO `con_role_menu` VALUES (1596, 4, 12);
INSERT INTO `con_role_menu` VALUES (1597, 4, 202);
INSERT INTO `con_role_menu` VALUES (1598, 4, 13);
INSERT INTO `con_role_menu` VALUES (1599, 4, 14);
INSERT INTO `con_role_menu` VALUES (1600, 4, 201);
INSERT INTO `con_role_menu` VALUES (1601, 4, 4);
INSERT INTO `con_role_menu` VALUES (1602, 4, 16);
INSERT INTO `con_role_menu` VALUES (1603, 4, 15);
INSERT INTO `con_role_menu` VALUES (1604, 4, 17);
INSERT INTO `con_role_menu` VALUES (1605, 4, 18);
INSERT INTO `con_role_menu` VALUES (1606, 4, 19);
INSERT INTO `con_role_menu` VALUES (1607, 4, 20);
INSERT INTO `con_role_menu` VALUES (1608, 4, 208);
INSERT INTO `con_role_menu` VALUES (1609, 4, 209);
INSERT INTO `con_role_menu` VALUES (1610, 4, 210);
INSERT INTO `con_role_menu` VALUES (1611, 4, 211);
INSERT INTO `con_role_menu` VALUES (1612, 4, 5);

-- ----------------------------
-- Table structure for con_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `con_role_resource`;
CREATE TABLE `con_role_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5276 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_role_resource
-- ----------------------------
INSERT INTO `con_role_resource` VALUES (5275, 4, 217);
INSERT INTO `con_role_resource` VALUES (5276, 4, 250);
INSERT INTO `con_role_resource` VALUES (5277, 2, 250);
INSERT INTO `con_role_resource` VALUES (5278, 4, 182);

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_role_user
-- ----------------------------
INSERT INTO `con_role_user` VALUES (14, 4, 11);

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '作者',
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
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_article
-- ----------------------------

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_category
-- ----------------------------

-- ----------------------------
-- Table structure for tb_chat_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_record`;
CREATE TABLE `tb_chat_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '聊天内容',
  `ip_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `type` tinyint NULL DEFAULT NULL COMMENT '类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1222 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_chat_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '评论用户Id',
  `article_id` int NULL DEFAULT NULL COMMENT '评论文章id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime(0) NOT NULL COMMENT '评论时间',
  `reply_id` int NULL DEFAULT NULL COMMENT '回复用户id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论id',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_comment_article`(`article_id`) USING BTREE,
  INDEX `fk_comment_parent`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 301 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 212 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', '2021-01-26 17:06:51', '2021-01-26 17:06:53', 1, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (2, '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07', '2021-01-25 20:43:09', 2, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2021-01-25 20:44:20', 3, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (4, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', 5, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (5, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', '2021-01-26 17:22:38', '2021-08-18 14:04:54', 8, NULL, 0, 0);
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
INSERT INTO `tb_menu` VALUES (18, '关于我', '/about', '/about/About.vue', 'el-icon-myguanyuwo', '2021-01-26 14:42:05', '2021-08-04 13:25:02', 4, 4, 0, 0);
INSERT INTO `tb_menu` VALUES (19, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', 6, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (20, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21', '2021-01-31 15:53:25', 1, 19, 0, 0);
INSERT INTO `tb_menu` VALUES (201, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51', '2021-02-05 14:59:53', 7, 202, 0, 0);
INSERT INTO `tb_menu` VALUES (202, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59', '2021-02-06 23:45:03', 4, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (208, '音乐管理', '/music-submenu', 'Layout', 'icon-music', '2021-08-18 14:04:48', '2021-08-18 14:09:57', 7, NULL, 0, 0);
INSERT INTO `tb_menu` VALUES (209, '歌手管理', '/singer', '/singer/Singer.vue', 'el-icon-myuser', '2021-08-18 14:06:59', '2021-08-18 14:07:41', 1, 208, 0, 0);
INSERT INTO `tb_menu` VALUES (210, '音乐列表', '/musicList', '/musicList/MusicList.vue', 'icon-musiclist', '2021-08-18 14:09:38', '2021-08-18 14:09:38', 2, 208, 0, 0);
INSERT INTO `tb_menu` VALUES (211, '歌手歌曲', '/singer/musics', '/singer/singerMusic.vue', 'icon-music', '2021-08-19 16:33:42', '2021-08-19 16:40:30', 7, 208, 0, 1);

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
  `time` tinyint(1) NULL DEFAULT NULL COMMENT '弹幕速度',
  `create_time` datetime(0) NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3455 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_message
-- ----------------------------

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
  `music_al` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲专辑',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '歌曲信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_music
-- ----------------------------

-- ----------------------------
-- Table structure for tb_music_list
-- ----------------------------
DROP TABLE IF EXISTS `tb_music_list`;
CREATE TABLE `tb_music_list`  (
  `id` int NOT NULL COMMENT ' 主键',
  `list_id` int NULL DEFAULT NULL COMMENT '列表id:1热歌榜,0新歌榜',
  `music_id` int NULL DEFAULT NULL COMMENT ' 歌曲id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_music_list
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 252 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------
INSERT INTO `tb_operation_log` VALUES (253, '角色模块', '修改', '/role/testOpt', 'com.zzh.controller.RoleController.testOpt', '测试标签', '[\"1\"]', 'POST', '\"test1\"', 11, 'admin', '192.168.85.1', '本地局域网', '2022-03-09 14:31:45');

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
) ENGINE = InnoDB AUTO_INCREMENT = 284 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `tb_resource` VALUES (178, '查看博客信息', '/', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (179, '查看关于我信息', '/about', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (180, '查看后台信息', '/admin', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (181, '修改关于我信息', '/admin/about', 'PUT', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (182, '查看后台文章', '/admin/getArticles', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (183, '添加或修改文章', '/admin/article/saveAndUpdateArticle', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (184, '恢复或删除文章', '/admin/article/deleteOrRecArticle', 'PUT', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (185, '物理删除文章', '/admin/article/deleteArticles', 'DELETE', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (186, '上传文章图片', '/admin/article/uploadImg', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (187, '查看文章选项', '/admin/article/options', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (188, '修改文章置顶', '/admin/article/top/*', 'PUT', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (189, '根据id查看后台文章', '/admin/article/selectArticlesById/*', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (190, '查看后台分类列表', '/admin/categories', 'GET', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (191, '添加或修改分类', '/admin/categories/saveOrUpdateCategory', 'POST', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (192, '删除分类', '/admin/categories/deleteCategory', 'DELETE', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (193, '查询后台评论', '/admin/comment/getBackComments', 'GET', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (194, '删除或恢复评论', '/admin/comment/updateCommentsStatus', 'PUT', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (195, '物理删除评论', '/admin/comment/deleteComments', 'DELETE', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (196, '查看后台友链列表', '/admin/link/getLinks', 'GET', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (197, '保存或修改友链', '/admin/links', 'POST', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (198, '删除友链', '/admin/deleteLinks', 'DELETE', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (199, '查看菜单列表', '/admin/menu/getMenu', 'POST', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (200, '查看后台留言列表', '/admin/message/getBackMessages', 'GET', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (201, '删除留言', '/admin/message/deleteMessages', 'DELETE', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (202, '查看公告', '/admin/notice', 'GET', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (203, '修改公告', '/admin/notice', 'PUT', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (204, '查看操作日志', '/admin/operation/logs', 'GET', 169, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (205, '删除操作日志', '/admin/operation/logs', 'DELETE', 169, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (206, '查看资源列表', '/admin/resources', 'GET', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (207, '新增或修改资源', '/admin/resources/addOrUpdate', 'POST', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (208, '删除资源', '/admin/resources/delete', 'DELETE', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (209, '导入swagger接口', '/admin/resources/import/swagger', 'GET', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (210, '保存或更新角色', '/admin/saveOrUpdateRole', 'POST', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (211, '查看角色菜单选项', '/admin/role/menus', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (212, '查看角色资源选项', '/admin/role/resources', 'GET', 177, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (213, '查询角色列表', '/admin/role/roles', 'GET', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (214, '查看后台标签列表', '/admin/tags/selectTags', 'GET', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (215, '添加或修改标签', '/admin/tags/saveOrUpdateTag', 'POST', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (216, '删除标签', '/admin/tags/deleteTag', 'DELETE', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (217, '查看用户菜单', '/menu/admin/getMenus', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (218, '查看后台用户列表', '/admin/user/users', 'GET', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (219, '修改用户禁用状态', '/admin/userInfo/changeEnable/*', 'PUT', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (220, '查看在线用户', '/admin/users/online', 'GET', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (221, '下线用户', '/admin/users/online/*', 'DELETE', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (222, '修改管理员密码', '/admin/users/password', 'PUT', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (223, '查询用户角色选项', '/admin/role/roleList', 'GET', 175, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (224, '修改用户角色', '/admin/userInfo/updateRole', 'PUT', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (225, '查看首页文章', '/articles', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (226, '查看文章归档', '/articles/archives', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (227, '点赞文章', '/articles/like', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (228, '查看最新文章', '/articles/newest', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (229, '搜索文章', '/articles/search', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (230, '根据id查看文章', '/articles/*', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (231, '查看分类列表', '/categories', 'GET', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (232, '查看分类下对应的文章', '/categories/*', 'GET', 165, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (233, '查询评论', '/comments', 'GET', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (234, '添加评论或回复', '/comments', 'POST', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (235, '评论点赞', '/comments/like', 'POST', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (236, '查询评论下的回复', '/comments/replies/*', 'GET', 176, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (237, '查看友链列表', '/links', 'GET', 167, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (238, '查看留言列表', '/messages', 'GET', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (239, '添加留言', '/messages', 'POST', 173, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (240, '查看标签列表', '/tags', 'GET', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (241, '查看分类下对应的文章', '/tags/*', 'GET', 170, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (242, '用户注册', '/users', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (243, '修改用户头像', '/users/avatar', 'POST', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (244, '发送邮箱验证码', '/users/code', 'GET', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (245, '修改用户资料', '/users/info', 'PUT', 171, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (246, 'qq登录', '/users/oauth/qq', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (247, '微博登录', '/users/oauth/weibo', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (248, '修改密码', '/users/password', 'PUT', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (249, '上传语音', '/voice', 'POST', 166, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (250, '上传文章封面', '/admin/article/uploadCover', 'POST', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (252, '查看后台菜单', '/admin/menus', 'GET', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (253, '更新或添加菜单数据', '/admin/updateOrSaveMenu', 'POST', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (254, '删除菜单数据', '/admin/deleteMenu', 'DELETE', 174, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (262, '音乐模块', NULL, NULL, NULL, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (263, '获取歌单音乐id列表', '/musicList', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (264, '查找指定音乐url', '/music/musicUrl', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (265, '获取歌曲歌词', '/music/lyric', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (266, '获取歌曲详情', '/music/getMusicInfo', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (267, '模糊查询', '/music/getMusicLike', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (268, '查询用户文章', '/articles/getUserArticle', 'GET', 168, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);
INSERT INTO `tb_resource` VALUES (269, '查询后台歌曲', '/admin/music/getMusicLists', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (271, '添加歌曲', '/admin/music/add', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (272, '更新歌曲文件', '/admin/music/updateFile/*', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (273, '更新歌曲信息', '/admin/music/updateMusicInfo', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (274, '删除歌曲', '/admin/music/delete', 'DELETE', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (275, '后台查询歌手', '/admin/singer/findAll', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (276, '更新歌曲图片', '/admin/music/updateImg', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (277, '查询后台歌手', '/admin/singer/list', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (278, '修改或保存歌手信息', '/admin/singer/saveOrUpdateSinger', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (279, '删除歌手信息', '/admin/singer/delete', 'DELETE', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (280, '更新歌手图片', '/admin/singer/updateImg', 'POST', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (281, '根据歌手id查找歌曲', '/admin/music/listBySinger', 'GET', 262, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 0);
INSERT INTO `tb_resource` VALUES (283, '普通登录', '/login', 'POST', 172, '2022-03-09 21:27:51', '2022-03-09 21:27:47', 0, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'ROLE_ADMIN', '管理员', '2022-03-09 21:27:27', '2022-03-09 21:27:39', 0);
INSERT INTO `tb_role` VALUES (2, 'ROLE_USER', '用户', '2022-03-09 21:27:27', '2022-03-09 21:27:39', 0);
INSERT INTO `tb_role` VALUES (4, 'ROLE_ROOT', '超级管理员', '2022-03-09 21:27:27', '2022-03-09 21:27:39', 0);

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
  `singer_introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = ' 歌手' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_singer
-- ----------------------------

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------

-- ----------------------------
-- Table structure for tb_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_unique_view`;
CREATE TABLE `tb_unique_view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL COMMENT '时间',
  `views_count` int NOT NULL COMMENT '访问量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 231 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_unique_view
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (11, 12, 'admin', '$2a$10$XqjK7hO9jqc7YpBqBsySK.79VaqEwbdGsmkTJmCgGoMz0qrj.xz9a', 1, 1, '2022-03-09 14:31:26', '192.168.85.1', '本地局域网', 'Unknown', 'Unknown');

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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (12, '374368081@qq.com', 'admin', '', 'test', 'test', '2022-03-05 17:34:53', NULL);

SET FOREIGN_KEY_CHECKS = 1;

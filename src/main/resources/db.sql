SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `salt` varchar(45) NOT NULL COMMENT '密码盐',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:正常,1:删除(暂停),2:待审核,3:永久删除',
  `create_date` datetime NOT NULL COMMENT '注册时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_account`
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `account` int(11) NOT NULL DEFAULT '0' COMMENT '用户账户',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_account_log`
-- ----------------------------
DROP TABLE IF EXISTS `user_account_log`;
CREATE TABLE `user_account_log` (
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `operation` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT '积分和账户操作记录',
  `op_type` tinyint(4) NOT NULL COMMENT '0:积分, 1:余额',
  `create_date` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

SET FOREIGN_KEY_CHECKS = 1;
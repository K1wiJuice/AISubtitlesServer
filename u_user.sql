-- --------------------------------------------------------
-- 主机:                           192.168.1.102
-- 服务器版本:                        5.7.22-0ubuntu18.04.1 - (Ubuntu)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 landgavin.u_user 结构
CREATE TABLE IF NOT EXISTS `u_user` (
  `id` char(36) NOT NULL,
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(8) DEFAULT NULL COMMENT '性别',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `weChat` varchar(64) DEFAULT NULL COMMENT '微信号',
  `qq_num` varchar(50) DEFAULT NULL COMMENT 'QQ号码',
  `phone_num` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `role_id` int(11) DEFAULT NULL COMMENT '角色',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `ID_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `hobby` varchar(200) DEFAULT NULL COMMENT '爱好',
  `province_num` varchar(16) DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(16) DEFAULT NULL COMMENT '省名称',
  `city_num` varchar(16) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(16) DEFAULT NULL COMMENT '城市名称',
  `country_num` varchar(16) DEFAULT NULL COMMENT '县编码',
  `country_name` varchar(16) DEFAULT NULL COMMENT '县名称',
  `employer` varchar(100) DEFAULT NULL COMMENT '工作单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`nickname`),
  UNIQUE KEY `Index 3` (`phone_num`),
  UNIQUE KEY `Index 4` (`qq_num`),
  UNIQUE KEY `Index 5` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  landgavin.u_user 的数据：~15 rows (大约)
DELETE FROM `u_user`;
/*!40000 ALTER TABLE `u_user` DISABLE KEYS */;
INSERT INTO `u_user` (`id`, `real_name`, `photo`, `sex`, `age`, `nickname`, `weChat`, `qq_num`, `phone_num`, `birthday`, `role_id`, `email`, `ID_card`, `hobby`, `province_num`, `province_name`, `city_num`, `city_name`, `country_num`, `country_name`, `employer`) VALUES
	('1b5b2539-99b5-4794-9eba-8b5eca986e79', 'schooladmin1', NULL, '男', 12, 'schooladmin1', '12', '12121212121', '1212121221', '2018-07-10', 6, '21212112@qq.com', '1631131311631', '1332132132321', '370000', '山东省', '370100', '济南市', '370102', '历下区', '1校'),
	('3d5caaf7-e71b-4b81-b812-9a651465ef92', '1校老师1', NULL, '男', 12, '1校老师1', '111', '111', '111', '2018-07-11', 2, 'laoshi11', '111', '111', '370000', '山东省', '370100', '济南市', '370102', '历下区', '111'),
	('4646cdc0-8e6b-4482-9cd5-7fca6bc69e6d', '校长1', NULL, '男', 12, 'xiaozhang1', '12', '12', '12', '2008-01-01', 3, 'xiaozhang1@qq.com', '12', '12', '370000', '山东省', '370100', '济南市', '370102', '历下区', '12'),
	('4c14c67c-5509-499b-a3d5-e15eeb88a493', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '15264149292', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('51cace35-6fc2-40fe-be9a-f3b71ff0ed7b', '测试领导', NULL, '男', 12, '我是领导', '12', '15161', '156165', '2015-02-05', 4, 'lingdao@qq.com', '213', '23', '370000', '山东省', '370100', '济南市', '370102', '历下区', '124'),
	('6161e9bd-053b-4a48-a207-2a69305e3126', 'testStudent2', NULL, '女', NULL, NULL, NULL, '1231231232', '1231231232', '1992-11-11', 1, '1231231232@qq.com', NULL, NULL, '370000', '山东省', '370100', '济南市', '370102', '历下区', NULL),
	('7864294a-047d-42ea-82ae-ce86286f11fb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('7ac07928-4e42-42a1-a63a-07ee2cb88658', 'testStudent4', NULL, '男', NULL, NULL, NULL, '1231231234', '1231231234', '1992-11-11', 1, '1231231234@qq.com', NULL, NULL, '370000', '山东省', '370100', '济南市', '370102', '历下区', NULL),
	('7ddd685f-d1ad-4803-9a8d-040ab2aa1f25', '1校老师2', NULL, '男', 12, '1校老师2', '12', '222', '222', '2018-07-10', 2, 'laoshi12@qq.com', '222', '222', '370000', '山东省', '370100', '济南市', '370102', '历下区', '133'),
	('928e735e-f4e9-4368-8ad2-306194d5de8f', 'testStudent3', NULL, '女', NULL, NULL, NULL, '1231231233', '1231231233', '1992-11-11', 1, '1231231233@qq.com', NULL, NULL, '370000', '山东省', '370100', '济南市', '370102', '历下区', NULL),
	('9690cc83-7248-45ea-9b6e-244d889233b8', 'testStudent1', NULL, '男', NULL, NULL, NULL, '2174936217@qq.com', '1231231231', '1992-11-11', 4, '1231231231@qq.com', NULL, NULL, '370000', '山东省', '370100', '济南市', '370102', '历下区', NULL),
	('9c675575-e6bf-41c5-8396-5dc15d0c3366', '校长2', NULL, '男', 12, 'xiaozhang2', '12', '22', '22', '2005-05-05', 3, 'xiaozhang2@qq.com', '22', '22', '370000', '山东省', '370100', '济南市', '370102', '历下区', '22'),
	('a1849042-d1cb-40e9-9316-4313c5f7e7b0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '614119717@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('bc435e44-f3ab-4b09-b8e9-a6154004c2c4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('d6d3d039-80bc-11e8-abe7-74d43544bb53', 'gavin', 'http://provindence.oss-cn-qingdao.aliyuncs.com/photo1531108539241.jpeg?Expires=1846468529&OSSAccessKeyId=LTAIdiIueBJfJG9Q&Signature=mEhmm8vb%2F3EQtA4wIYanlPyDppQ%3D', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 'beargavin@aliyun.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `u_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

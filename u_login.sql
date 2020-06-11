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

-- 导出  表 landgavin.u_login 结构
CREATE TABLE IF NOT EXISTS `u_login` (
  `user_id` char(36) NOT NULL COMMENT '用户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `login_type_id` int(11) NOT NULL COMMENT '登录类型',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态：0有效，1无效',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  landgavin.u_login 的数据：~17 rows (大约)
DELETE FROM `u_login`;
/*!40000 ALTER TABLE `u_login` DISABLE KEYS */;
INSERT INTO `u_login` (`user_id`, `name`, `password`, `login_type_id`, `state`) VALUES
	('1b5b2539-99b5-4794-9eba-8b5eca986e79', 'schooladmin1', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('3d5caaf7-e71b-4b81-b812-9a651465ef92', 'laoshi11', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('4646cdc0-8e6b-4482-9cd5-7fca6bc69e6d', 'xiaozhang1', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('4c14c67c-5509-499b-a3d5-e15eeb88a493', '15201270478', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL),
	('51cace35-6fc2-40fe-be9a-f3b71ff0ed7b', 'lingdao', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('6161e9bd-053b-4a48-a207-2a69305e3126', '3701020001001002', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('65185d7a-34d7-4256-80da-2a1cfed16cea', 'yangkang', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('7864294a-047d-42ea-82ae-ce86286f11fb', NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, NULL),
	('7ac07928-4e42-42a1-a63a-07ee2cb88658', '3701020001001004', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('7ddd685f-d1ad-4803-9a8d-040ab2aa1f25', 'laoshi12', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('928e735e-f4e9-4368-8ad2-306194d5de8f', '3701020001001003', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('9690cc83-7248-45ea-9b6e-244d889233b8', '3701020001001001', 'e10adc3949ba59abbe56e057f20f883e', 4, NULL),
	('9c675575-e6bf-41c5-8396-5dc15d0c3366', 'xiaozhang2', '25d55ad283aa400af464c76d713c07ad', 4, NULL),
	('a1849042-d1cb-40e9-9316-4313c5f7e7b0', NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, NULL),
	('bc435e44-f3ab-4b09-b8e9-a6154004c2c4', NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, NULL),
	('d6d3d039-80bc-11e8-abe7-74d43544bb53', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 4, NULL),
	('f3b149c7-1ce2-4603-b0d7-bd51d45e2ca1', 'dianliu', '25d55ad283aa400af464c76d713c07ad', 4, NULL);
/*!40000 ALTER TABLE `u_login` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

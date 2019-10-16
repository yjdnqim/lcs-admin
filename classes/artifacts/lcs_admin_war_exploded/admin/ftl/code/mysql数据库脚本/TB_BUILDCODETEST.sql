
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TB_BUILDCODETEST`
-- ----------------------------
DROP TABLE IF EXISTS `TB_BUILDCODETEST`;
CREATE TABLE `TB_BUILDCODETEST` (
 		`BUILDCODETEST_ID` varchar(100) NOT NULL,
		`F1` varchar(255) DEFAULT NULL COMMENT '字段一',
		`F2` int(11) NOT NULL COMMENT '字段二',
		`F3` varchar(255) DEFAULT NULL COMMENT '字段3',
  		PRIMARY KEY (`BUILDCODETEST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

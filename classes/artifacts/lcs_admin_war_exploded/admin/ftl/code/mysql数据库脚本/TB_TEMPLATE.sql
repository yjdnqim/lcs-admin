
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TB_TEMPLATE`
-- ----------------------------
DROP TABLE IF EXISTS `TB_TEMPLATE`;
CREATE TABLE `TB_TEMPLATE` (
 		`TEMPLATE_ID` varchar(100) NOT NULL,
		`FIELD_1` varchar(255) DEFAULT NULL COMMENT '字段_String',
		`FIELD_2` int(11) NOT NULL COMMENT '字段_Int',
		`FIELD_3` varchar(255) DEFAULT NULL COMMENT '字段_Date',
  		PRIMARY KEY (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

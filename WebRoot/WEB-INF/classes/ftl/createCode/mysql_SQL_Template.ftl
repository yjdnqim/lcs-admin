
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `${tablePrefixName}_${objectNameUpper}`
-- ----------------------------
DROP TABLE IF EXISTS `${tablePrefixName}_${objectNameUpper}`;
CREATE TABLE `${tablePrefixName}_${objectNameUpper}` (
 		`${objectNameUpper}_ID` varchar(100) NOT NULL,
	<#list fieldList as var>
		<#if var[1] == 'Integer'>
		`${var[0]}` int(11) NOT NULL COMMENT '${var[2]}',
		<#else>
		`${var[0]}` varchar(255) DEFAULT NULL COMMENT '${var[2]}',
		</#if>
	</#list>
  		PRIMARY KEY (`${objectNameUpper}_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

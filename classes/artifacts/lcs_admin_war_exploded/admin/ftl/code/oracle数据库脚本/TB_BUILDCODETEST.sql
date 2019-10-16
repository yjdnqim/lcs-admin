-- ----------------------------
-- Table structure for "C##NEWO"."TB_BUILDCODETEST"
-- ----------------------------
-- DROP TABLE "C##NEWO"."TB_BUILDCODETEST";
CREATE TABLE "C##NEWO"."TB_BUILDCODETEST" (
	"F1" VARCHAR2(255 BYTE) NULL ,
	"F2" NUMBER(10) NULL ,
	"F3" VARCHAR2(255 BYTE) NULL ,
	"BUILDCODETEST_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##NEWO"."TB_BUILDCODETEST"."F1" IS '字段一';
COMMENT ON COLUMN "C##NEWO"."TB_BUILDCODETEST"."F2" IS '字段二';
COMMENT ON COLUMN "C##NEWO"."TB_BUILDCODETEST"."F3" IS '字段3';
COMMENT ON COLUMN "C##NEWO"."TB_BUILDCODETEST"."BUILDCODETEST_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table TB_BUILDCODETEST
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."TB_BUILDCODETEST"

-- ----------------------------

ALTER TABLE "C##NEWO"."TB_BUILDCODETEST" ADD CHECK ("BUILDCODETEST_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."TB_BUILDCODETEST"
-- ----------------------------
ALTER TABLE "C##NEWO"."TB_BUILDCODETEST" ADD PRIMARY KEY ("BUILDCODETEST_ID");

-- ----------------------------
-- Table structure for "C##NEWO"."TB_TEMPLATE"
-- ----------------------------
-- DROP TABLE "C##NEWO"."TB_TEMPLATE";
CREATE TABLE "C##NEWO"."TB_TEMPLATE" (
	"FIELD_1" VARCHAR2(255 BYTE) NULL ,
	"FIELD_2" NUMBER(10) NULL ,
	"FIELD_3" VARCHAR2(255 BYTE) NULL ,
	"TEMPLATE_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##NEWO"."TB_TEMPLATE"."FIELD_1" IS '字段_String';
COMMENT ON COLUMN "C##NEWO"."TB_TEMPLATE"."FIELD_2" IS '字段_Int';
COMMENT ON COLUMN "C##NEWO"."TB_TEMPLATE"."FIELD_3" IS '字段_Date';
COMMENT ON COLUMN "C##NEWO"."TB_TEMPLATE"."TEMPLATE_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table TB_TEMPLATE
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."TB_TEMPLATE"

-- ----------------------------

ALTER TABLE "C##NEWO"."TB_TEMPLATE" ADD CHECK ("TEMPLATE_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."TB_TEMPLATE"
-- ----------------------------
ALTER TABLE "C##NEWO"."TB_TEMPLATE" ADD PRIMARY KEY ("TEMPLATE_ID");

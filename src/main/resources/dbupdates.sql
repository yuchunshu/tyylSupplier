--公文添加摘要字段  by LJX 20161219
ALTER TABLE oa_doc_file ADD (digest VARCHAR(2000));

--常务会议题增加字段 by LJX 20161220
ALTER TABLE oa_meeting_topic ADD (sort INT);
ALTER TABLE oa_meeting_topic ADD (routine_id VARCHAR(36));
ALTER TABLE oa_meeting_topic ADD (relative_depts VARCHAR(2000));
--增加常委会表  by LJX 20161220
CREATE TABLE
    oa_meeting_routine
    (
        routine_id VARCHAR(36) NOT NULL,
        title VARCHAR(250),
        period INT,
        TIME INT,
        start_time DATETIME,
        end_time DATETIME,
        is_cur CHAR(1),
        creator_id bigint,
        creator_name VARCHAR(50),
        creat_dept_id bigint,
        create_dep VARCHAR(100),
        remark VARCHAR(1000),
        create_time DATETIME,
        PRIMARY KEY (routine_id)
    );


-- 环节实例增加预处理排序 by yuandl 20161220
ALTER TABLE wf_node_inst ADD COLUMN process_sort INTEGER;
--  增加环节配置自动审批环节 by yuandl 20161220
ALTER TABLE wf_flow_nodes ADD COLUMN process_node_id VARCHAR(10);

-- 修改政府办收文流程，合并处级领导审批 by yuandl 20161220
UPDATE wf_node SET node_name='处级领导审批' WHERE node_id='N105';
UPDATE wf_flow_nodes SET next_node_id4=NULL WHERE flow_id='F01' AND node_id='N103';
UPDATE wf_flow_nodes SET next_node_id3=NULL,process_node_id='N105' WHERE flow_id='F01' AND node_id='N104';
UPDATE wf_flow_nodes SET next_node_id4=NULL WHERE flow_id='F01' AND node_id='N105';
UPDATE wf_node_receive SET duty_ids=CONCAT('1,',duty_ids) WHERE flow_id='F01' AND node_id='N105' AND receive_dep_id='4';
INSERT INTO `dmoa`.`wf_node_func` (`func_id`, `flow_id`, `node_id`, `func_flag`) VALUES ('155', 'F01', 'N104', 'S7');

-- 修改政府办发文流程
UPDATE wf_node SET node_name='处级领导审批' WHERE node_id='N204';
UPDATE wf_flow_nodes SET next_node_id3='N206',next_node_id4=NULL WHERE flow_id='F02' AND node_id='N201';
UPDATE wf_flow_nodes SET next_node_id3='N206',next_node_id4=NULL WHERE flow_id='F02' AND node_id='N202';
UPDATE wf_flow_nodes SET next_node_id3=NULL,process_node_id='N204' WHERE flow_id='F02' AND node_id='N203';
UPDATE wf_flow_nodes SET next_node_id4=NULL WHERE flow_id='F02' AND node_id='N204';
UPDATE wf_node_receive SET duty_ids=CONCAT('1,',duty_ids) WHERE flow_id='F02' AND node_id='N204' AND receive_dep_id='4';
INSERT INTO `dmoa`.`wf_node_func` (`func_id`, `flow_id`, `node_id`, `func_flag`) VALUES ('156', 'F02', 'N203', 'S7');

-- 更新环节已读状态 by yuandl 20161221
UPDATE wf_node_inst SET read_flag = '1' WHERE inst_status='1';

-- 修改代拟发文流程 by yuandl 20161221
UPDATE wf_node SET node_name='处级领导审批' WHERE node_id='N305';
UPDATE wf_flow_nodes SET next_node_id3='N307',next_node_id4=NULL WHERE flow_id='F03' AND node_id='N303';
UPDATE wf_flow_nodes SET next_node_id3=NULL,process_node_id='N305' WHERE flow_id='F03' AND node_id='N304';
UPDATE wf_flow_nodes SET next_node_id4=NULL WHERE flow_id='F03' AND node_id='N305';
UPDATE wf_node_receive SET duty_ids=CONCAT('1,',duty_ids) WHERE flow_id='F03' AND node_id='N305' AND receive_dep_id='4';
INSERT INTO `dmoa`.`wf_node_func` (`func_id`, `flow_id`, `node_id`, `func_flag`) VALUES ('157', 'F03', 'N304', 'S7');

-- 环节实例增加字段以实现隐藏未办理的人员 by yuandl 20161221
ALTER TABLE wf_node_inst ADD COLUMN skipped char(1);

--增加附件标识字段 by LJX 20161221
ALTER TABLE oa_meeting_routine ADD (attach_flag CHAR(1));
--议题类型  by LJX 20161221
ALTER TABLE oa_meeting_topic ADD (type CHAR(1));


-- 增加督查室、应急办与秘书股同样的环节 by yuandl 20161226
INSERT INTO `dmoa`.`wf_node_receive` (`id`, `flow_id`, `node_id`, `receive_flag`, `receiver_id`, `receive_dep_id`, `receive_ancestor_dep`, `duty_ids`) VALUES ('40', 'F01', 'N103', '1', NULL, '31', '0', NULL);
INSERT INTO `dmoa`.`wf_node_receive` (`id`, `flow_id`, `node_id`, `receive_flag`, `receiver_id`, `receive_dep_id`, `receive_ancestor_dep`, `duty_ids`) VALUES ('41', 'F02', 'N202', '1', NULL, '31', '0', NULL);
INSERT INTO `dmoa`.`wf_node_receive` (`id`, `flow_id`, `node_id`, `receive_flag`, `receiver_id`, `receive_dep_id`, `receive_ancestor_dep`, `duty_ids`) VALUES ('42', 'F03', 'N303', '1', NULL, '31', '0', NULL);
-- 增加公文传阅字段 by yuandl 20161226
ALTER TABLE oa_doc_read ADD COLUMN copies VARCHAR(10) NULL;
ALTER TABLE oa_doc_read ADD COLUMN dense CHAR(1) NULL;

--用户表添加 锁定时间 by LJX
ALTER TABLE sys_user ADD COLUMN locked_time DATETIME NULL;

--流程基础表设计调整 hzr 20170111
ALTER TABLE wf_node_dept DROP COLUMN receive_flag;
ALTER TABLE wf_node_dept DROP COLUMN receiver_id;
ALTER TABLE wf_flow_nodes CHANGE next_node_id next_node_ids VARCHAR(100);
ALTER TABLE wf_flow_nodes ADD (edit_flag CHAR(1), doc_editable CHAR(1), func_btns VARCHAR(100), opinion_tag VARCHAR(20), receiver_ids VARCHAR(100));
    
RENAME TABLE wf_node_mans TO wf_his_dealer;

--修改日志为uuid，hzr 20170223
DROP TABLE SYS_LOG;
CREATE TABLE SYS_LOG (
        ID VARCHAR2(36) NOT NULL, 
        CONTENT VARCHAR2(2000),
        IP VARCHAR2(255),
        LOG_TYPE CHAR(1),
        OPERATE_DATE DATE,
        SJ_TYPE CHAR(1),
        URL VARCHAR2(255), 
        OPERATOR_ID NUMBER(18), 
        CONSTRAINT PK_SYS_LOG PRIMARY KEY (ID)
);

--车辆申请表添加  by dengl 20180809
DROP TABLE IF EXISTS `oa_official_car`;
CREATE TABLE `oa_official_car` (
  `car_id` varchar(36) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(40) DEFAULT NULL,
  `dept_id` bigint(18) DEFAULT NULL,
  `dept_name` varchar(40) DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `car_number` varchar(40) DEFAULT NULL,
  `driver` varchar(40) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `business_type` char(1) DEFAULT NULL,
  `version_` int(11) DEFAULT NULL,
  `flow_id` varchar(20) DEFAULT NULL,
  `flow_inst_id` varchar(36) DEFAULT NULL,
  `rental_place` varchar(255) DEFAULT NULL,
  `departure_place` varchar(255) DEFAULT NULL,
  `week` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`car_id`),
  KEY `flow_id` (`flow_id`),
  KEY `flow_inst_id` (`flow_inst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

comment on table "oa_official_car" is
'用车申请';

comment on column "oa_official_car"."create_time" is
'申请时间';

comment on column "oa_official_car"."creator_id" is
'创建人 user_id';

comment on column "oa_official_car"."status" is
'0暂存 1在办 2办结';

comment on column "oa_official_car"."version_" is
'版本号（用于多用户保存互斥判断）';

--请假申请表添加  by dengl 20180809
DROP TABLE IF EXISTS `oa_leave`;
CREATE TABLE `oa_leave` (
  `id` varchar(36) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `leave_type` char(1) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `leave_day` bigint(4) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `version_` int(11) DEFAULT NULL,
  `flow_id` varchar(20) DEFAULT NULL,
  `flow_inst_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

comment on table "oa_leave" is
'请假申请';

comment on column "oa_leave"."create_date" is
'创建日期';

comment on column "oa_leave"."creator_id" is
'创建人 user_id';

comment on column "oa_leave"."leave_type" is
'1公休假  2探亲假  3事假  4病假  5其他';

comment on column "oa_leave"."status" is
'0暂存 1在办 2待归档 3归档 4删除 5退回 6取回';

comment on column "oa_leave"."version_" is
'版本号（用于多用户保存互斥判断）';

--流程定义表添加流程类别字段 by dengl 20180809
ALTER TABLE wf_flow ADD COLUMN flow_sort CHAR(1) NULL;

--故障報告申請表添加by dengl 20180814
DROP TABLE IF EXISTS `oa_equipment_repair`;
CREATE TABLE `oa_equipment_repair` (
  `repair_id` varchar(36) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator_id` bigint(18) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `dept_id` bigint(18) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `device_model` varchar(50) DEFAULT NULL,
  `device_number` varchar(50) DEFAULT NULL,
  `failure_reason` varchar(1000) DEFAULT NULL,
  `repair_type` char(1) DEFAULT NULL,
  `expect_cost` varchar(10) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `version_` int(11) DEFAULT NULL,
  `flow_id` varchar(10) DEFAULT NULL,
  `flow_inst_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`repair_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

comment on table "oa_equipment_repair" is
'设备报修申请';

comment on column "oa_equipment_repair"."create_time" is
'申请时间';

comment on column "oa_equipment_repair"."creator_id" is
'创建人 user_id';

comment on column "oa_equipment_repair"."repair_type" is
'0自行维修1返厂维修 2上门维修';

comment on column "oa_equipment_repair"."status" is
'0暂存 1在办 2办结';

comment on column "oa_equipment_repair"."version_" is
'版本号（用于多用户保存互斥判断）';


-- 添加移动端图标背景色 by shicx 2018-08-13
ALTER TABLE sys_desktop_shortcut ADD appcolor VARCHAR(20);
ALTER TABLE sys_power ADD appcolor VARCHAR(20) NULL;
-- 移动端修改版本号字段与添加热更新版本号  by shicx 2018-08-13
ALTER TABLE `sys_apk_info`
CHANGE COLUMN
`version` `app_version` VARCHAR(30) NULL DEFAULT NULL,
ADD COLUMN
`app_platform` CHAR(1) NULL AFTER `app_version`;

ALTER TABLE `sys_mobile_device`
ADD COLUMN `chcp_version` VARCHAR(45) NULL;

--訂餐表添加  by dengl 20180814
DROP TABLE IF EXISTS `oa_meal`;
CREATE TABLE `oa_meal` (
  `meal_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(18) DEFAULT NULL,
  `dept_id` bigint(18) DEFAULT NULL,
  `meal_date` datetime DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `meal_user_id` bigint(18) DEFAULT NULL,
  `meal_dept_id` bigint(18) DEFAULT NULL,
  `meal_place` char(1) DEFAULT NULL,
  `meal_type` char(1) DEFAULT NULL,
  `check_id` bigint(18) DEFAULT NULL,
  `meal_user_name` varchar(255) DEFAULT NULL,
  `meal_dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`meal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

comment on table "oa_meal" is
'订餐申请';

comment on column "oa_meal"."create_time" is
'创建时间';

comment on column "oa_meal"."creator_id" is
'创建人 user_id';

comment on column "oa_meal"."status" is
'1在办 2办结';

comment on column "oa_meal"."meal_place" is
'0明秀 1青湖';

comment on column "oa_meal"."meal_type" is
'0员工餐 1接待餐  2加班餐   3实习餐';

-- 系统日志加入 浏览器信息 字段 by yucs 2018-10-11
ALTER TABLE `sys_log`
ADD COLUMN `browser`  varchar(255) NULL AFTER `status`;
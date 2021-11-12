DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    REVISION bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    KEY_ VARCHAR(255)    COMMENT '键' ,
    LABEL VARCHAR(255)    COMMENT '说明' ,
    INTRO VARCHAR(255)    COMMENT 'INTRO' ,
    PRIMARY KEY (id)
)  COMMENT = '字典总表';

DROP TABLE IF EXISTS sys_dict_item;
CREATE TABLE sys_dict_item(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    REVISION bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    DICT_KEY VARCHAR(255)    COMMENT '关联字典总表的键' ,
    KEY_ VARCHAR(255)    COMMENT '键' ,
    LABEL VARCHAR(255)    COMMENT '说明' ,
    INTRO VARCHAR(255)    COMMENT 'INTRO' ,
    SORT_ VARCHAR(255)    COMMENT '排序' ,
    PRIMARY KEY (id)
)  COMMENT = '字典明细表';



/* 插入字典总表[apply_status-处置状态] */
INSERT INTO sys_dict(KEY_,LABEL,INTRO,REVISION) VALUES('apply_status','处置状态','',1);
/* 插入字典明细表 */
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('apply_status','0','未处置','','',1);
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('apply_status','1','已通过','','',1);
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('apply_status','2','已拒绝','','',1);

/* 插入字典总表[file_share_permission-文件共享权限] */
INSERT INTO sys_dict(KEY_,LABEL,INTRO,REVISION) VALUES('file_share_permission','文件共享权限','',1);
/* 插入字典明细表 */
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('file_share_permission','0','全部','','',1);
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('file_share_permission','1','查看','','',1);
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('file_share_permission','2','修改','','',1);
INSERT INTO sys_dict_item(DICT_KEY,KEY_,LABEL,SORT_,INTRO,REVISION) VALUES('file_share_permission','3','删除','','',1);


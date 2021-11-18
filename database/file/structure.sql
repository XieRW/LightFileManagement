DROP TABLE IF EXISTS file;
CREATE TABLE file(
    id bigint(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint(64)    COMMENT '乐观锁' ,
    create_user_id bigint(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    size INT    COMMENT '文件大小' ,
    filename VARCHAR(255)    COMMENT '文件实际名称' ,
    url VARCHAR(255)    COMMENT '文件URL' ,
    path VARCHAR(255)    COMMENT '文件物理存储相对路径' ,
    storename VARCHAR(255)    COMMENT '物理存储文件名称' ,
    extension VARCHAR(255)    COMMENT '文件扩展名' ,
    remark VARCHAR(255)    COMMENT '文件备注' ,
    directory VARCHAR(255)    COMMENT '文件分类目录' ,
    file_group_id bigint(64)    COMMENT '文件分组' ,
    PRIMARY KEY (id)
)  COMMENT = '文件表';


DROP TABLE IF EXISTS file_share_friend;
CREATE TABLE file_share_friend(
    id bigint(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    file_share_permission VARCHAR(255)    COMMENT '共享权限' ,
    share_from bigint    COMMENT '共享来源' ,
    share_to bigint    COMMENT '共享目标' ,
    file_id bigint    COMMENT '文件id' ,
    PRIMARY KEY (id)
)  COMMENT = '文件共享表';

DROP TABLE IF EXISTS file_group;
CREATE TABLE file_group(
    id bigint(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint(64)    COMMENT '乐观锁' ,
    create_user_id bigint(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    p_id bigint(64)   DEFAULT 0 COMMENT '父id' ,
    name VARCHAR(255)    COMMENT '分组名' ,
    detail VARCHAR(255)    COMMENT '分组说明' ,
    PRIMARY KEY (id)
)  COMMENT = '文件分组表';

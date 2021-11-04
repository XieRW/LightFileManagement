DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id INT(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted VARCHAR(255)    COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision INT(64)    COMMENT '乐观锁' ,
    create_user_id INT(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id INT(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    username VARCHAR(255)    COMMENT '用户登录名' ,
    name VARCHAR(255)    COMMENT '用户昵称' ,
    mobile VARCHAR(255)    COMMENT '手机号' ,
    email VARCHAR(255)    COMMENT '邮箱' ,
    password VARCHAR(255)    COMMENT '密码' ,
    salt VARCHAR(255)    COMMENT '盐' ,
    source_user_id VARCHAR(255)    COMMENT '第三方用户id' ,
    source_type VARCHAR(255)    COMMENT '用户来源' ,
    role_id INT(64)    COMMENT '角色id' ,
    PRIMARY KEY (id)
)  COMMENT = '角色表';

DROP TABLE IF EXISTS role;
CREATE TABLE role(
    id INT(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted VARCHAR(255)    COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision INT(64)    COMMENT '乐观锁' ,
    create_user_id INT(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id INT(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    name VARCHAR(255)    COMMENT '角色名称' ,
    remark VARCHAR(255)    COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '角色表';

DROP TABLE IF EXISTS user_friend;
CREATE TABLE user_friend(
    id INT(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted VARCHAR(255)    COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision INT(64)    COMMENT '乐观锁' ,
    create_user_id INT(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id INT(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    apply_from_id INT(64)    COMMENT '申请者' ,
    apply_to_id INT(64)    COMMENT '被申请者' ,
    apply_time DATETIME    COMMENT '申请时间' ,
    agree_time DATETIME    COMMENT '通过时间' ,
    PRIMARY KEY (id)
)  COMMENT = '好友表';

DROP TABLE IF EXISTS user_friend_application;
CREATE TABLE user_friend_application(
    id INT(64) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted VARCHAR(255)    COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision INT(64)    COMMENT '乐观锁' ,
    create_user_id INT(64)    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id INT(64)    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    apply_from_id INT(64)    COMMENT '申请者' ,
    apply_to_id INT(64)    COMMENT '被申请者' ,
    agree_time DATETIME    COMMENT '通过时间' ,
    dispose_time DATETIME    COMMENT '处置时间' ,
    apply_status VARCHAR(255)    COMMENT '申请状态，关联字典apply_status' ,
    PRIMARY KEY (id)
)  COMMENT = '好友申请表';

DROP TABLE IF EXISTS captcha;
CREATE TABLE captcha(
    uuid char(36) NOT NULL   COMMENT '主键' ,
    code VARCHAR(10)    COMMENT '验证码' ,
    expire_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间' ,
    PRIMARY KEY (uuid)
)  COMMENT = '系统验证码';


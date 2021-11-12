DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    username VARCHAR(255)    COMMENT '用户登录名' ,
    name VARCHAR(255)    COMMENT '用户昵称' ,
    mobile VARCHAR(255)    COMMENT '手机号' ,
    email VARCHAR(255)    COMMENT '邮箱' ,
    password VARCHAR(255)    COMMENT '密码' ,
    salt VARCHAR(255)    COMMENT '盐' ,
    source_user_id VARCHAR(255)    COMMENT '第三方用户id' ,
    source_type VARCHAR(255)    COMMENT '用户来源' ,
    role_id bigint    COMMENT '角色id' ,
    status INT(10)   DEFAULT 1 COMMENT '状态;0：禁用   1：正常  2：' ,
    PRIMARY KEY (id)
)  COMMENT = '用户表';


DROP TABLE IF EXISTS role;
CREATE TABLE role(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    name VARCHAR(255)    COMMENT '角色名称' ,
    remark VARCHAR(255)    COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '角色表';

DROP TABLE IF EXISTS user_friend;
CREATE TABLE user_friend(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    apply_from_id bigint    COMMENT '申请者' ,
    apply_to_id bigint    COMMENT '被申请者' ,
    apply_time DATETIME    COMMENT '申请时间' ,
    agree_time DATETIME    COMMENT '通过时间' ,
    PRIMARY KEY (id)
)  COMMENT = '好友表';

DROP TABLE IF EXISTS user_friend_application;
CREATE TABLE user_friend_application(
    id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id bigint    COMMENT '创建人' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    apply_from_id bigint    COMMENT '申请者' ,
    apply_to_id bigint    COMMENT '被申请者' ,
    apply_time DATETIME    COMMENT '申请时间' ,
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

DROP TABLE IF EXISTS mail_contactor;
CREATE TABLE mail_contactor(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT '联系人id' ,
    is_deleted INT(1)   DEFAULT 0 COMMENT '逻辑删除;0:未删除，1：已删除' ,
    revision bigint    COMMENT '乐观锁' ,
    create_user_id BIGINT(20)    COMMENT '创建者id' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_user_id bigint    COMMENT '更新人' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    name VARCHAR(100)    COMMENT '联系人名称' ,
    age TINYINT(4)    COMMENT '年龄' ,
    sex BIT(1)    COMMENT '性别' ,
    work_unit VARCHAR(100)    COMMENT '工作单位' ,
    position VARCHAR(100)    COMMENT '职务' ,
    is_importance BIT(1) NOT NULL  DEFAULT 0 COMMENT '是否重要联系人;0：否  1：是' ,
    office_tel VARCHAR(200)    COMMENT '办公室号码' ,
    mobile1 VARCHAR(200)    COMMENT '手机号码1' ,
    mobile2 VARCHAR(200)    COMMENT '手机号码2' ,
    home_tel VARCHAR(200)    COMMENT '家庭号码' ,
    fax VARCHAR(200)    COMMENT '传真号码' ,
    other_tel VARCHAR(200)    COMMENT '其他号码' ,
    is_commonly_used BIT(1)   DEFAULT 0 COMMENT '是否常用联系人;0：否  1：是' ,
    first_name VARCHAR(10)    COMMENT '拼音首字母' ,
    email VARCHAR(100)    COMMENT '邮箱地址' ,
    platform_id BIGINT(20)    COMMENT '终端用户id' ,
    remark VARCHAR(100)    COMMENT '备注' ,
    longitude DECIMAL(30,20)    COMMENT '经度' ,
    latitude DECIMAL(30,20)    COMMENT '维度' ,
    address VARCHAR(255)    COMMENT '地址' ,
    open_id VARCHAR(255)    COMMENT '微信关注公众号的标识' ,
    contactor_flag TINYINT(4)    COMMENT '用户标识;普通用户为1' ,
    sms_prompt_expired_time DATETIME    COMMENT '短信可再次提醒的时间' ,
    urgent_contactor_id BIGINT(20)    COMMENT '紧急联系人id' ,
    personnel_mark BIT(1)    COMMENT '负责人标识;0为否 : 1为是' ,
    images VARCHAR(255)    COMMENT '头像' ,
    order_num INT(11)    COMMENT '' ,
    default_phone_type INT(11)   DEFAULT 0 COMMENT '默认号码类型（0手机号码; 1办公号码 2家庭号码 3其他号码）' ,
    wx_user BIT(1)   DEFAULT 0 COMMENT '是否微信用户，1是; 0否' ,
    random_password VARCHAR(10)    COMMENT '' ,
    app_user BIT(1)   DEFAULT 0 COMMENT '是否APP用户，1是; 0否' ,
    app_status TINYINT(4)   DEFAULT 0 COMMENT 'APP状态，;0离线，1在线' ,
    pinyin VARCHAR(100)    COMMENT '' ,
    positioning_device_type INT(11)    COMMENT '关联定位类型' ,
    positioning_device_id BIGINT(20)    COMMENT '关联定位的设备id' ,
    source_contact_id VARCHAR(50)    COMMENT '' ,
    source_type VARCHAR(50)    COMMENT '' ,
    location_update_time DATETIME    COMMENT '位置更新时间' ,
    cgcs_longitude DOUBLE    COMMENT '国家2000坐标系经度' ,
    cgcs_latitude DOUBLE    COMMENT '国家2000坐标系纬度' ,
    is_phone_display BIT(1)   DEFAULT 0 COMMENT '是否隐藏显示号码;0或null: 显示号码，1: 隐藏号码' ,
    ring INT(11)   DEFAULT 0 COMMENT '来电语音;  0一般  1紧急' ,
    PRIMARY KEY (id)
)  COMMENT = '联系人表';

DROP TABLE IF EXISTS sys_user_token;
CREATE TABLE sys_user_token(
    user_id BIGINT NOT NULL   COMMENT '' ,
    token VARCHAR(100) NOT NULL   COMMENT 'token' ,
    expire_time DATETIME    COMMENT '过期时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    last_ip VARCHAR(255)    COMMENT '最后一次登录的ip' ,
    PRIMARY KEY (user_id)
)  COMMENT = '系统用户Token';

CREATE UNIQUE INDEX token ON sys_user_token(token);

INSERT INTO `light_file_management`.`user`(`id`, `is_deleted`, `revision`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `username`, `name`, `mobile`, `email`, `password`, `salt`, `source_user_id`, `source_type`, `role_id`, `status`) VALUES (1, '0', 0, 1, '2021-11-05 11:39:38', 1, '2021-11-05 11:39:38', 'xrw', 'xearin', '18651920000', '1429382877@qq.com', 'a22c9962194e0b356341c04eaa09e8f7741c89d1c0303e9808d65ff7be61759f', '6AHIT66OR3JI9Pnr', '1', '', 1, 1);

INSERT INTO `light_file_management`.`role`(`id`, `is_deleted`, `revision`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `name`, `remark`) VALUES (1, '0', NULL, 1, '2021-11-05 16:16:49', 1, '2021-11-05 16:16:56', 'admin', '管理员');
INSERT INTO `light_file_management`.`role`(`id`, `is_deleted`, `revision`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `name`, `remark`) VALUES (2, '0', NULL, 1, '2021-11-06 09:10:03', 1, '2021-11-06 09:10:03', 'common', '一般成员');

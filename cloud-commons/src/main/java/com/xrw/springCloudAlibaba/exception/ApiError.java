package com.xrw.springCloudAlibaba.exception;

/**
 * @author xearin
 */
public enum ApiError {
    //============通用：权限相关=============
    PASSWORD_INCONSISTENCIES(10000, "密码不一致"),
    LOGIN_ERROR(10001, "用户名不存在或密码错误"),
    NOT_LOGIN(10002, "未登录，禁止访问"),
    SIGN_ERROR(10003, "签名错误"),
    LOGIN_EXPIRED(10004, "登录失效"),
    LOGIN_AUTHENTICATION_ERROR(10005, "登陆信息认证失败"),
    INTERFACE_UNPREMITTED(10006, "无访问权限"),
    CODE_ERROR(10007, "验证码错误"),
    NoRELEVANTDATA(10008, "暂无该部门相关数据"),
    NoCert(10009, "您的证书无效，请访问 /license/getServerInfos 获得当前服务器信息，然后重新申请证书！"),
    NOT_ADMIN_ERROR(10010, "非管理员账号无权限访问"),
    GENERATE_TOKEN_ERROR(10013, "生成Token失败"),
    OPERATION_UNPREMITTED(10014, "无操作权限"),
    USER_NOT_FIND(10015, "获取当前用户失败"),

    //============通用：分页相关=============
    PAGE_ERROR(11001, "分页信息错误，必须同时为空或同时不为空"),

    //============通用：数据相关=============
    DATA_FETCH_ERROR(12002, "数据获取失败"),
    DATA_SYNC_GARBLED_ERROR(12003, "数据库语句执行失败"),
    DATA_NOT_EXIST(12004, "数据已删除"),
    DATA_EVENT_NOT_EXIST(12014, "事件id错误或事件不存在"),
    DATA_FORMAT_ERROR(12005, "数据格式错误"),
    RELATION_FORMAT_ERROR(12006, "响应等级判断条件格式错误"),
    DATA_SAVE_ERROR(12007, "数据保存失败"),
    DATA_TOO_LONG(12008, "数据长度过长"),
    DATA_INCORRECT_STRING(12009, "无法识别字符"),
    DATA_EXISTS(12010, "数据已存在，无法插入重复数据"),
    DATA_GROUP_EXISTS(12011, "同一上级分组已存在相同名称分组，请重新编辑"),
    DATA_NUMBER_EXISTS(12012, "号码已存在"),
    DATA_NOT_EXISTS(12013, "数据不存在"),
    DATA_NOT_SEND(12014, "请选择一条已发信息"),
    DATA_NAME_REPEAT(12015, "名称重复"),
    DATA_DEVICE_CODE_NOT_EXISTS(12016, "设备编码不存在"),
    DATA_ORDER_OBJECT_NOT_EXIST(12017, "排序对象已删除，请刷新后重试"),
    NUMBER_FORMAT_EXCEPTION(12018, "参数格式错误"),
    NULL_POINT_EXCEPTION(12019, "暂无数据"),
    DATA_DELETE_ERROR(12020, "数据删除失败"),

    //============通用：数据修改相关=============
    DATA_GROUP_NOT_EMPTY(13001, "无法删除包含子节点或数据的分组"),
    DATA_INVALID(13002, "数据校验失败"),
    DATA_MOBILE_INVALID(13002, "手机号码格式错误"),
    DATA_CONTACTOR_PREPLANE_EXISTS(13003, "删除失败,联系人已存在预案中"),
    DATA_NOT_ALLOW_DELETE(13004, "删除失败,该分组数据不允许删除"),
    DATA_GROUP_NO_EDIT(13001, "无法修改此分组"),
    DATA_INFO(13001, "分组信息无法删除"),
    DATA_GROUP_NAME_REPEAT(13001, "分组名称重复"),
    DATA_MUST_HAVE_CONTACT(13005, "操作失败,至少要存在一个联系方式"),
    DATA_GROUP_NAME_EXIST(13001, "所选的单位已经添加了"),

    //============通用：导入相关=============
    IMPORT_FILE_NOT_FOUND(14010, "文件不存在"),
    IMPORT_ERROR(14011, "导入数据格式错误"),
    IMPORT_FILE_TYPE_ERROR(14012, "导入文件格式错误"),
    IMPORT_NO_GROUP_TYPE(14013, "分组类型错误"),
    FILE_TYPE_ERROR(14014, "文件格式错误"),
    FILE_UPLOAD_ERROR(14015, "文件上传失败"),
    FILE_UPLOAD_BLANK(14015, "文件内容为空"),
    FILE_TOO_LARGE(14016, "文件过大"),
    FILE_DEAL_FAILED(14017, "处理文件失败"),

    //============通用：字典相关=============
    DICT_NOT_EXISTS(15000, "字典不存在"),

    //============通用：MQ相关=============
    MQ_SEND_ERROR(16000,"MQ消息发送失败"),

    //============通用：接口请求相关=============
    PARAMETER_NULL_ERROR(17000, "参数不存在或者为空"),
    FEIGN_CLOUD_MQ_ERROR(17001,"远程调用cloudMq服务失败");

    public Integer errorCode;
    public String msg;

    ApiError(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}

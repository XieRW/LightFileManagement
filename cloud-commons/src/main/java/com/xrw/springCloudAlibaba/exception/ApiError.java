package com.xrw.springCloudAlibaba.exception;

/**
 * @author unknown
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
    //============通用：分页相关=============
    PAGE_ERROR(21001, "分页信息错误，必须同时为空或同时不为空"),

    //============通用：数据相关=============
    DATA_FETCH_ERROR(20002, "数据获取失败"),

    DATA_SYNC_GARBLED_ERROR(20003, "数据库语句执行失败"),
    DATA_NOT_EXIST(20004, "数据已删除"),
    DATA_EVENT_NOT_EXIST(20014, "事件id错误或事件不存在"),
    DATA_FORMAT_ERROR(20005, "数据格式错误"),
    RELATION_FORMAT_ERROR(20006, "响应等级判断条件格式错误"),
    DATA_SAVE_ERROR(20007, "数据保存失败"),
    DATA_TOO_LONG(20008, "数据长度过长"),
    DATA_INCORRECT_STRING(20009, "无法识别字符"),
    DATA_EXISTS(20010, "数据已存在，无法插入重复数据"),
    DATA_GROUP_EXISTS(20011, "同一上级分组已存在相同名称分组，请重新编辑"),
    DATA_NUMBER_EXISTS(20012, "号码已存在"),
    DATA_NOT_EXISTS(20013, "数据不存在"),
    DATA_NOT_SEND(20014, "请选择一条已发信息"),
    DATA_NAME_REPEAT(20015, "名称重复"),
    DATA_DEVICE_CODE_NOT_EXISTS(20016, "设备编码不存在"),
    DATA_ORDER_OBJECT_NOT_EXIST(20017, "排序对象已删除，请刷新后重试"),

    //============通用：数据修改相关=============
    DATA_GROUP_NOT_EMPTY(21001, "无法删除包含子节点或数据的分组"),
    DATA_INVALID(21002, "数据校验失败"),
    DATA_MOBILE_INVALID(21002, "手机号码格式错误"),
    DATA_CONTACTOR_PREPLANE_EXISTS(21003, "删除失败,联系人已存在预案中"),
    DATA_NOT_ALLOW_DELETE(21004, "删除失败,该分组数据不允许删除"),
    DATA_GROUP_NO_EDIT(21001, "无法修改此分组"),
    DATA_INFO(21001, "分组信息无法删除"),
    DATA_GROUP_NAME_REPEAT(21001, "分组名称重复"),
    DATA_MUST_HAVE_CONTACT(21005, "操作失败,至少要存在一个联系方式"),
    DATA_GROUP_NAME_EXIST(21001, "所选的单位已经添加了"),

    //============通用：导入相关=============
    IMPORT_FILE_NOT_FOUND(20010, "文件不存在"),
    IMPORT_ERROR(20011, "导入数据格式错误"),
    IMPORT_FILE_TYPE_ERROR(20012, "导入文件格式错误"),
    IMPORT_NO_GROUP_TYPE(20013, "分组类型错误"),
    FILE_TYPE_ERROR(20014, "文件格式错误"),
    FILE_UPLOAD_ERROR(20015, "文件上传失败"),
    FILE_UPLOAD_BLANK(20015, "文件内容为空"),
    FILE_TOO_LARGE(20016, "文件过大"),
    FILE_DEAL_FAILED(20017, "处理文件失败"),

    //============h5小程序：任务相关=============
    DISTRIBUTION_NOT_EXIST(21000, "任务不存在"),
    DISTRIBUTION_ALREADY_CHECKED(21001, "任务已被确认"),
    DISTRIBUTION_ALREADY_ARRIVED(21002, "任务正在处理中"),
    DISTRIBUTION_ALREADY_FINISHED(21003, "任务已完结"),

    //============指挥调度：任务相关=========
    SCHEME_TASK_NULL_CONTENT(30000, "任务内容为空"),
    SCHEME_TASK_NONE_CONTACTOR(30001, "任务无对应联系人"),
    SCHEME_TASK_NONE_PROCESS(30002, "任务未选择流程或流程已删除"),
    SCHEME_TASK_LOW_STOCKS(30003, "库存不足"),
    SCHEME_TASK_NO_DISPATCH_NUM(30004, "未填写调度数量"),

    //============app：事件相关=========
    APP_EVENT_HAS_BEEN_SENT(40001, "事件已下发"),

    //============ 语音调度 ============
    VOICE_CONTROL_ERROR_TYPE(50001, "无法识别搜索类型"),
    VOICE_CONTROL_NO_EXPERT_TYPE(50002, "找不到该专家类型"),

    //============任务============
    TASK_HAS_FINISHED(1000001, "任务已经完成，无法继续操作"),
    TASK_HAS_CANCELED(1000002, "任务已经取消，无法继续操作"),
    TASK_HAS_NOT_TYPE(1000003, "保存失败，请选择任务类型"),

    //============专项防范：报告单位相关-报告单位-扬州============
    SPECIAL_PRECAUTIONS_UNIT_HAVE(1000006, "报告单位已存在"),
    SPECIAL_PRECAUTIONS_UNIT_HAVE_CHILDREN(1000007, "当前单位包含下级单位不能删除"),

    //============专项防范：责任单位相关============
    SPECIAL_PRECAUTIONS_DEPT_NULL(2000001, "你不是责任单位人员"),
    SPECIAL_PRECAUTIONS_DEPT_STATUS(2000002, "当前用户不是最高权限管理员账号"),
    SPECIAL_PRECAUTIONS_DEPT_SAVE_ERROR(2000003, "新增失败"),
    SPECIAL_PRECAUTIONS_DEPT_UPDATE_ERROR(2000004, "修改失败"),
    SPECIAL_PRECAUTIONS_DEPT_DELETE_ERROR(2000005, "删除失败"),
    SPECIAL_PRECAUTIONS_DEPT_HAVE(2000006, "责任单位已存在"),

    //============专项防范：用户相关============
    SPECIAL_PRECAUTIONS_USER_NO_LOGIN(3000001, "登录失效，请重新登录"),
    SPECIAL_PRECAUTIONS_USER_NO_HAVE(3000002, "登录名已经存在"),
    SPECIAL_PRECAUTIONS_USER_NO_PRIVILEGE(3000003, "没有权限进行操作"),
    SPECIAL_PRECAUTIONS_USER_STATUS_HAVE(3000004, "当前区划已经存在管理员账号"),
    SPECIAL_PRECAUTIONS_USER_DELETE_CURRENT_ID_ERROR(3000005, "不能删除自己"),
    SPECIAL_PRECAUTIONS_USER_NO_SECURITY(3000006, "没有权限访问当前数据"),

    //============专项防范：任务配置============
    SPECIAL_PRECAUTIONS_SET_TASK_DEF_ERROR(4000001, "已经存在默认值"),
    SPECIAL_PRECAUTIONS_SET_TASK_SET_DEF(4000001, "请设置默认值"),
    SPECIAL_PRECAUTIONS_DELETE_TASKS(5000001, "不能删除已经下发的任务"),
    SPECIAL_PRECAUTIONS_REPORT_TASKS(60001, "最高负责人不能上报"),

    //============专项防范：基本信息============
    SPECIAL_PRECAUTIONS_CATEGORY_DEF_ERROR(7000001, "已经存在默认值"),
    SPECIAL_PRECAUTIONS_CATEGORY_SET_DEF(7000002, "请设置默认值"),
    SPECIAL_PRECAUTIONS_CATEGORY_ADD_ERROR(7000003, "新增失败"),
    SPECIAL_PRECAUTIONS_CATEGORY_UPDATE_ERROR(7000004, "修改失败"),

    //============专项防范：任务创建============
    SPECIAL_PRECAUTIONS_TASK_CREATE_ERROR(8000001, "该区域没有负责人不能创建督察任务!"),
    SPECIAL_PRECAUTIONS_TASK_SET_ERROR(8000002, "当前区域或单位预案内容为空!"),
    SPECIAL_PRECAUTIONS_TASK_UPDATE(8000003, "当前任务已经下发，不能修改！"),
    SPECIAL_PRECAUTIONS_TASK_DELETE(8000004, "当前任务已经下发，不能删除！"),

    //============专项防范：专项行动============
    SPECIAL_PRECAUTIONS_ACTION_DELETE(9000001, "当前专项行动还有任务，不能删除！"),
    SPECIAL_PRECAUTIONS_ACTION_UPDATE(9000002, "当前专项行动还有任务，不能修改！"),
    SPECIAL_PRECAUTIONS_PATROL_UPDATE(9000003, "当前巡查对象关联了任务，不能修改！"),
    SPECIAL_PRECAUTIONS_RISK_UPDATE(9000004, "当前巡查对象关联了任务，不能修改！"),
    SPECIAL_PRECAUTIONS_TASK_END(9000005, "当前任务已完结，不能继续操作！"),
    SPECIAL_PRECAUTIONS_ACTION_END(9000006, "当前专项行动已完结，不能继续操作！"),

    //============三级联动============
    DATA_SYNC_ERROR(900001, "三级联动接口调用失败！"),
    DATA_SYNC_UPDATEERROR(900002, "地图联动共享标绘无法删除修改！"),
    DATA_SYNC_TARGETERROR(900003, "联动目标配置错误！"),
    //===========启动预案===========
    Paln_Start(9180000, "地图未开启"),
    PLAN_LEVEL_MUST_NOT_EMPTY(9180001, "请选择响应等级"),

    //=============数据请求格式=================
    //=================系统设置返回结果===================
    RTURN_ERROR(1000001, "修改密码失败！"),
    RTURN_ERROR_PASSWORD(1000003, "密码错误！"),
    RTURN_SAME_PASSWORD(1000008, "新密码与旧密码相同！"),
    RTURN_PASSWORD_EQUALS_USERNAME(1000009, "新密码与用户名相同！"),
    RTURN_ERROR_RESET(1000004, "重置密码失败！"),
    DELETE_ERROR(1000002, "当前事件类型包含下级不能删除！"),
    DELETE_ERROR_PLAN(1000005, "类型绑定了预案 不能删除！"),
    SELECT_ERROR_EVENT_CLASS(1000006, "事件等级名称已存在"),
    CASE_CLASS_EXIST(1000007, "事件类型名称已存在"),
    UNRECOGNIZED_CONFIG_CODE(1000008, "无法识别的配置项编码"),

    // ================文件相关错误===================
    FAX_NOT_EXIST(500, "传真文件不存在"),
    FAX_TRANSFORM_ERROR(500, "传真预览出错"),
    FAX_FILE_CONVERT_ERROR(200011, "传真文件转换错误"),
    FAX_FILE_MERGE_ERROR(200011, "传真文件合并错误"),
    FAX_RECEIVER_EMPTY_ERROR(200012, "传真接收者不能为空"),

    PHONE_RECORD_NOT_EXIST(500, "电话录音文件不存在"),
    PHONE_RECORD_PATH_NOT_EXIST(500, "电话录音文件地址不在配置表中，请检查配置文件"),
    PHONE_RECORD_GETLONG_ERROR(500, "获取录完时长失败"),

    // ================错误信息======================
    NUMBER_FORMAT_EXCEPTION(-1, "参数格式错误"),
    NULL_POINT_EXCEPTION(0, "暂无数据"),
    COMMIT_REPETITION(-1, "请勿重复提交"),
    SYSTEM_ERROR(-1, "系统异常"),
    SYSTEM_BUSY_ERROR(-1, "系统繁忙，请稍后再试"),
    DICTIONARY_NOT_EXIST(500, "字典表错误"),
    MISSING_NECESSARY_PARAM(-1, "缺少必要参数"),
    MISSING_PRIMARY_ID(-1, "缺少主键id"),

    SAVE_PREPLAN_ERROR(500, "请先保存事件"),
    EVENT_TIME_ERROR(500, "续报时间需要晚于事发时间且早于当前时间"),
    EVENT_DEAL_CONTENT_ERROR(500, "通信单位或短信内容不能为空"),
    EVENT_DEAL_EVENT_ERROR(500, "事件关联不能为空"),
    EVENT_NO_UNRESPONSE(500, "不存在未响应人员"),

    // ================三级联动======================
    LINKAGE_SEND_ERROR(300001, "三级联动发送数据失败"),


    //=========================政务管理========================
    AFFAIRS_OCCUPY_ERROR(500, "该类型已被使用,无法删除!"),
    AFFAIRS_UPDATE_ERROR(500, "该任务已下发,无法修改!"),
    AFFAIRS_DELETED_ERROR(500, "该任务已下发,无法删除!"),
    AFFAIRS_NAME_REPEAT_ERROR(500, "该名称已存在"),


    //=========================石景山值班排班========================
    SJS_DUTY_DATE_ERROR(500, "所选日期小于当前月!"),

    //===========平安统一登录的异常==========
    OAUTH2_PINGAN_PARAM_ERROR(2020032, "必传参数不能为空，请重新从统一平台登录"),
    OAUTH2_PINGAN_USER_UN_MAPPER(2100001, "首次登录请手动登录以映射"),
    OAUTH2_PINGAN_GET_TOKEN_FAILED(2100002, "获取token失败，请重新从统一平台登录"),
    OAUTH2_PINGAN_GET_CODE_FAILED(2100003, "获取授权码失败，请重新从统一平台登录"),
    OAUTH2_PINGAN_CODE_EXPIRATION(2100004, "授权码已经过期，请重新从统一平台登录"),
    OAUTH2_PINGAN_TOKEN_EXPIRATION(2100005, "token已经过期，请重新从统一平台登录"),
    OAUTH2_PINGAN_CONNECT_FAILED(2200004, "连接失败，请重新从统一平台登录"),

    SPECIAL_PRECAUTIONS_AREA_HAVE(1000006, "行政区域已存在"),
    SPECIAL_PRECAUTIONS_AREA_HAVE_CHILDREN(1000007, "当前区域包含下级区域不能删除"),

    //=============================接口调用异常=========================
    API_CALL_ERROR(500, "远程接口调用失败"),


    //=============================值班异常=========================
    DUTY_TOO_MANY_PEOPLE(500, "值班人数超出班次最大人数！");

    public Integer errorCode;
    public String msg;

    ApiError(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}

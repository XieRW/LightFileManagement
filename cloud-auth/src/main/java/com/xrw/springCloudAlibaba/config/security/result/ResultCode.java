package com.xrw.springCloudAlibaba.config.security.result;

/**
 * 返回码。<br/>
 * 使用规则: <br/>
 * 通用200成功码，500为失败码。<br>
 * 根据模块划分开头一位数字。<br>
 * 根据子模块划分开头第二位数字。<br>
 *   用户模块(登陆模块)：40xx <br>
 *
 * @author xearin
 * @Date 2019-11-15 10:00
 **/
public class ResultCode {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 服务器运行时异常
     */
    public static final int FAIL = 500;

    /**
     * 40XX 用户认证授权类
     */
    /** 没有登陆 */
    public static final int UNAUTHORIZED = 4001;
    /** 登录失败 */
    public static final int LOGIN_FAIL = 4002;
    /** 注册失败 */
    public static final int REGISTER_FAIL = 4003;
    /** TOKEN已过期或无效 */
    public static final int INVALID_TOKEN = 4004;
    /** 无效的微信认证授权参数 */
    public static final int INVALID_WX_PARAM = 4005;

/**
 * 5XXX 日常管理模块
 */
    /**
     * 50xx 日常值班
     */
    /** 添加班次过多 */
    public static final int DUTY_ENOUGH_DUTY_SCHEDULE_COUNT = 5001;
    /** 请求的参数不足 */
    public static final int DUTY_NO_ENOUGH_PARAMS = 5002;
    /** 非法操作：值班员选择过多（举例） */
    public static final int DUTY_INVALID_OPERATION = 5003;
    /** 图片过大 */
    public static final int DUTY_OVER_SIZE_IMAGE = 5004;
    /** 请上传正确的文件格式 */
    public static final int DUTY_UPLOAD_INVALID_FILE = 5005;
    /** 请上传正确的文件格式 */
    public static final int DUTY_UPLOAD_FILE_NULL = 5006;

    /**
     * 51XX 融合通信
     */


    /**
     * 52XX 值班排班
     */

    /** 班次可操作的日期已结束 **/
    public static final int DUTY_EXPIRED_TIME = 5201;



/**
 * 6XXX 指挥调度
 */

}

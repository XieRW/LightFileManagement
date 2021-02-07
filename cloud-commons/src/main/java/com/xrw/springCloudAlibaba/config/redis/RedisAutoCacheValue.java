package com.xrw.springCloudAlibaba.config.redis;

/**
 * @program: LightFileManagement
 * @description: 自动缓存的缓存空间或者说类型
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-14 22:11
 **/
public class RedisAutoCacheValue {

    /**
     * 自动生成key前缀
     */
    public static final String AUTO_KEY_PREFIX = "AUTO:";

    /**
     * 配置相关
     */
    public static final String CONFIG = "CONFIG";

    /**
     * demo或者测试
     */
    public static final String DEMO = "DEMO";

    /**
     * 联系人数据的缓存
     */
    public static final String CONTACTOR = "contactor";
    public static final String COMMONCONTACTOR = "commonContactor";

    /**
     * 存放用户信息的缓存
     */
    public static final String USER = "USER";

    /**
     * 值班排班
     */
    public static final String DUTY_SCHEDULE = "DUTY_SCHEDULE";


    /**
     * 值班日志
     */
    public static final String DUTY_LOG_OPERATIONS = "DUTY_LOG_OPERATIONS";
    public static final String DUTY_LOG_OPERATIONS_LIST = "DUTY_LOG_OPERATIONS_LIST";
    public static final String DUTY_LOG_ITEM = "DUTY_LOG_ITEM";
    public static final String DUTY_LOG_ITEM_LIST = "DUTY_LOG_ITEM_LIST";
    /**
     * 要勤处置过程
     */
    public static final String EMERGENCY_HANDLE_PROCESS = "EMERGENCY_HANDLE_PROCESS";
    /**
     * 摄像头
     */
    public static final String RISK_CAMERA = "RISK_CAMERA";
    /**
     * 人员预警
     */
    public static final String RISK_HUMAN_ALARM = "RISK_HUMAN_ALARM";
    /**
     * 车辆预警
     */
    public static final String RISK_VEHICLE_ALARM = "RISK_VEHICLE_ALARM";

    /**
     * 可视化-监测预警
     */
    public static final String JCYJ_HUMAN_HOT = "JCYJ_HUMAN_HOT";
    public static final String JCYJ_HUMAN_HOT_KEY = "JCYJ_HUMAN_HOT_KEY";
    public static final String JCYJ_HUMAN_HOT_ALL = "JCYJ_HUMAN_HOT_ALL";
    public static final String JCYJ_HUMAN_HOT_ALL_KEY = "JCYJ_HUMAN_HOT_ALL_KEY";
    public static final String JCYJ_VEHICLE_HOT = "JCYJ_VEHICLE_HOT";
    public static final String JCYJ_VEHICLE_HOT_ALL = "JCYJ_VEHICLE_HOT_ALL";
    //车辆占比
    public static final String JCYJ_VEHICLE_ALARM_SCALE = "JCYJ_VEHICLE_ALARM_SCALE";
    //人员占比
    public static final String JCYJ_HUMAN_ALARM_SCALE = "JCYJ_HUMAN_ALARM_SCALE";

}

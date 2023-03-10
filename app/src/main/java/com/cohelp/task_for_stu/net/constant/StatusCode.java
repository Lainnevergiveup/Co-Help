package com.cohelp.task_for_stu.net.constant;

/**
 * @author jianping5
 * @create 2022/10/10 20:47
 */
public class StatusCode {

    public static final String SUCCESS_REGISTER = "200";
    public static final String ERROR_REGISTER = "400";

    public static final String SUCCESS_LOGIN = "201";
    public static final String ERROR_LOGIN = "401";

    public static final String SUCCESS_GET_DATA = "202";
    public static final String ERROR_GET_DATA = "402";

    public static final String SUCCESS_REQUEST = "203";
    public static final String ERROR_REQUEST = "403";

    public static final String SUCCESS_LOGOUT = "204";

    public static final String SUCCESS_CHANGE_USER_INFO = "205";
    public static final String ERROR_CHANGE_USER_INFO = "405";

    public static final String ERROR_USER_EXIST = "406";

    public static final String ERROR_SAVE_HELP = "407";

    public static final String ERROR_SAVE_IMAGE = "408";

    public static final String ERROR_SAVE_HOLE = "409";

    public static final String ERROR_SAVE_ACTIVITY = "410";

    /**
     * 参数错误（例如：为空）
     */
    public static final String ERROR_PARAMS = "414";

    /**
     * 系统错误
     */
    public static final String ERROR_SYSTEM = "500";

    /**
     * 登录拦截器（请求被拦截-> 返回状态码 -> 跳转登录页面）
     * 清空本地cookie
     */
    public static final String INTERCEPTOR_LOGIN = "444";


}

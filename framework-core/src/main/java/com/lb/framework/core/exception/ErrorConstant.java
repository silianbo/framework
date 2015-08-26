package com.lb.framework.core.exception;

/**
 * 
 * 1~999 系统通用错误代码, 1000 以上业务系统自定义
 * 
 * @author platform
 *
 */
public class ErrorConstant {

    /**
     * INPUT VALUE IS EMPTY
     */
    public static final int EMPTY = 100;

    /**
     * ID NOT ALLOWED NULL
     */
    public static final int ID_NOT_ALLOWED_NULL = 101;

    /**
     * INPUT PARAM NOT MATCH
     */
    public static final int NO_MATCH = 200;

    /**
     * AREA_NOT_MATCH
     */
    public static final int AREA_NOT_MATCH = 201;

    /**
     * DATA_NOT_MATCH
     */
    public static final int DATA_NOT_MATCH = 202;

    /**
     * USER_NOT_MATCH
     */
    public static final int USER_NOT_MATCH = 203;

    /**
     * CAN_NOT_FIND_OBJECT
     */
    public static final int CAN_NOT_FIND_OBJECT = 300;

    /**
     * NOT_SUPPORT
     */
    public static final int NOT_SUPPORT = 500;

    /**
     * INPUT_TYPE_NOT_SUPPORT
     */
    public static final int INPUT_TYPE_NOT_SUPPORT = 501;

    /**
     * CALL_URL_NOT_SUPPORT
     */
    public static final int CALL_URL_NOT_SUPPORT = 502;

    /**
     * SERVICE_ERROR
     */
    public static final int SERVICE_ERROR = 600;

    /**
     * USER_CHECKED_ERROR
     */
    public static final int USER_CHECKED_ERROR = 601;

    /**
     * SERVICE_EXCEPTION
     */
    public static final int SERVICE_EXCEPTION = 602;

    /**
     * SERVICE_EXEC_FAIL
     */
    public static final int SERVICE_EXEC_FAIL = 603;

    /**
     * SERVICE_BUSY
     */
    public static final int SERVICE_BUSY = 900;

    /**
     * SERVICE_MAINTAIN
     */
    public static final int SERVICE_MAINTAIN = 901;
}

package com.moheqionglin.globalexception;

/**
 * @ClassName : GlobalBizException
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-23 17:18
 */
public class GlobalBizException extends Exception {
    private int code;

    public GlobalBizException(int retCode) {
        this(retCode, (String)null, (Throwable)null);
    }

    public GlobalBizException(int code, String message) {
        this(code, message, (Throwable)null);
    }

    public GlobalBizException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

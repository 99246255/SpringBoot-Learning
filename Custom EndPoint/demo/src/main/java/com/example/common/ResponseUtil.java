package com.example.common;

public final class ResponseUtil {
    private static final int FAULT_CODE = 500;
    private static final int SUCCESS_CODE = 200;
    private static final int N0_PERMS_CODE = 403;
    private static final String SUCCESS_MSG = "OK";
    private static final String FAULT_MSG = "服务器内部错误";
    private static final String UNKNOWN_MSG = "服务器内部未知错误";
    private static final String N0_PERMS = "没有相关操作权限";

    public ResponseUtil() {
    }

    public static <T> Result<T> getBusinessResult(BusinessException ex) {
        return new Result(Integer.parseInt(ex.getXCode()), ex.getMessageWithoutCode());
    }

    public static <T> Result<T> getErrorResult(String msg) {
        return new Result(500, msg);
    }

    public static <T> Result<T> getFaultResult() {
        return new Result(500, "服务器内部错误");
    }

    public static <T> Result<T> getSuccessResult(T obj) {
        return new Result(0, "OK", obj);
    }

    public static <T> Result<T> getUnknownResult() {
        return new Result(500, "服务器内部未知错误");
    }

    public static <T> Result<T> getNotPermsResult() {
        return new Result(403, "没有相关操作权限");
    }
}

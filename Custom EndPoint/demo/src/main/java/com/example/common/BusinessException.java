package com.example.common;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_FAULT_CODE = "-1";
    private String xCode;
    private String message;

    public BusinessException(String message) {
        this(DEFAULT_FAULT_CODE, message);
    }

    public BusinessException(String xCode, String message) {
        this(xCode, message, (Throwable)null);
    }

    public BusinessException(String xCode, String message, Throwable throwable) {
        super("[" + xCode + "] - " + message, throwable);
        this.message = message;
        this.xCode = xCode;
    }

    public String getXCode() {
        return this.xCode;
    }

    public void setXCode(String xCode) {
        this.xCode = xCode;
    }

    public String getMessageWithoutCode() {
        return this.message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

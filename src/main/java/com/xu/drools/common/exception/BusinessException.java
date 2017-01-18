
package com.xu.drools.common.exception;


import com.xu.drools.common.bean.ExceptionType;

/**
 * 业务异常
 *
 * @author xuhua
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private int code;

    private String message;

    private int level;

    public BusinessException(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.level = exceptionType.getLevel();
    }

    public BusinessException(int code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }

}

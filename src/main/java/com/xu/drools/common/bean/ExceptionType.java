package com.xu.drools.common.bean;

/**
 * Enum 响应类型
 *
 * @date 16/08/01
 * @auther hua xu
 */
public enum ExceptionType {
    SUCCESS(200, "success",2),
    SYSTEM_ERROR(300001, "系统错误", 5),
    RULE_IS_NULL(300002, "系统错误", 2),
    RULE_IS_ERROR(300003, "规则语句错误", 2);

    private int code;
    private String message;
    private int level;

    ExceptionType(int code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }
}



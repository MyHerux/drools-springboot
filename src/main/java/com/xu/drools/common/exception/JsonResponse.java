package com.xu.drools.common.exception;



/**
 * 业务异常类json
 *
 * @author xuhua
 * @since 1.0.0
 */
public class JsonResponse {
    private int code;
    private int level;
    private String message;
    private Object data;

    public JsonResponse(Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            this.code = businessException.getCode();
            this.message = businessException.getMessage();
            this.level = businessException.getLevel();
        } else {
            this.code = 500;
            this.message = "传入数据错误";
            this.level = 5;
        }
    }

    public JsonResponse(Object data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

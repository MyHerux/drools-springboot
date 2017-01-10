package com.xu.drools.bean;

import java.io.Serializable;

public class Message implements Serializable,BaseBean{

    private static final long serialVersionUID = 1L;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getOut() {
        return status;
    }
}
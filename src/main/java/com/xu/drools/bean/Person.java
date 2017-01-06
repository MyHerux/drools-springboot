package com.xu.drools.bean;


import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer age;
    private String desc;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

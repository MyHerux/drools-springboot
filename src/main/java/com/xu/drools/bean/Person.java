package com.xu.drools.bean;


import java.io.Serializable;

public class Person implements Serializable,BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
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

    @Override
    public String getOut() {
        return "{\"age\"="+age+",\"desc\"="+desc+"}";
    }
}

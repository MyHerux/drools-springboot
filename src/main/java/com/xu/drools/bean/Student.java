package com.xu.drools.bean;

/**
 * Created by xu on 2017/6/5.
 */
public class Student {
    private String name;
    private String word1;
    private String word2;
    private Integer desc1;
    private Integer desc2;

    public Student(String name, String word1, String word2, Integer desc1, Integer desc2) {
        this.name = name;
        this.word1 = word1;
        this.word2 = word2;
        this.desc1 = desc1;
        this.desc2 = desc2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public Integer getDesc1() {
        return desc1;
    }

    public void setDesc1(Integer desc1) {
        this.desc1 = desc1;
    }

    public Integer getDesc2() {
        return desc2;
    }

    public void setDesc2(Integer desc2) {
        this.desc2 = desc2;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", word1='" + word1 + '\'' +
                ", word2='" + word2 + '\'' +
                ", desc1=" + desc1 +
                ", desc2=" + desc2 +
                '}';
    }
}

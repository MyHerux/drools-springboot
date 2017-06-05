package com.xu.drools.bean;

/**
 * Created by xu on 2017/6/5.
 */
public class Student {
    private String name;
    private String word1;
    private String word2;

    public Student(String name, String word1, String word2) {
        this.name = name;
        this.word1 = word1;
        this.word2 = word2;
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
}

package com.xu.drools.bean;

/**
 * Created by xu on 2017/6/6.
 */
public class World {
    private Integer a;
    private Integer b;
    private Integer c;

    public World(Integer a, Integer b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "World{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}

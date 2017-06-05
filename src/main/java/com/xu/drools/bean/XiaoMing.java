package com.xu.drools.bean;

/**
 * Created by xu on 2017/6/5.
 * 小明喝水问题中的小明
 */
public class XiaoMing {

    //总共的钱
    private int money;
    //空瓶子数目
    private int bottle;
    //已经喝掉的汽水
    private int drink;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBottle() {
        return bottle;
    }

    public void setBottle(int bottle) {
        this.bottle = bottle;
    }

    public int getDrink() {
        return drink;
    }

    public void setDrink(int drink) {
        this.drink = drink;
    }
}

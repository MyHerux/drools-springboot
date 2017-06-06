package com.xu.drools.bean;

public class Golfer{

	private String name;
	private String color;
	private int position;

	public Golfer(String name, String color, int position) {
		this.name = name;
		this.color = color;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
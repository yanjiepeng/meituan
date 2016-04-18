package com.example.bean;

import java.util.Arrays;

public class Food {

	int f_id;
	int belong_id;
	String f_name;
	int f_price;
	byte[] f_img;
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public int getBelong_id() {
		return belong_id;
	}
	public void setBelong_id(int belong_id) {
		this.belong_id = belong_id;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public int getF_price() {
		return f_price;
	}
	public void setF_price(int f_price) {
		this.f_price = f_price;
	}
	public byte[] getF_img() {
		return f_img;
	}
	public void setF_img(byte[] f_img) {
		this.f_img = f_img;
	}
	@Override
	public String toString() {
		return "Food [f_id=" + f_id + ", belong_id=" + belong_id + ", f_name="
				+ f_name + ", f_price=" + f_price + ", f_img="
				+ Arrays.toString(f_img) + "]";
	}
	
	
	
	
}

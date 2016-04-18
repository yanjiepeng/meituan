package com.example.application;

import android.app.Application;

public class MyApplication extends Application {

	private int u_id = 0;
	private String name = null;
	private String address = null;
	private String et_phone = null;
	private String et_password = null;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getEt_phone() {
		return et_phone;
	}

	public void setEt_phone(String et_phone) {
		this.et_phone = et_phone;
	}

	public String getEt_password() {
		return et_password;
	}

	public void setEt_password(String et_password) {
		this.et_password = et_password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static MyApplication instance;

	public static MyApplication getInstance() {

		return

		instance;

	}

	@Override
	public void onCreate() {

		// TODO Auto-generated method stub

		super.onCreate();

		instance = this;

	}

}

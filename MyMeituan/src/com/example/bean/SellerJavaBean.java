package com.example.bean;

import java.util.Arrays;

public class SellerJavaBean {
	private String s_id;
	private String s_name;
	private String s_address;
	private String s_starting;
	private byte[] s_img;
//	private String str = "img";

	public SellerJavaBean(String s_id, String s_name, String s_address,
			String s_starting, byte[] s_sellerpic) {
		
		super();
		this.s_id = s_id;
		this.s_name = s_name;
		this.s_address = s_address;
		this.s_starting = s_starting;
		this.s_img = s_sellerpic;
	}

	public String getS_id() {
		return s_id;
	}

	public void setS_id(String s_id) {
		this.s_id = s_id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_address() {
		return s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public String getS_starting() {
		return s_starting;
	}

	public void setS_starting(String s_starting) {
		this.s_starting = s_starting;
	}

	public byte[] getS_sellerpic() {
		return s_img;
	}

	public void setS_sellerpic(byte[] s_sellerpic) {
		this.s_img = s_sellerpic;
	}

	@Override
	public String toString() {
		return "SellerJavaBean [s_id=" + s_id + ", s_name=" + s_name
				+ ", s_address=" + s_address + ", s_starting=" + s_starting
				+ ", s_sellerpic=" + Arrays.toString(s_img) + "]";
	}

}

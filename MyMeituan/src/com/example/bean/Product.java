package com.example.bean;

/**
 * @author sujunhua
 *登陆成功后，获取服务器返回的json数据，并解析
 */
public class Product {


	private int u_id;
	private String u_tele;
	private String u_pwd;
	private String u_address;
	private String u_name;
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getU_tele() {
		return u_tele;
	}
	public void setU_tele(String u_tele) {
		this.u_tele = u_tele;
	}
	public String getU_pwd() {
		return u_pwd;
	}
	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}
	public String getU_address() {
		return u_address;
	}
	public void setU_address(String u_address) {
		this.u_address = u_address;
	}
	
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public Product(int u_id, String u_tele, String u_pwd, String u_address,String u_name) {
		super();
		this.u_id = u_id;
		this.u_tele = u_tele;
		this.u_pwd = u_pwd;
		this.u_address = u_address;
		this.u_name = u_name;
	}
	
}

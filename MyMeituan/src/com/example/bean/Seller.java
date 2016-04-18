package com.example.bean;


//商家信息
public class Seller {

	//商家id
		private int s_id;
		//商家用户名
		private String s_name;
	    //商家地址
		private String s_address;
	    //商家电话
		private String s_tele;
		//商家起送价
		private String s_starting;
		
		//构造器
		public Seller() {
			super();
		}
		//构造器
		public Seller(int s_id, String s_name, String s_address, String s_tele,
				String s_starting) {
			super();
			this.s_id = s_id;
			this.s_name = s_name;
			this.s_address = s_address;
			this.s_tele = s_tele;
			this.s_starting = s_starting;
		}
		//get set方法
		public int getS_id() {
			return s_id;
		}
		public void setS_id(int s_id) {
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
		public String getS_tele() {
			return s_tele;
		}
		public void setS_tele(String s_tele) {
			this.s_tele = s_tele;
		}
		public String getS_starting() {
			return s_starting;
		}
		public void setS_starting(String s_starting) {
			this.s_starting = s_starting;
		}
		@Override
		public String toString() {
			return "Seller [s_id=" + s_id + ", s_name=" + s_name
					+ ", s_address=" + s_address + ", s_tele=" + s_tele
					+ ", s_starting=" + s_starting + "]";
		}
		
		
		
		
		
		
}

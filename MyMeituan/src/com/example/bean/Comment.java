package com.example.bean;



public class Comment {

	//评价id
		private int c_id;
		//评价用户
		private String u_name;
		//评价内容
		private String c_content;
		//评价时间
		private String c_time;
		
		//评价对象 及商户id
		private int c_sellerId;
		
		
		public Comment() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Comment( String u_name, String c_content, String c_time,
				int c_sellerId) {
			super();
			this.u_name = u_name;
			this.c_content = c_content;
			this.c_time = c_time;
			this.c_sellerId = c_sellerId;
		}
		public int getC_id() {
			return c_id;
		}
		public void setC_id(int c_id) {
			this.c_id = c_id;
		}
		public String getU_name() {
			return u_name;
		}
		public void setU_name(String string) {
			this.u_name = string;
		}
		public String getC_content() {
			return c_content;
		}
		public void setC_content(String c_content) {
			this.c_content = c_content;
		}
		public String getC_time() {
			return c_time;
		}
		public void setC_time(String c_time) {
			this.c_time = c_time;
		}
		public int getC_sellerId() {
			return c_sellerId;
		}
		public void setC_sellerId(int c_sellerId) {
			this.c_sellerId = c_sellerId;
		}
		@Override
		public String toString() {
			return "Comment [c_id=" + c_id + ", u_name=" + u_name + ", c_content="
					+ c_content + ", c_time=" + c_time + ", c_sellerId="
					+ c_sellerId + "]";
		}
		
		
}

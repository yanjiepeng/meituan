package com.example.seller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymeituan.R;
import com.example.util.MyHttpUtils;
import com.lidroid.xutils.http.RequestParams;

public class ShopLoginActivity extends Activity implements OnClickListener {
	
	public static final String ACTION_LOGIN="200";
	public static final String ACTION_REGIST="201";
	public static final String ACTION_GET_INDENT="202";
	public static final String ACTION_UPLOAD_MENU="203";
	public static final String url="http://http://yanjiepeng.eicp.net:24310/YueFanServer/login";
	private EditText et_name,et_password;
	private Button btn_login,btn_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//去掉界面标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_login);
		initView();
	}
	//初始化登录界面的控件
	private void initView() {
		// TODO Auto-generated method stub
		et_name=(EditText) this.findViewById(R.id.et_name_login);
		et_password=(EditText) this.findViewById(R.id.et_password_login);
		btn_login=(Button) this.findViewById(R.id.btn_login_login);
		btn_register=(Button) this.findViewById(R.id.btn_register_login);
		//为登录按钮和注册按钮设置点击事件监听
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}
	//点击触发监听事件时调用
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		//点击登录按钮
		case R.id.btn_login_login:
			//
			if(et_name.getText().toString()!=null&&et_password.getText().toString()!=null){
				RequestParams params=new RequestParams();
				params.addQueryStringParameter("name", et_name.getText().toString());
				params.addQueryStringParameter("password", et_password.getText().toString());
				String result=new MyHttpUtils().getNetwork(url, params);
				//用户名和密码正确则跳转到商家主界面
				if(result.equals("")){
					Intent intent=new Intent(ShopLoginActivity.this,ShopMainActivity.class);
					this.startActivity(intent);
					//跳转后销毁登录界面
					this.finish();
				}
			}
			break;
		//点击注册按钮
		case R.id.btn_register_login:
//			Intent intent=new Intent(ShopLoginActivity.this,ShopRegisterActivity.class);
//			this.startActivity(intent);
			startActivity(new Intent(ShopLoginActivity.this, ShopRegistActivity.class));
			break;
		}
	}

}

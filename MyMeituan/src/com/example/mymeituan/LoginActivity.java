package com.example.mymeituan;

import com.example.fragment.PasswordFragment;
import com.example.fragment.PhoneFragment;

import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author sujunhua
 *这是登陆界面的activity，其中包含2个fragment，其中一个fragment是手机号快捷登陆，一个是账户密码登陆
 */
public class LoginActivity extends Activity implements OnClickListener {
	

	LinearLayout linearlayoutLogin, linearlayoutLoginbar;
	TextView tvPhone, tvPassword, tvRegister;
	ColorStateList orangeColor1, orangeColor2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		// 初始化短信验证sdk
		SMSSDK.initSDK(this, "d9f416ffffca", "2d568c1884f4e29e48892b6b7fb30f35");
		init();

		FragmentTransaction ts = getFragmentManager().beginTransaction();
		ts.add(R.id.linearlayout_activity_login, new PasswordFragment());
		ts.commit();

	}

	private void init() {
		// TODO Auto-generated method stub
		//定义了2种字体颜色，用于改变不同fragment切换时对应的变化
		orangeColor1 = ColorStateList.valueOf(0xFFFFA500);
		orangeColor2 = ColorStateList.valueOf(0xFF2F4F4F);
		
		//用于装fragment的容器
		linearlayoutLogin = (LinearLayout) findViewById(R.id.linearlayout_activity_login);
		//退出当前activity的一个控件
		linearlayoutLoginbar = (LinearLayout) findViewById(R.id.linearlayout_activity_login_bar);
		linearlayoutLoginbar.setOnClickListener(this);

		tvPhone = (TextView) findViewById(R.id.tv_phone_activity_login);
		tvPhone.setOnClickListener(this);

		tvPassword = (TextView) findViewById(R.id.tv_password_activity_login);
		tvPassword.setTextColor(orangeColor1);
		tvPassword.setOnClickListener(this);
		
		//立即注册的TextView，并未此设置点击监听事件
		tvRegister = (TextView) findViewById(R.id.tv_activity_login_register);
		tvRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_phone_activity_login:
			//点击后为手机号快捷登陆的fragment
			tvPhone.setTextColor(orangeColor1);
			tvPassword.setTextColor(orangeColor2);

			FragmentTransaction ts1 = getFragmentManager().beginTransaction();
			ts1.replace(R.id.linearlayout_activity_login, new PhoneFragment());
			ts1.commit();

			break;
		case R.id.tv_password_activity_login:
			//点击后为账号秘密登陆的fragment
			tvPassword.setTextColor(orangeColor1);
			tvPhone.setTextColor(orangeColor2);

			FragmentTransaction ts2 = getFragmentManager().beginTransaction();
			ts2.replace(R.id.linearlayout_activity_login,
					new PasswordFragment());
			ts2.commit();
			break;

		case R.id.tv_activity_login_register:
			//点击后跳转到注册的activity
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			
			//点击注册界面后，注销事件监听器
			SMSSDK.unregisterAllEventHandler();
			break;
		case R.id.linearlayout_activity_login_bar:
			//点击后退出当前activity
			finish();
			break;
		default:
			break;
		}
	}

}

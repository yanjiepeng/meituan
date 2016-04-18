package com.example.seller;


import com.example.mymeituan.R;
import com.example.util.MyHttpUtils;
import com.lidroid.xutils.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShopRegistActivity extends Activity {
	
	private EditText et_phone, et_password, et_name, et_address;
	private Button btn_sure;
	public static final String url = "http://http://yanjiepeng.eicp.net:24310/YueFanServer/login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_register);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_name = (EditText) this.findViewById(R.id.et_name_register);
		et_phone = (EditText) this.findViewById(R.id.et_phone_register);
		et_address = (EditText) this.findViewById(R.id.et_address_register);
		et_password = (EditText) findViewById(R.id.et_password_register);
		btn_sure = (Button) this.findViewById(R.id.btn_sure_register);

		final String phone = et_phone.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		final String name = et_name.getText().toString().trim();
		final String address = et_address.getText().toString().trim();

		final RequestParams params = new RequestParams();
		btn_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (phone != null && password != null && name != null
						&& address != null) {
					// 设置请求参数
					
					params.addQueryStringParameter("REQUEST_CODE",
							ShopLoginActivity.ACTION_REGIST);
					params.addQueryStringParameter("s_tele", et_phone.getText().toString().trim());
					params.addQueryStringParameter("s_pwd", et_password.getText().toString().trim());
					params.addQueryStringParameter("s_name", et_name.getText().toString().trim());
					params.addQueryStringParameter("s_address", et_address.getText().toString().trim());
					// 执行网络请求，并用Toast弹出返回信息
					String result = new MyHttpUtils().getNetwork(url, params);
					Toast.makeText(ShopRegistActivity.this, result,
							Toast.LENGTH_LONG).show();
					// 如果注册成功，则跳转回登录界面
					if (result.equals("")) {
						Intent intent = new Intent(ShopRegistActivity.this,
								ShopLoginActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});
	}


}

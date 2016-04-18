package com.example.mymeituan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.application.MyApplication;
import com.example.application.SysApplication;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddressActivity extends Activity implements OnClickListener {

	Button btnAdd, btnManager, btnUpdata;
	LinearLayout linearlayoutBar;
	EditText etAddress, etname;
	TextView etphone;
	MyApplication a;
	String name;
	String phone;
	String address;
	String id ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_address);

				init();

				a = (MyApplication) getApplication();
				name = a.getName();
				phone = a.getEt_phone();
				address = a.getAddress();
				id= a.getU_id()+"";

				if (name != null && phone != null && address != null) {

					etname.setText(name);
					etphone.setText(phone+"    id:"+id);
					etAddress.setText(address);
				}
				// 将这个activity加入到list中
				SysApplication.getInstance().addActivity(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		btnAdd = (Button) findViewById(R.id.btn_add_activity_address);
		btnManager = (Button) findViewById(R.id.btn_manager_activity_address);
		btnUpdata = (Button) findViewById(R.id.btn_updata_address_activity_address);
		btnAdd.setOnClickListener(this);
		btnManager.setOnClickListener(this);
		btnUpdata.setOnClickListener(this);

		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_address);
		linearlayoutBar.setOnClickListener(this);

		etAddress = (EditText) findViewById(R.id.et_address_activity_address);
		etname = (EditText) findViewById(R.id.et_name_activity_address);
		etphone = (TextView) findViewById(R.id.et_phone_activity_address);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add_activity_address:
			Intent intent = new Intent(AddressActivity.this,
					AddAddressActivity.class);
			startActivityForResult(intent, 1);

			break;
		case R.id.btn_manager_activity_address:

			break;
		case R.id.btn_updata_address_activity_address:

			MyApplication a = (MyApplication) this.getApplication();
			if ((etname.getText().toString()).equals(a.getName())
					&& (etphone.getText().toString()).equals(a.getEt_phone())
					&& (etAddress.getText().toString()).equals(a.getAddress())) {
				Toast.makeText(this, "请修改您需要修改的属性", Toast.LENGTH_SHORT).show();
			} else {
				// 创建httpUtils对象，发送post
				HttpUtils mUtils = new HttpUtils();

				RequestParams params = new RequestParams();
				params.addQueryStringParameter("REQUEST_CODE", "110");
				params.addQueryStringParameter("u_tele", etphone.getText()
						.toString());
				try {
					params.addQueryStringParameter("u_name", URLEncoder.encode(
							etname.getText().toString(), "utf-8"));
					params.addQueryStringParameter("u_address", URLEncoder
							.encode(etAddress.getText().toString(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mUtils.send(HttpMethod.POST,
						"http://yanjiepeng.eicp.net:24310/YueFanServer/login",
						params, new RequestCallBack<String>() {

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// TODO Auto-generated method stub
								super.onLoading(total, current, isUploading);

								Log.i("FSLog", current + "/" + total
										+ isUploading);
							}

							@Override
							public void onStart() {
								// TODO Auto-generated method stub
								super.onStart();
								Log.i("FSLog", "start send post");
							}

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub
								Log.i("FSLog", "post 失败 " + arg0.toString());
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								String result = arg0.result;
								Log.i("FSLog", "post 成功");
								Log.i("FSLog", result);

								if ((result != null) && (result.equals("true"))) {

									Log.i("FSLog", "result:" + result);

									MyApplication a = (MyApplication) getApplication();
									a.setAddress(etAddress.getText().toString());
									a.setEt_phone(etphone.getText().toString());
									a.setName(etname.getText().toString());

								}
							}
						});
			}
			break;
		case R.id.linearlayout_activity_address:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && data != null) {

			etname.setText(data.getExtras().getString("name"));
			etphone.setText(data.getExtras().getString("phone"));
			etAddress.setText(data.getExtras().getString("address"));
		}
	}

}

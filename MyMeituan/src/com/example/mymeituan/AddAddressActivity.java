package com.example.mymeituan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.application.MyApplication;
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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AddAddressActivity extends Activity implements OnClickListener {

	LinearLayout linearlayoutAddAddress;
	ImageButton ibtnCommit;
	EditText u_name, u_tele, etMapAddress, etNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		linearlayoutAddAddress = (LinearLayout) findViewById(R.id.linearlayout_activity_address);
		linearlayoutAddAddress.setOnClickListener(this);
		ibtnCommit = (ImageButton) findViewById(R.id.ibtn_activity_add_address);
		ibtnCommit.setOnClickListener(this);

		u_name = (EditText) findViewById(R.id.et_putname_activity_add_address);
		u_tele = (EditText) findViewById(R.id.et_putphone_activity_add_address);
		etMapAddress = (EditText) findViewById(R.id.et_map_address_activity_add_address);
		etNumber = (EditText) findViewById(R.id.et_number_activity_add_address);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearlayout_activity_address:
			finish();
			break;
		case R.id.ibtn_activity_add_address:
			// 提交地址数据到服务器

						// 创建httpUtils对象，发送post
						HttpUtils mUtils = new HttpUtils();

						RequestParams params = new RequestParams();
						params.addQueryStringParameter("REQUEST_CODE", "110");
						params.addQueryStringParameter("u_tele", u_tele.getText()
								.toString());
						try {
							params.addQueryStringParameter("u_name",
									URLEncoder.encode(u_name.getText().toString(), "utf-8"));
							params.addQueryStringParameter(
									"u_address",
									URLEncoder.encode(etMapAddress.getText().toString()
											+ etNumber.getText().toString(), "utf-8"));
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

										Log.i("FSLog", current + "/" + total + isUploading);
									}

									@Override
									public void onStart() {
										// TODO Auto-generated method stub
										super.onStart();
										Log.i("FSLog", "start send post");
									}

									@Override
									public void onFailure(HttpException arg0, String arg1) {
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
											a.setAddress(etMapAddress.getText().toString()
													+ etNumber.getText().toString());
											a.setEt_phone(u_tele.getText().toString());
											a.setName(u_name.getText().toString());

											Intent intent = new Intent();
											intent.putExtra("address", etMapAddress
													.getText().toString()
													+ etNumber.getText().toString());
											intent.putExtra("name", u_name.getText()
													.toString());
											intent.putExtra("phone", u_tele.getText()
													.toString());
											setResult(1, intent);
											finish();
										}
									}
								});
			break;
		default:
			break;
		}
	}
}

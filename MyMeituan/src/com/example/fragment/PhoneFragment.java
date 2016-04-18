package com.example.fragment;

import com.example.application.MyApplication;
import com.example.bean.Product;
import com.example.mymeituan.R;
import com.example.mymeituan.R.id;
import com.example.mymeituan.R.layout;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * @author muhua55 手机号快捷登陆的fragment，用的SMSSSDK 注：短信验证一个手机号码只能验证一次
 */
public class PhoneFragment extends Fragment implements OnClickListener {

	Button btnIdentify, btnLogin;
	EditText etPhone, etCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_login_phone, container,
				false);

		// 初始化短信验证sdk
		SMSSDK.initSDK(getActivity(), "d98fd15fec60",
				"168fc3b7f5c67c3cc85af5c3472ea6cb");

		etPhone = (EditText) view.findViewById(R.id.et_fragment_phone);
		etCode = (EditText) view.findViewById(R.id.et_fragment_phone_code);

		btnIdentify = (Button) view
				.findViewById(R.id.btn_identify_fragment_login);
		btnLogin = (Button) view.findViewById(R.id.btn_login_fragment_phone);

		// 为获取验证码按钮设置监听
		btnIdentify.setOnClickListener(this);
		// 为登陆按钮设置监听器
		btnLogin.setOnClickListener(this);
		return view;
	}

	private EventHandler mHandler = new EventHandler() {

		@Override
		public void afterEvent(int arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			super.afterEvent(arg0, arg1, arg2);
			switch (arg0) {
			// 获取到验证码后被回调
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:

				if (arg1 == SMSSDK.RESULT_COMPLETE) {
					Log.i("FSLog",
							"登陆：EVENT_GET_VERIFICATION_CODE RESULT_COMPLETE");
				} else {
					Log.i("FSLog", "登陆：EVENT_GET_VERIFICATION_CODE ERROR");
				}

				break;
			// 提交验证码后的回调，表明结果成功与否
			case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:

				if (arg1 == SMSSDK.RESULT_COMPLETE) {
					Log.i("FSLog", "登陆：验证成功");

					// 通过验证后跳转到MyFragment所在MainActivity
					// 创建httpUtils对象，发送post
					HttpUtils mUtils = new HttpUtils();

					RequestParams params = new RequestParams();
					params.addQueryStringParameter("REQUEST_CODE", "100");
					params.addQueryStringParameter("tel", etPhone.getText()
							.toString());

					mUtils.send(
							HttpMethod.POST,
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
									Log.i("FSLog", "post 成功");
									Log.i("FSLog", arg0.result);

									if (arg0.result.equals("no_regist")) {
										Toast.makeText(getActivity(),
												"您输入的账户没有注册，请先注册", 0).show();
									} else if (arg0.result.equals("no user")) {
										Toast.makeText(getActivity(),
												"您输入的账户不存在，请重新输入", 0).show();
									} else {

										Toast.makeText(getActivity(), "登陆成功", 0)
												.show();
										Gson gson = new Gson();// new一个Gson对象
										// 将一个json字符串转换为java对象
										Product product = gson.fromJson(
												arg0.result, Product.class);
										// 输出
										Log.i("FSLog",
												"Id:" + product.getU_id());
										Log.i("FSLog",
												"Tele:" + product.getU_tele());
										Log.i("FSLog",
												"Name:" + product.getU_name());
										Log.i("FSLog",
												"Pwd:" + product.getU_pwd());
										Log.i("FSLog",
												"Address:"
														+ product
																.getU_address());
										
										int u_id = product.getU_id();
										String tele = product.getU_tele();
										String name = product.getU_name();
										String address = product.getU_address();

										// 点击登陆按钮后跳转至MyFragment所在MainActivity
										MyApplication a = (MyApplication) getActivity()
												.getApplication();
										a.setU_id(u_id);
										a.setName(name);
										a.setAddress(address);
										a.setEt_phone(tele);
										// 添加给第一个Activity的返回值，并设置resultCode
										Intent intent = new Intent();
										intent.putExtra("et_phone", etPhone
												.getText().toString());
										getActivity().setResult(0, intent);
										getActivity().finish();
									}

								}
							});

				} else {
					Log.i("FSLog", "登陆：验证失败");
				}
				break;
			default:
				break;
			}

		}

		@Override
		public void beforeEvent(int arg0, Object arg1) {
			// TODO Auto-generated method stub
			super.beforeEvent(arg0, arg1);

		}

		@Override
		public void onRegister() {
			// TODO Auto-generated method stub
			super.onRegister();
			Log.i("FSLog", "登陆：onRegister");
		}

		@Override
		public void onUnregister() {
			// TODO Auto-generated method stub
			super.onUnregister();
			Log.i("FSLog", "登陆：onUnregister");
		}

	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 注销事件监听器
		SMSSDK.unregisterAllEventHandler();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_identify_fragment_login:

			if (etPhone.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "请输入您的手机号", 0).show();
			} else {
				// 注册事件监听器
				SMSSDK.registerEventHandler(mHandler);
				// 获取验证码
				Toast.makeText(getActivity(), "获取验证码中，请稍后", Toast.LENGTH_LONG)
						.show();
				SMSSDK.getVerificationCode("86", etPhone.getText().toString());

			}
			break;
		case R.id.btn_login_fragment_phone:

			if (etPhone.getText().toString().equals("")
					&& etCode.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "请输入您的手机号", 0).show();
			} else if (etCode.getText().toString().equals("")) {

				Toast.makeText(getActivity(), "请输入收到的验证码,如未收到请重新获取", 0).show();
			} else {
				// 校验验证码
				SMSSDK.submitVerificationCode("86", etPhone.getText()
						.toString(), etCode.getText().toString());
			}
			break;
		default:
			break;
		}
	}
}

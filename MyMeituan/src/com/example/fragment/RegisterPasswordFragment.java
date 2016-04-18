package com.example.fragment;

import com.example.application.MyApplication;
import com.example.mymeituan.LoginActivity;
import com.example.mymeituan.R;
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

/**
 * @author sujunhua 设置密码的fragment
 */
public class RegisterPasswordFragment extends Fragment implements
		OnClickListener {

	EditText u_pwd, u_pwd1;
	Button btnRegister;
	String u_tele;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_register_password,
				container, false);

		u_pwd = (EditText) view
				.findViewById(R.id.et_fragment_register_password);
		u_pwd1 = (EditText) view
				.findViewById(R.id.et_fragment_register_password_agin);

		btnRegister = (Button) view
				.findViewById(R.id.btn_fragment_register_register);
		btnRegister.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_fragment_register_register:
			if (u_pwd.getText().toString().equals(u_pwd1.getText().toString())) {

				MyApplication a = (MyApplication) getActivity()
						.getApplication();
				u_tele = a.getEt_phone();

				Log.i("FSLog", u_tele);

				// 创建httpUtils对象，发送post
				HttpUtils mUtils = new HttpUtils();

				RequestParams params = new RequestParams();
				params.addQueryStringParameter("REQUEST_CODE", "101");
				params.addQueryStringParameter("u_tele", u_tele);
				params.addQueryStringParameter("u_pwd", u_pwd.getText()
						.toString());

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
								Log.i("FSLog", "post 成功");
								Log.i("FSLog", arg0.result);

								// 注册成功后直接跳转至登录所在的Activity
								Intent intent = new Intent(getActivity(),
										LoginActivity.class);
								startActivity(intent);
								getActivity().finish();

							}
						});
			} else {
				Toast.makeText(getActivity(), "两次输入的密码不一致，请重新输入", 0).show();
			}
			break;
		default:
			break;
		}
	}

}

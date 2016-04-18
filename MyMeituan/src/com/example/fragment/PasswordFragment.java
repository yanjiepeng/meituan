package com.example.fragment;

import com.example.application.MyApplication;
import com.example.bean.Product;
import com.example.mymeituan.R;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sujunhua 账户密码登陆的fragment
 */
public class PasswordFragment extends Fragment implements OnClickListener {

	EditText et_phone, et_password;
	TextView tvForget;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_login_acount, container,
				false);

		et_phone = (EditText) view.findViewById(R.id.et_fragment_login);
		et_password = (EditText) view.findViewById(R.id.et_fragment_password);
		tvForget = (TextView) view
				.findViewById(R.id.tv_forget_password_login_acount);
		tvForget.setOnClickListener(this);
		Button btnLogin = (Button) view
				.findViewById(R.id.btn_fragment_login_acount);
		btnLogin.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_fragment_login_acount:
			if ((et_phone.getText().toString()).equals("")
					&& (et_password.getText().toString()).equals("")) {
				Toast.makeText(getActivity(), "请输入账户", 0).show();
			} else if ((et_phone.getText().toString()).equals("")) {
				Toast.makeText(getActivity(), "请输入账户", 0).show();
			} else if ((et_password.getText().toString()).equals("")) {
				Toast.makeText(getActivity(), "请输入密码", 0).show();
			} else {
				// 创建httpUtils对象，发送post
				HttpUtils mUtils = new HttpUtils();

				RequestParams params = new RequestParams();
				params.addQueryStringParameter("REQUEST_CODE", "1000");
				params.addQueryStringParameter("tel", et_phone.getText()
						.toString());
				params.addQueryStringParameter("pwd", et_password.getText()
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

								if (arg0.result.equals("error")) {

									Toast.makeText(getActivity(), "密码错误，请重新输入",
											0).show();
								} else if (arg0.result.equals("no user")) {
									Toast.makeText(getActivity(),
											"您输入的账户不存在，请重新输入", Toast.LENGTH_SHORT).show();
								} else {

									Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT)
											.show();
									Gson gson = new Gson();// new一个Gson对象
									// 将一个json字符串转换为java对象
									Product product = gson.fromJson(
											arg0.result, Product.class);
									// 输出
									Log.i("FSLog", "Id:" + product.getU_id());
									Log.i("FSLog",
											"Tele:" + product.getU_tele());
									Log.i("FSLog",
											"Name:" + product.getU_name());
									Log.i("FSLog", "Pwd:" + product.getU_pwd());
									Log.i("FSLog",
											"Address:" + product.getU_address());

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
									intent.putExtra("et_phone", et_phone
											.getText().toString());
									getActivity().setResult(0, intent);
									getActivity().finish();
								}
							}
						});
			}
			break;
		case R.id.tv_forget_password_login_acount:
			Toast.makeText(getActivity(), "忘记密码请与管理员联系", 0).show();
			break;
		}
	}
}
package com.example.fragment;

import com.example.application.MyApplication;
import com.example.mymeituan.R;

import cn.smssdk.SMSSDK;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author sujunhua 获取验证码的fragment
 */
public class RegisterPhoneFragment extends Fragment {

	EditText u_tele;
	Button btnCode;
	CheckBox cbRegister;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_register_phone,
				container, false);

		u_tele = (EditText) view.findViewById(R.id.et_fragment_register);
		btnCode = (Button) view.findViewById(R.id.btn_fragment_register);
		cbRegister = (CheckBox) view.findViewById(R.id.cb_fragment_register);

		btnCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (u_tele.getText().toString().equals("")) {

					Toast.makeText(getActivity(), "请输入您的手机号", 0).show();
				} else {

					if (cbRegister.isChecked()) {

						MyApplication a = (MyApplication) getActivity()
								.getApplication();
						a.setEt_phone(u_tele.getText().toString());
						// 获取验证码
						SMSSDK.getVerificationCode("86", u_tele.getText()
								.toString());
						
					} else if (!cbRegister.isChecked()) {
						Toast.makeText(getActivity(), "请接受用户协议",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		return view;
	}

}

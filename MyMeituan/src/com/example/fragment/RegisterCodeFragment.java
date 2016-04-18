package com.example.fragment;

import com.example.application.MyApplication;
import com.example.mymeituan.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * @author sujunhua 提交验证码的frament
 */
public class RegisterCodeFragment extends Fragment implements OnClickListener {

	Button btnCommitCode;
	EditText etCode;
	String u_tele;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_register_code,
				container, false);

		btnCommitCode = (Button) view
				.findViewById(R.id.btn_fragment_register_code);

		etCode = (EditText) view.findViewById(R.id.et_fragment_register_code);
		btnCommitCode.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_fragment_register_code:
			MyApplication a = (MyApplication) getActivity().getApplication();
			u_tele = a.getEt_phone();
			Log.i("FSLog", u_tele);
			// 校验验证码
			SMSSDK.submitVerificationCode("86", u_tele, etCode.getText()
					.toString());
		default:
			break;
		}
	}
}

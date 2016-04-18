package com.example.mymeituan;

import com.example.application.MyApplication;
import com.example.application.SysApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MycountActivity extends Activity implements OnClickListener {

	RelativeLayout relativelayoutIcon, relativelayoutUsername,
			relativelayoutNumber;
	LinearLayout lineralayoutBar;
	TextView tvUsername, etPhone;
	Button btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycount);

		init();

		MyApplication a = (MyApplication) this.getApplication();
		String et_phone = a.getEt_phone();

		tvUsername.setText(et_phone);
		etPhone.setText(et_phone);
	}

	private void init() {
		// TODO Auto-generated method stub
		relativelayoutIcon = (RelativeLayout) findViewById(R.id.relativelayout_icon_activity_mycount);
		relativelayoutIcon.setOnClickListener(this);
		relativelayoutUsername = (RelativeLayout) findViewById(R.id.relativelayout_username_activity_mycount);
		relativelayoutUsername.setOnClickListener(this);
		relativelayoutNumber = (RelativeLayout) findViewById(R.id.relativelayout_number_activity_mycount);
		relativelayoutNumber.setOnClickListener(this);
		lineralayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_mycount);
		lineralayoutBar.setOnClickListener(this);

		tvUsername = (TextView) findViewById(R.id.tv_username_activity_mycount);
		tvUsername.setOnClickListener(this);
		btnExit = (Button) findViewById(R.id.btn_exit_activity_mycount);
		btnExit.setOnClickListener(this);
		etPhone = (TextView) findViewById(R.id.tv_user_number_activity_mycount);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relativelayout_icon_activity_mycount:
			break;
		case R.id.relativelayout_username_activity_mycount:
			break;
		case R.id.relativelayout_number_activity_mycount:
			break;
		case R.id.linearlayout_activity_mycount:
			finish();
			break;
		case R.id.tv_username_activity_mycount:
			break;
		case R.id.btn_exit_activity_mycount:

			Intent intent = new Intent(MycountActivity.this, HomeActivity.class);
			startActivity(intent);
			SysApplication.getInstance().exit();
			break;
		default:
			break;

		}
	}
}

package com.example.mymeituan;

import com.example.application.SysApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class RedpacketActivity extends Activity implements OnClickListener {

	Button btnRedpacket;
	LinearLayout linearlayoutBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacketmeituan);

		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_redpacketmeituan);
		linearlayoutBar.setOnClickListener(this);

		btnRedpacket = (Button) findViewById(R.id.btn_activity_redpacketmeituan);
		btnRedpacket.setOnClickListener(this);

		// 将这个activity加入到list中
		SysApplication.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_activity_redpacketmeituan:
			break;
		case R.id.linearlayout_activity_redpacketmeituan:
			finish();
			break;
		default:
			break;
		}
	}
}

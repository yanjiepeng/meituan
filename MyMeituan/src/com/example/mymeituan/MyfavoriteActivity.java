package com.example.mymeituan;

import com.example.application.SysApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyfavoriteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myfavorite);

		Button btnReload = (Button) findViewById(R.id.btn_reload_activity_myfavorite);
		btnReload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		LinearLayout linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_myfavorite);
		linearlayoutBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 将这个activity加入到list中
		SysApplication.getInstance().addActivity(this);
	}
}

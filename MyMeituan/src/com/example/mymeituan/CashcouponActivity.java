package com.example.mymeituan;

import com.example.application.SysApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class CashcouponActivity extends Activity {

	LinearLayout linearlayoutBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cashcoupon);

		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_cashcoupon_business);
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

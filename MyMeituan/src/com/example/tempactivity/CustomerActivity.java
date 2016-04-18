package com.example.tempactivity;

import com.example.fragment.CustomerIndentFragment;
import com.example.mymeituan.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RelativeLayout;

public class CustomerActivity extends FragmentActivity{
	FragmentManager fm;
	RelativeLayout test;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_customer);
	test = (RelativeLayout) this.findViewById(R.id.rl_test);
	CustomerIndentFragment fragment = new CustomerIndentFragment();
	fm = getSupportFragmentManager();
	FragmentTransaction ft = fm.beginTransaction();
	ft.replace(R.id.rl_test, fragment);
	//ft.addToBackStack(null);
	ft.commitAllowingStateLoss();
}
}

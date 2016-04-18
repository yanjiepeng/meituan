package com.example.tempactivity;

import java.util.ArrayList;

import com.example.mymeituan.R;
import com.example.widget.Guide_switch_bar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	int curVer = 0;
	ArrayList<View> pages = new ArrayList<View>();
	ViewPager viewPager;
	Button guide_customer, guide_celler, btn_switch_bar1, btn_switch_bar2,
			btn_switch_bar3, btn_switch_bar4;
	Intent intent;
	Guide_switch_bar gsb;
	int flag = 0;
	static final int STATE_BTN_1 = 0;
	static final int STATE_BTN_2 = 1;
	static final int STATE_BTN_3 = 2;
	static final int STATE_BTN_4 = 3;
	static final int CUSTOMER_STATE = 0;
	static final int CELLER_STATE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		boolean show_state = true;

		try {
			show_state = needShowGuide();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		if (!show_state) {
			intent = new Intent(GuideActivity.this, WelcomeActivity.class);
			startActivity(intent);
			finish();
		} else {
			SharedPreferences sp = getSharedPreferences("version",
					Context.MODE_PRIVATE);
			sp.edit().putInt("version", curVer).commit();
		}
		setContentView(R.layout.activity_guide);
		init();

	}

	private void init() {
		pages.add(LayoutInflater.from(this).inflate(R.layout.guide_guide_page1,
				null));
		pages.add(LayoutInflater.from(this).inflate(R.layout.guide_guide_page2,
				null));
		pages.add(LayoutInflater.from(this).inflate(R.layout.guide_guide_page3,
				null));
		View guide_page_4 = LayoutInflater.from(this).inflate(
				R.layout.guide_guide_page4, null);
		pages.add(guide_page_4);
		gsb = (Guide_switch_bar) findViewById(R.id.gsb_guide_guide);
		guide_customer = (Button) guide_page_4
				.findViewById(R.id.btn_guide_customer);
		guide_celler = (Button) guide_page_4
				.findViewById(R.id.btn_guide_celler);
		btn_switch_bar1 = (Button) gsb.findViewById(R.id.btn_switch_bar_1);
		btn_switch_bar1.setBackgroundResource(R.drawable.shape_guide_switch_bar_button_click);
		btn_switch_bar2 = (Button) gsb.findViewById(R.id.btn_switch_bar_2);
		btn_switch_bar3 = (Button) gsb.findViewById(R.id.btn_switch_bar_3);
		btn_switch_bar4 = (Button) gsb.findViewById(R.id.btn_switch_bar_4);
		guide_customer.setOnClickListener(this);
		guide_celler.setOnClickListener(this);
		btn_switch_bar1.setOnClickListener(this);
		btn_switch_bar2.setOnClickListener(this);
		btn_switch_bar3.setOnClickListener(this);
		btn_switch_bar4.setOnClickListener(this);
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
		viewPager = (ViewPager) findViewById(R.id.vp_guide_guide);
		viewPager.setAdapter(myPagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(this);

	}

	private boolean needShowGuide() throws NameNotFoundException {
		boolean b = true;
		PackageManager manager = this.getPackageManager();
		PackageInfo pi = manager.getPackageInfo(this.getPackageName(), 0);
		curVer = pi.versionCode;
		SharedPreferences sp = getSharedPreferences("version",
				Context.MODE_PRIVATE);
		int storedVer = sp.getInt("version", 0);
		b = !(curVer == storedVer);
		return b;
	}

	@Override
	public void onClick(View v) {
		SharedPreferences sp = this.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		Editor e = sp.edit();
		switch (v.getId()) {
		case R.id.btn_guide_customer:
			e.putInt("userState", CUSTOMER_STATE);
			e.apply();
			intent = new Intent(GuideActivity.this, WelcomeActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_guide_celler:
			e.putInt("userState", CELLER_STATE);
			e.apply();
			intent = new Intent(GuideActivity.this, WelcomeActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_switch_bar_1:
			viewPager.setCurrentItem(0);
			break;
		case R.id.btn_switch_bar_2:
			viewPager.setCurrentItem(1);
			break;
		case R.id.btn_switch_bar_3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.btn_switch_bar_4:
			viewPager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}

	private void change(int state) {
		switch (state) {
		case STATE_BTN_1:
			btn_switch_bar1
			.setBackgroundResource(R.drawable.shape_guide_switch_bar_button);
			break;
		case STATE_BTN_2:
			btn_switch_bar2
			.setBackgroundResource(R.drawable.shape_guide_switch_bar_button);
			break;
		case STATE_BTN_3:
			btn_switch_bar3
			.setBackgroundResource(R.drawable.shape_guide_switch_bar_button);
			break;
		case STATE_BTN_4:
			btn_switch_bar4
			.setBackgroundResource(R.drawable.shape_guide_switch_bar_button);
			break;
		default:
			break;
		}
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(pages.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pages.get(position));
			return pages.get(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			btn_switch_bar1
					.setBackgroundResource(R.drawable.shape_guide_switch_bar_button_click);
			change(flag);
			flag = STATE_BTN_1;
			break;
		case 1:
			btn_switch_bar2
					.setBackgroundResource(R.drawable.shape_guide_switch_bar_button_click);
			change(flag);
			flag = STATE_BTN_2;
			break;
		case 2:
			btn_switch_bar3
					.setBackgroundResource(R.drawable.shape_guide_switch_bar_button_click);
			change(flag);
			flag = STATE_BTN_3;
			break;
		case 3:
			btn_switch_bar4
					.setBackgroundResource(R.drawable.shape_guide_switch_bar_button_click);
			change(flag);
			flag = STATE_BTN_4;
			break;

		default:
			break;
		}
	}
}
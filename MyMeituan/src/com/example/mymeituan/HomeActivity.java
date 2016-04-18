package com.example.mymeituan;

import java.util.ArrayList;

import cn.smssdk.SMSSDK;

import com.baidu.mapapi.SDKInitializer;
import com.example.application.MyApplication;
import com.example.fragment.CustomerIndentFragment;
import com.example.fragment.FirstPageFragment;
import com.example.fragment.MyFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeActivity extends FragmentActivity implements OnClickListener {

	private Button btn_category_home, btn_order_home, btn_mine_home;
	private TextView tv_category_home, tv_order_home, tv_mine_home;
	private RelativeLayout rl1,rl2,rl3;
	private ViewPager vp_main;
	public static int currentID ;
	ArrayList<Fragment> pages = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		SDKInitializer.initialize(getApplicationContext());
		//SMSSDK.initSDK(arg0, arg1, arg2);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initMenuWiget();
		currentID = ((MyApplication)getApplication()).getU_id();
		


		pages.add(new FirstPageFragment());
		pages.add(new CustomerIndentFragment());
		pages.add(new MyFragment());

		// home   viewpager
		vp_main = (ViewPager) findViewById(R.id.vp_main);
		vp_main.setOffscreenPageLimit(3);
		vp_main.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		// 设计viewpage监听
		vp_main.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					ButtonIsSelected(0);
					break;

				case 1:
					ButtonIsSelected(1);
					break;
				case 2:
					ButtonIsSelected(2);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	// ViewPager监听器
	class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return pages.get(arg0);
		}

	}

	// 初始化widget
	private void initMenuWiget() {
		// TODO Auto-generated method stub
		btn_category_home = (Button) findViewById(R.id.btn_category_home);
		btn_mine_home = (Button) findViewById(R.id.btn_mine_home);
		btn_order_home = (Button) findViewById(R.id.btn_order_home);
		tv_category_home = (TextView) findViewById(R.id.tv_category_home);
		tv_mine_home = (TextView) findViewById(R.id.tv_mine_home);
		tv_order_home = (TextView) findViewById(R.id.tv_order_home);
		
		rl1 = (RelativeLayout) findViewById(R.id.rl1);
		rl2 = (RelativeLayout) findViewById(R.id.rl2);
		rl3 = (RelativeLayout) findViewById(R.id.rl3);

		rl1.setOnClickListener(this);
		rl2.setOnClickListener(this);
		rl3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl1:
			//ButtonIsSelected(0);
			vp_main.setCurrentItem(0);
			break;
		case R.id.rl2:
			//ButtonIsSelected(1);
			vp_main.setCurrentItem(1);
			break;
		case R.id.rl3:

			//ButtonIsSelected(2);
			vp_main.setCurrentItem(2);
			break;
		default:
			break;
		}
	}

	// 按钮点击变换
	@SuppressWarnings("deprecation")
	private void ButtonIsSelected(int position) {
		switch (position) {
		case 0:
			btn_category_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_poi_selected));
			btn_mine_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_user_normal));
			btn_order_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_order_normal));

			tv_category_home.setTextColor(getResources().getColor(
					R.color.textSelected));
			tv_mine_home.setTextColor(getResources().getColor(R.color.gray));
			tv_order_home.setTextColor(getResources().getColor(R.color.gray));
			break;
		case 1:
			btn_category_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_poi_normal));
			btn_mine_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_user_normal));
			btn_order_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_order_selected));
			tv_order_home.setTextColor(getResources().getColor(
					R.color.textSelected));
			tv_category_home
					.setTextColor(getResources().getColor(R.color.gray));
			tv_mine_home.setTextColor(getResources().getColor(R.color.gray));
			break;
		case 2:
			btn_category_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_poi_normal));
			btn_mine_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_user_selected));
			btn_order_home.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.takeout_ic_order_normal));
			tv_mine_home.setTextColor(getResources().getColor(
					R.color.textSelected));
			tv_category_home
					.setTextColor(getResources().getColor(R.color.gray));
			tv_order_home.setTextColor(getResources().getColor(R.color.gray));
			break;
		}
	}
	
}

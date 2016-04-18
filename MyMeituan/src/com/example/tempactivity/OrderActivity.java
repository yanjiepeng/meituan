package com.example.tempactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.adapter.OrderDetailsAdapter;
import com.example.adapter.OrderStateAdapter;
import com.example.application.MyApplication;
import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;
import com.example.util.UpData;
import com.example.widget.CustomerIndentListView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderActivity extends Activity implements OnPageChangeListener,
		OnClickListener {
	ViewPager vp;
	CustomerIndentListView vp_lv_1, vp_lv_2;
	LinearLayout order_page1, order_page2, order_title;
	ArrayList<LinearLayout> pages = new ArrayList<LinearLayout>();
	Button order_state, order_details, backhome, order_onemore;
	TextView tv_order_state, tv_order_details, tx_order_name;
	UpData upData;
	List<Map<String, Object>> indentData = new ArrayList<Map<String, Object>>();
	OrderStateAdapter stateAdapter;
	OrderDetailsAdapter detailAdapter;
	int position;
	public String request_code = "REQUEST_CODE";
	public String code_number = "105";
	UpDataUI broadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		init();
	}

	private void init() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("UpData.info");
		broadcastReceiver = new UpDataUI();
		registerReceiver(broadcastReceiver, filter);
		upData = new UpData(this, MyApplication.getInstance().getU_id());
		indentData = upData.queryStu();
		tx_order_name = (TextView) findViewById(R.id.tx_order_name);
		tx_order_name
				.setText(indentData.get(position).get("f_name").toString());
		LayoutInflater inflater = LayoutInflater.from(this);
		vp = (ViewPager) findViewById(R.id.vp_order);
		order_page1 = (LinearLayout) inflater.inflate(
				R.layout.customer_order_page1, null);
		order_page2 = (LinearLayout) inflater.inflate(
				R.layout.customer_order_page2, null);
		pages.add(order_page1);
		pages.add(order_page2);
		OrderPagerAdapter adapter = new OrderPagerAdapter();
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(this);
		order_state = (Button) findViewById(R.id.btn_order_state);
		order_state.setOnClickListener(this);
		order_details = (Button) findViewById(R.id.btn_order_details);
		order_details.setOnClickListener(this);
		tv_order_state = (TextView) findViewById(R.id.tv_order_state);
		tv_order_details = (TextView) findViewById(R.id.tv_order_details);
		backhome = (Button) order_page1
				.findViewById(R.id.btn_customer_order_backhome);
		backhome.setOnClickListener(this);
		order_onemore = (Button) order_page2
				.findViewById(R.id.btn_customer_order_onemore);
		order_onemore.setOnClickListener(this);
		order_title = (LinearLayout) findViewById(R.id.ll_order_title);
		order_title.setOnClickListener(this);
		vp_lv_1 = (CustomerIndentListView) order_page1
				.findViewById(R.id.lv_customer_order_state);
		stateAdapter = new OrderStateAdapter(this);
		vp_lv_1.setAdapter(stateAdapter);
		vp_lv_1.setSelection(1);
		vp_lv_2 = (CustomerIndentListView) order_page2
				.findViewById(R.id.lv_customer_order_information);
		detailAdapter = new OrderDetailsAdapter(this, indentData, position);
		vp_lv_2.setAdapter(detailAdapter);
		vp_lv_2.setSelection(1);
	}

	class OrderPagerAdapter extends PagerAdapter {

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_order_state:
			vp.setCurrentItem(0);
			tv_order_state.setVisibility(View.VISIBLE);
			tv_order_details.setVisibility(View.INVISIBLE);
			break;
		case R.id.btn_order_details:
			vp.setCurrentItem(1);
			tv_order_state.setVisibility(View.INVISIBLE);
			tv_order_details.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_customer_order_backhome:
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_customer_order_onemore:
			Intent i_ntent = new Intent(this, HomeActivity.class);
			startActivity(i_ntent);
			break;
		case R.id.ll_order_title:
			finish();
			break;
		default:
			break;
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
			tv_order_state.setVisibility(View.VISIBLE);
			tv_order_details.setVisibility(View.INVISIBLE);
			break;
		case 1:
			tv_order_state.setVisibility(View.INVISIBLE);
			tv_order_details.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	private class UpDataUI extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int temp = Integer.parseInt((String) indentData.get(position).get(
					"b_status"));
			stateAdapter.setState(temp);
			stateAdapter.notifyDataSetChanged();
			vp_lv_1.setSelection(1);
			detailAdapter.orderData = upData.queryStu();
			detailAdapter.notifyDataSetChanged();
			vp_lv_2.setSelection(1);
		}
	}
}

package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.mymeituan.R;
import com.example.tempactivity.CellerActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderDetailsAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	LinearLayout details, distribution, information;
	RelativeLayout rl_storename;
	TextView tv_storename, name, price, number, distribution_price,
			total_price, expect_time, customer, address, tv_distribution,
			order_time, pay, order_remarks, order_number;
	public List<Map<String, Object>> orderData = new ArrayList<Map<String,Object>>();
	int parentPosition;

	public OrderDetailsAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public OrderDetailsAdapter(Context context,
			List<Map<String, Object>> orderData, int parentPosition) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.orderData = orderData;
		this.parentPosition = parentPosition;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (position) {
		case 0:
			details = (LinearLayout) inflater.inflate(
					R.layout.item_order_details, null);
			rl_storename = (RelativeLayout) details
					.findViewById(R.id.rl_customer_order_storename);
			rl_storename.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, CellerActivity.class);
					context.startActivity(intent);
				}
			});
			tv_storename = (TextView) details
					.findViewById(R.id.tv_customer_order_storename);
			name = (TextView) details.findViewById(R.id.tv_customer_order_name);
			price = (TextView) details
					.findViewById(R.id.tv_customer_order_price);
			number = (TextView) details
					.findViewById(R.id.tv_customer_order_number);
			distribution_price = (TextView) details
					.findViewById(R.id.tv_customer_order_distribution_price);
			total_price = (TextView) details
					.findViewById(R.id.tv_customer_order_total_price);
			initFirstItem();
			return details;
		case 1:
			distribution = (LinearLayout) inflater.inflate(
					R.layout.item_order_distribution, null);
			expect_time = (TextView) distribution
					.findViewById(R.id.tv_customer_order_expect_time);
			customer = (TextView) distribution
					.findViewById(R.id.tv_customer_order_customer);
			address = (TextView) distribution
					.findViewById(R.id.tv_customer_order_address);
			tv_distribution = (TextView) distribution
					.findViewById(R.id.tv_customer_order_distribution);
			initSecondItem();
			return distribution;
		case 2:
			information = (LinearLayout) inflater.inflate(
					R.layout.item_order_information, null);
			order_time = (TextView) information
					.findViewById(R.id.tv_customer_order_order_time);
			pay = (TextView) information
					.findViewById(R.id.tv_customer_order_pay);
			order_remarks = (TextView) information
					.findViewById(R.id.tv_customer_order_remarks);
			order_number = (TextView) information
					.findViewById(R.id.tv_customer_order_order_number);
			initThirdItem();
			return information;
		default:
			break;
		}
		return null;
	}

	private void initThirdItem() {
		order_time.setText(orderData.get(parentPosition).get("b_time")
				.toString());
		// pay.setText(orderData.get(parentPosition).get("pay").toString());
		// order_remarks.setText(orderData.get(parentPosition).get("order_remarks").toString());
		order_number.setText(orderData.get(parentPosition).get("b_id")
				.toString());
	}

	private void initSecondItem() {
		expect_time.setText(orderData.get(parentPosition).get("b_time")
				.toString());
		customer.setText(orderData.get(parentPosition).get("u_name").toString());
		address.setText(orderData.get(parentPosition).get("u_address")
				.toString());
		// tv_distribution.setText(orderData.get(parentPosition).get("tv_distribution")
		// .toString());
	}

	private void initFirstItem() {
		tv_storename.setText(orderData.get(parentPosition).get("s_name")
				.toString());
		name.setText(orderData.get(parentPosition).get("f_name").toString());
		String s_price = orderData.get(parentPosition).get("b_price").toString();
		price.setText(s_price);
		String s_temp = "价格"+s_price + "*1";
		number.setText(s_temp);
		// distribution_price.setText(orderData.get(parentPosition).get("distribution_price")
		// .toString());
		int temp = Integer.parseInt((String) orderData.get(parentPosition).get(
				"b_price")) + 1;
		total_price.setText(temp + "");
	}

}

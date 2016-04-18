package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.adapter.CustomerIndentAdapter;
import com.example.application.MyApplication;
import com.example.mymeituan.R;
import com.example.util.UpData;
import com.example.widget.CustomerIndentListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CustomerIndentFragment extends Fragment implements OnClickListener {
	LinearLayout ll_indent;
	CustomerIndentListView lv_indent;
	Button btn_indent_edit;
	public static List<Map<String, Object>> indentData = new ArrayList<Map<String, Object>>();
	int editState = 0;
	CustomerIndentAdapter ciAdapter;
	public static final int STATE_EDIE = 0;
	public static final int STATE_CANCLE = 1;
	public String request_code = "REQUEST_CODE";
	public String code_number = "105";
	public int currentID = 0;
	UpData upData;
	UpDataUI broadcastReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ll_indent = (LinearLayout) inflater.inflate(
				R.layout.fragment_customer_indent, container, false);

		init();

		return ll_indent;
	}

	private void init() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("UpData.info");
		broadcastReceiver = new UpDataUI();
		this.getActivity().registerReceiver(broadcastReceiver, filter);
		lv_indent = (CustomerIndentListView) ll_indent
				.findViewById(R.id.lv_customer_indent_indent);
		lv_indent.request_code = "REQUEST_CODE";
		lv_indent.code_number = "105";

		btn_indent_edit = (Button) ll_indent
				.findViewById(R.id.btn_customer_indent_edit);
		btn_indent_edit.setOnClickListener(this);
		ciAdapter = new CustomerIndentAdapter(indentData, this.getActivity());
		lv_indent.setAdapter(ciAdapter);
		initData();

	}

	private void initData() {
		upData = new UpData(this.getActivity(),((MyApplication)getActivity().getApplication()).getU_id());
		upData.request_code = request_code;
		upData.code_number = code_number;
		upData.currentID =((MyApplication)getActivity().getApplication()).getU_id();
		upData.initHttp();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_customer_indent_edit:
			if (editState == STATE_EDIE) {
				editState = STATE_CANCLE;
				ciAdapter.state = STATE_CANCLE;
				ciAdapter.notifyDataSetChanged();
				btn_indent_edit.setText("删除");

			} else if (editState == STATE_CANCLE) {
				editState = STATE_EDIE;
				ciAdapter.state = STATE_EDIE;
				ciAdapter.notifyDataSetChanged();
				btn_indent_edit.setText("编辑");

			}
			break;
		default:
			break;
		}
	}

	private class UpDataUI extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			indentData = upData.queryStu();
			ciAdapter.indentData = upData.queryStu();
			ciAdapter.notifyDataSetChanged();
			lv_indent.setSelection(1);
		}

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.getActivity().unregisterReceiver(broadcastReceiver);
	}

}

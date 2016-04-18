package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mymeituan.R;
import com.example.util.MenuSave;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SellerFragment extends Fragment {
	// 商家名字
	TextView tv_seller_name;
	// 商家地址
	TextView tv_seller_address;
	// 商家电话
	TextView tv_seller_tele;
	// 商家电话
	TextView tv_seller_starting;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.sellerlistitem, container, false);
		// a.获取指定的控件对�?
		tv_seller_name = (TextView) v.findViewById(R.id.tv_seller_name);
		tv_seller_address = (TextView) v.findViewById(R.id.tv_seller_address);
		tv_seller_tele = (TextView) v.findViewById(R.id.tv_seller_tele);
		tv_seller_starting = (TextView) v.findViewById(R.id.tv_seller_starting);
		// 取出来

		String name = MenuSave.s.getS_name().toString();
		tv_seller_name.setText(name);
		String address = MenuSave.s.getS_address().toString();
		tv_seller_address.setText(address);
		String tele = MenuSave.s.getS_tele().toString();
		tv_seller_tele.setText(tele);
		String starting = MenuSave.s.getS_starting().toString();
		tv_seller_starting.setText(starting);

		return v;
	}

}
